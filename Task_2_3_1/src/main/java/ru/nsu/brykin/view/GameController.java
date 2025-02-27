package ru.nsu.brykin.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import ru.nsu.brykin.model.GameModel;
import ru.nsu.brykin.model.SnakeModel;

public class GameController {

    @FXML
    private Canvas gameCanvas;

    private GameModel gameModel;
    private GraphicsContext gc;
    private Timeline timeline;

    @FXML
    public void initialize() {
        gc = gameCanvas.getGraphicsContext2D();
        gameModel = new GameModel(20, 20, 1);
        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();
        timeline = new Timeline(new KeyFrame(Duration.millis(200), event -> {
            if (gameModel.isGameOver()) {
                drawGameOver();
                timeline.stop();
            } else {
                gameModel.update();
                drawGame();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        if (gameModel.isGameOver()) {
            return;
        }

        switch (event.getCode()) {
            case UP:
                gameModel.getSnake().setDirection(SnakeModel.Direction.UP);
                break;
            case DOWN:
                gameModel.getSnake().setDirection(SnakeModel.Direction.DOWN);
                break;
            case LEFT:
                gameModel.getSnake().setDirection(SnakeModel.Direction.LEFT);
                break;
            case RIGHT:
                gameModel.getSnake().setDirection(SnakeModel.Direction.RIGHT);
                break;
        }
    }

    private void drawGame() {
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        drawSnake();
        drawFood();
    }

    private void drawSnake() {
        gc.setFill(Color.GREEN);
        for (SnakeModel.Point point : gameModel.getSnake().getBody()) {
            gc.fillRect(point.x * 20, point.y * 20, 20, 20);
        }
    }

    private void drawFood() {
        gc.setFill(Color.RED);
        gc.fillOval(gameModel.getFood().x * 20, gameModel.getFood().y * 20, 20, 20);
    }

    private void drawGameOver() {
        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 48));

        String gameOverText = "Game Over";
        double textWidth = gc.getFont().getSize() * gameOverText.length() / 2;
        double textHeight = gc.getFont().getSize();
        gc.fillText(gameOverText, (gameCanvas.getWidth() - textWidth) / 2, (gameCanvas.getHeight() - textHeight) / 2);
    }
}