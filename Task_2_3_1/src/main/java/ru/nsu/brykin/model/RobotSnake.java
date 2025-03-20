package ru.nsu.brykin.model;

/**
 * Змейка-робот.
 */
public class RobotSnake extends BasicSnake {
    private final BehaviorStrategy behaviorStrategy;

    /**
     * Конструктор змейки-робота.
     */
    public RobotSnake(int startX, int startY, int initialLength, BehaviorStrategy behaviorStrategy) {
        super(startX, startY, initialLength);
        this.behaviorStrategy = behaviorStrategy;
    }

    /**
     * Обновление направления движения.
     */
    public void updateDirection(GameModel model) {
        setDirection(behaviorStrategy.chooseDirection(this, model));
    }
}