package com.cincuentazo;

import java.io.IOException;
import java.net.URL;

import com.cincuentazo.controller.GameController;

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
        URL fxmlUrl = App.class.getResource("/com/cincuentazo/view/start-view.fxml");

        if (fxmlUrl == null) {
            throw new IOException("No se encontró start-view.fxml.");
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Scene scene = new Scene(loader.load(), 900, 600);

        mainStage.setTitle("Cincuentazo");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    /**
     * Muestra la pantalla principal del juego.
     *
     * @param artificialPlayers cantidad de jugadores artificiales seleccionados
     */
    public static void showGameView(int artificialPlayers) {
        try {
            URL fxmlUrl = App.class.getResource("/com/cincuentazo/view/game-view.fxml");

            if (fxmlUrl == null) {
                throw new IOException("No se encontró game-view.fxml.");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Scene scene = new Scene(loader.load(), 1000, 680);

            GameController controller = loader.getController();
            controller.startNewGame(artificialPlayers);

            mainStage.setTitle("Cincuentazo - Partida");
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.show();

        } catch (IOException exception) {
            showError("Error cargando la vista del juego", exception.getMessage());
        }
    }

    /**
     * Muestra una alerta de error.
     *
     * @param title título de la alerta
     * @param message mensaje de la alerta
     */
    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Cincuentazo");
        alert.setHeaderText(title);
        alert.setContentText(message);
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