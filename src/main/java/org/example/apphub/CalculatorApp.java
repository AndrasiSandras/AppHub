package org.example.apphub;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CalculatorApp {

    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;

    public void start() {
        Stage stage = new Stage();
        stage.setTitle("Számológép");

        TextField display = new TextField();
        display.setEditable(false);
        display.setPrefHeight(50);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Számgombok
        int num = 1;
        for (int row = 2; row >= 0; row--) {
            for (int col = 0; col < 3; col++) {
                int digit = num;
                Button btn = new Button(String.valueOf(digit));
                btn.setPrefSize(50, 50);
                btn.setOnAction(e -> {
                    currentInput += digit;
                    display.setText(currentInput);
                });
                grid.add(btn, col, row);
                num++;
            }
        }

        // 0 gomb
        Button zeroBtn = new Button("0");
        zeroBtn.setPrefSize(50, 50);
        zeroBtn.setOnAction(e -> {
            currentInput += "0";
            display.setText(currentInput);
        });
        grid.add(zeroBtn, 1, 3);

        // Törlés (C) gomb
        Button clearBtn = new Button("C");
        clearBtn.setPrefSize(50, 50);
        clearBtn.setOnAction(e -> {
            currentInput = "";
            operator = "";
            firstOperand = 0;
            display.clear();
        });
        grid.add(clearBtn, 0, 3);

        // Egyenlőség (=)
        Button equalBtn = new Button("=");
        equalBtn.setPrefSize(50, 50);
        equalBtn.setOnAction(e -> {
            if (!currentInput.isEmpty() && !operator.isEmpty()) {
                double secondOperand = Double.parseDouble(currentInput);
                double result = switch (operator) {
                    case "+" -> firstOperand + secondOperand;
                    case "-" -> firstOperand - secondOperand;
                    case "*" -> firstOperand * secondOperand;
                    case "/" -> secondOperand != 0 ? firstOperand / secondOperand : 0;
                    default -> 0;
                };
                display.setText(String.valueOf(result));
                currentInput = String.valueOf(result);
                operator = "";
            }
        });
        grid.add(equalBtn, 2, 3);

        // Műveletgombok: + - * /
        String[] ops = {"+", "-", "*", "/"};
        for (int i = 0; i < ops.length; i++) {
            String op = ops[i];
            Button btn = new Button(op);
            btn.setPrefSize(50, 50);
            btn.setOnAction(e -> {
                if (!currentInput.isEmpty()) {
                    firstOperand = Double.parseDouble(currentInput);
                    operator = op;
                    currentInput = "";
                    display.setText("");
                }
            });
            grid.add(btn, 3, i);
        }

        VBox root = new VBox(10, display, grid);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 280, 300);
        stage.setScene(scene);
        stage.show();
    }
}
