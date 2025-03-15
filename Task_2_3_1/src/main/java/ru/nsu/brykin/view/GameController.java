package ru.nsu.brykin.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import ru.nsu.brykin.model.*;

/**
 * играем.
 */
public class GameController {

    @FXML
    private Canvas gameCanvas;

    @FXML
    private Label gameOverLabel;
    private GameModel gameModel;
    private GraphicsContext gc;
    private Timeline timeline;
    private double cellWidth;
    private double cellHeight;
    private BooleanProperty gameOver = new SimpleBooleanProperty(false);

    @FXML
    public void initialize() {
        gc = gameCanvas.getGraphicsContext2D();
        gameModel = new GameModel(new GameConfig());
        cellWidth = gameCanvas.getWidth() / gameModel.getConfig().getWidth();
        cellHeight = gameCanvas.getHeight() / gameModel.getConfig().getHeight();
        gameOverLabel.visibleProperty().bind(gameOver);
        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();
        timeline = new Timeline(new KeyFrame(Duration.millis(200), event -> {
            if (gameModel.isGameOver()) {
                gameOverLabel.setText(gameModel.isWin() ? "You Win!" : "Game Over");
                gameOver.set(true);
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
                gameModel.getSnake().setDirection(Direction.UP);
                break;
            case DOWN:
                gameModel.getSnake().setDirection(Direction.DOWN);
                break;
            case LEFT:
                gameModel.getSnake().setDirection(Direction.LEFT);
                break;
            case RIGHT:
                gameModel.getSnake().setDirection(Direction.RIGHT);
                break;
        }
    }

    /**
     * теперь я определяю реальность!.
     */
    private void drawGame() {
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        drawSnake();
        drawFood();
    }

    /**
     * питон, который съел слона.
     */
    private void drawSnake() {
        gc.setFill(Color.GREEN);
        for (Point point : gameModel.getSnake().getBody()) {
            gc.fillRect(point.x * cellWidth, point.y * cellHeight, cellWidth, cellHeight);
        }
    }

    /**
     * слон)).
     */
    private void drawFood() {
        gc.setFill(Color.RED);
        gc.fillOval(gameModel.getFood().getX() * cellWidth, gameModel.getFood().getY() * cellHeight, cellWidth, cellHeight);
    }

    /**
     * gameOverProperty.
     */
    public BooleanProperty gameOverProperty() {
        return gameOver;
    }
}