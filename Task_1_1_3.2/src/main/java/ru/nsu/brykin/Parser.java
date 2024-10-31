package ru.nsu.brykin;

/**
 * класс для парсинга строк.
 */
class Parser {
    public Expression parse(String expression) {
        expression = expression.replaceAll("\\s+", ""); // Удалить пробелы
        return parseExpression(expression);
    }

    private Expression parseExpression(String expression) {
        int openBrackets = 0;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '(') {
                openBrackets++;
            }
            if (c == ')') {
                openBrackets--;
            }
            if (openBrackets == 0) {
                // Обработка операций без обязательных скобок
                if (c == '+') {
                    return new Add(parseExpression(expression.substring(0, i)),
                            parseExpression(expression.substring(i + 1)));
                }
                if (c == '-') {
                    return new Sub(parseExpression(expression.substring(0, i)),
                            parseExpression(expression.substring(i + 1)));
                }
                if (c == '*') {
                    return new Mul(parseExpression(expression.substring(0, i)),
                            parseExpression(expression.substring(i + 1)));
                }
                if (c == '/') {
                    return new Div(parseExpression(expression.substring(0, i)),
                            parseExpression(expression.substring(i + 1)));
                }
            }
        }
        // Удаляем внешние скобки
        if (expression.startsWith("(") && expression.endsWith(")")) {
            return parseExpression(expression.substring(1, expression.length() - 1));
        }
        if (expression.matches("\\d+")) {
            return new Number(Integer.parseInt(expression));
        }
        return new Variable(expression);
    }
}