package com.cincuentazo;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación Cincuentazo.
 * Se encarga de iniciar JavaFX y cargar las pantallas principales.
 */
public class App extends Application {

    private static Stage mainStage;

    /**
     * Método inicial de JavaFX.
     *
     * @param stage ventana principal de la aplicación
     * @throws IOException si no se puede cargar el archivo FXML
     */
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        showStartView();
    }

    /**
     * Muestra la pantalla inicial del juego.
     *
     * @throws IOException si no se puede cargar el archivo FXML
     */
    public static void showStartView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                App.class.getResource("/com/cincuentazo/view/start-view.fxml")
        );

        Scene scene = new Scene(loader.load(), 900, 600);

        mainStage.setTitle("Cincuentazo");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    /**
     * Método temporal para validar la selección de jugadores máquina.
     * En el PR 4 este método cargará la vista principal del juego.
     *
     * @param artificialPlayers cantidad de jugadores artificiales seleccionados
     */
    public static void showGameView(int artificialPlayers) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cincuentazo");
        alert.setHeaderText("Configuración seleccionada");
        alert.setContentText("Jugarás contra " + artificialPlayers + " jugador(es) artificial(es).");
        alert.showAndWait();
    }

    /**
     * Punto de entrada de la aplicación.
     *
     * @param args argumentos de ejecución
     */
    public static void main(String[] args) {
        launch(args);
    }
}