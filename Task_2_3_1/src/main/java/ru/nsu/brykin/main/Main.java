package ru.nsu.brykin.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * запуск.
 */
public class Main extends Application {

    /**
     * begin.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/StartView.fxml"));
        primaryStage.setTitle("Змейка");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * launching.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
