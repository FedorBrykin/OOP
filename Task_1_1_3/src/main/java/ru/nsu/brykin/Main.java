package ru.nsu.brykin;

import java.util.Scanner;

/**
 * работа с пользователем.
 */
public class Main {
    /**
     * ввод/вывод.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();

        // Ввод выражения
        System.out.print("Математическое выражение: ");
        String inputExpression = scanner.nextLine();

        // Парсинг выражения
        Expression e = parser.parse(inputExpression);
        System.out.println(e.print());

        // Дифференцирование
        System.out.print("Переменная дифференцирования: ");
        String variable = scanner.nextLine();
        Expression de = e.derivative(variable);
        System.out.println(de.print());

        // Ввод значений переменных
        System.out.print("Значения переменных (пример: x=10; y=13): ");
        String variableAssignments = scanner.nextLine();
        int result = e.eval(variableAssignments);
        System.out.println(result);

        scanner.close();
    }
}