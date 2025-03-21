package ru.nsu.brykin.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * игра началась!.
 */
public class StartController {

    @FXML
    private void startGame() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/GameView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Змейка");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
