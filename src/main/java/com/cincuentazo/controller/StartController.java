package com.cincuentazo.controller;

import com.cincuentazo.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller for the start screen.
 * Allows the human player to enter a name, select artificial opponents,
 * and view game instructions.
 */
public class StartController {

    @FXML
    private TextField playerNameTextField;

    @FXML
    private ChoiceBox<Integer> machineChoiceBox;

    @FXML
    private Label messageLabel;

    /**
     * Initializes start screen components.
     */
    @FXML
    public void initialize() {
        machineChoiceBox.setItems(FXCollections.observableArrayList(1, 2, 3));
        machineChoiceBox.setValue(1);
        messageLabel.setText("");
    }

    /**
     * Handles the start game button click.
     */
    @FXML
    private void onStartGame() {
        String playerName = playerNameTextField.getText();

        if (playerName == null || playerName.trim().isEmpty()) {
            messageLabel.setText("Debes ingresar tu nombre para iniciar la partida.");
            return;
        }

        Integer selectedPlayers = machineChoiceBox.getValue();

        if (selectedPlayers == null) {
            messageLabel.setText("Debes seleccionar la cantidad de jugadores artificiales.");
            return;
        }

        App.showGameView(playerName.trim(), selectedPlayers);
    }

    /**
     * Displays the game instructions in an information dialog.
     */
    @FXML
    private void onShowInstructions() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instrucciones - Cincuentazo");
        alert.setHeaderText("¿Cómo se juega Cincuentazo?");

        alert.setContentText(
                "Objetivo:\n"
                        + "Sobrevivir sin hacer que la suma de la mesa supere 50.\n\n"
                        + "Preparación:\n"
                        + "• Cada jugador recibe 4 cartas.\n"
                        + "• Se coloca una carta inicial en la mesa.\n"
                        + "• Puedes jugar contra 1, 2 o 3 jugadores artificiales.\n\n"
                        + "Valores de las cartas:\n"
                        + "• Cartas del 2 al 8: suman su valor.\n"
                        + "• 9: suma 0.\n"
                        + "• 10: suma 10.\n"
                        + "• J, Q y K: restan 10.\n"
                        + "• As: puede valer 1 o 10 según convenga.\n\n"
                        + "Turno:\n"
                        + "• Debes escoger una carta jugable de tu mano.\n"
                        + "• Después de jugar, debes tomar una carta del mazo.\n"
                        + "• Los jugadores artificiales juegan automáticamente.\n\n"
                        + "Eliminación:\n"
                        + "• Si un jugador no tiene ninguna carta que pueda jugar sin superar 50, queda eliminado.\n\n"
                        + "Ganador:\n"
                        + "• Gana el último jugador que quede activo en la partida."
        );

        alert.showAndWait();
    }
}
