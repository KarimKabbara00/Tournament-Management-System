package edu.ucdenver.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * <p>This defines the Admin Application Class.
 * This class creates the stage, loads the fxml file
 * and creates the window.
 * </p>
 * @author Karim Kabbara
 * @version 1.0
 * @since 2022-11-11
 */
public class AdminApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdminApplication.class.getResource("app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("Tournament System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * <p>
     *     Launches the Admin App
     *     </p>
     * @param args Args from command line
     *
     */
    public static void main(String[] args) {
        launch();
    }
}