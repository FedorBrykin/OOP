package ru.nsu.brykin.model;

/**
 * Стратегия поведения змейки-робота.
 */
public interface BehaviorStrategy {
    /**
     * Выбор направления движения.
     */
    Direction chooseDirection(Snake snake, GameModel model);
}
