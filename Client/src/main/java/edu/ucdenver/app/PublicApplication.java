package edu.ucdenver.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * <p>This defines the Public Application Class.
 * This class creates the stage, loads the fxml file
 * and creates the window.
 * </p>
 * @author David Desrochers
 * @version 1.0
 * @since 2022-11-11
 */
public class PublicApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdminApplication.class.getResource("publicApp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("View Tournament");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * <p>
     *     Runs the Public Application
     *     </p>
     * @param args Command line args
     *
     */
    public static void main(String[] args) {
        launch();
    }
}