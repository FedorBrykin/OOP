package ru.nsu.brykin;

import java.util.Scanner;

/**
 * работа с пользователем.
 */
public class Main {
    /**
     * main.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();

        System.out.print("Математическое выражение: ");
        String inputExpression = scanner.nextLine();

        Expression e = parser.parse(inputExpression);
        e = e.simplify();
        e.print();

        System.out.print("Переменная дифференцирования: ");
        String variable = scanner.nextLine();
        Expression de = e.derivative(variable);
        de.print();

        System.out.print("Значения переменных (пример: x=10; y=13): ");
        String variableAssignments = scanner.nextLine();
        double result = e.eval(variableAssignments);
        System.out.println(result);
        scanner.close();
    }
}