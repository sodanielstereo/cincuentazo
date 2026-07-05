package com.cincuentazo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.cincuentazo.controller.GameController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación Cincuentazo.
 * Se encarga de iniciar JavaFX, cargar vistas y configurar el ícono de la aplicación.
 */
public class App extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        configureApplicationIcon();
        showStartView();
    }

    /**
     * Configura el ícono de la ventana principal.
     */
    private static void configureApplicationIcon() {
        InputStream iconStream = App.class.getResourceAsStream("/com/cincuentazo/icon.png");

        if (iconStream != null) {
            mainStage.getIcons().add(new Image(iconStream));
        }
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
        Scene scene = new Scene(loader.load(), 1000, 680);

        mainStage.setTitle("Cincuentazo");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    /**
     * Muestra la pantalla principal del juego.
     *
     * @param realPlayerName nombre del jugador real
     * @param artificialPlayers cantidad de jugadores artificiales seleccionados
     */
    public static void showGameView(String realPlayerName, int artificialPlayers) {
        try {
            URL fxmlUrl = App.class.getResource("/com/cincuentazo/view/game-view.fxml");

            if (fxmlUrl == null) {
                throw new IOException("No se encontró game-view.fxml.");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Scene scene = new Scene(loader.load(), 1000, 780);

            GameController controller = loader.getController();
            controller.startNewGame(realPlayerName, artificialPlayers);

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

    public static void main(String[] args) {
        launch(args);
    }
}