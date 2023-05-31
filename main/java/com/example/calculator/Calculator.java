package com.example.calculator;

import java.util.Stack;
import java.lang.StringBuilder;


public class Calculator {
    public double evaluateExpression(String expression) {
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (ch == ' ') {
                continue;
            } else if (Character.isDigit(ch) || ch == '.') {
                StringBuilder operand = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    operand.append(expression.charAt(i++));
                }
                i--;

                double value = Double.parseDouble(operand.toString());
                operandStack.push(value);
            } else if (ch == '(') {
                operatorStack.push(ch);
            } else if (ch == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    evaluateTopOperator(operandStack, operatorStack);
                }

                if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
                    operatorStack.pop();
                } else {
                    throw new IllegalArgumentException("Invalid expression: Unbalanced parentheses");
                }
            } else if (isOperator(ch)) {
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(ch)) {
                    evaluateTopOperator(operandStack, operatorStack);
                }
                operatorStack.push(ch);
            } else {
                throw new IllegalArgumentException("Invalid expression: Invalid character " + ch);
            }
        }

        while (!operatorStack.isEmpty()) {
            evaluateTopOperator(operandStack, operatorStack);
        }

        if (operandStack.isEmpty()) {
            throw new IllegalArgumentException("Invalid expression: No operands found");
        }

        return operandStack.pop();
    }

    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private void evaluateTopOperator(Stack<Double> operandStack, Stack<Character> operatorStack) {
        if (operandStack.size() < 2) {
            throw new IllegalArgumentException("Invalid expression: Insufficient operands");
        }

        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();
        char operator = operatorStack.pop();

        double result = performOperation(operand1, operand2, operator);
        operandStack.push(result);
    }

    private double performOperation(double operand1, double operand2, char operator) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }
}
