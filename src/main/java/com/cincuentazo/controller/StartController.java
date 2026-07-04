package com.cincuentazo.controller;

import com.cincuentazo.App;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

/**
 * Controlador de la pantalla inicial.
 * Permite seleccionar la cantidad de jugadores artificiales.
 */
public class StartController {

    @FXML
    private ChoiceBox<Integer> machineChoiceBox;

    @FXML
    private Label messageLabel;

    /**
     * Inicializa los componentes de la pantalla inicial.
     */
    @FXML
    public void initialize() {
        machineChoiceBox.setItems(FXCollections.observableArrayList(1, 2, 3));
        machineChoiceBox.setValue(1);
        messageLabel.setText("");
    }

    /**
     * Maneja el evento del botón Iniciar juego.
     */
    @FXML
    private void onStartGame() {
        Integer selectedPlayers = machineChoiceBox.getValue();

        if (selectedPlayers == null) {
            messageLabel.setText("Debes seleccionar la cantidad de jugadores artificiales.");
            return;
        }

        App.showGameView(selectedPlayers);
    }
}