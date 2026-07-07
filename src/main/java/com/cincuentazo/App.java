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
 * Main application class for Cincuentazo.
 * Starts JavaFX, loads views, and configures the application icon.
 */
public class App extends Application {

    /** Primary stage shared across all views. */
    private static Stage mainStage;

    /**
     * Initializes the application and displays the start view.
     *
     * @param stage primary JavaFX stage
     * @throws IOException if the start view cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        configureApplicationIcon();
        showStartView();
    }

    /**
     * Sets the window icon for the main stage.
     */
    private static void configureApplicationIcon() {
        InputStream iconStream = App.class.getResourceAsStream("/com/cincuentazo/icon.png");

        if (iconStream != null) {
            mainStage.getIcons().add(new Image(iconStream));
        }
    }

    /**
     * Displays the game start screen.
     *
     * @throws IOException if the FXML file cannot be loaded
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
     * Displays the main game screen and starts a new match.
     *
     * @param realPlayerName name of the human player
     * @param artificialPlayers number of artificial players selected
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
     * Shows an error alert dialog.
     *
     * @param title alert title
     * @param message alert message
     */
    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Cincuentazo");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
