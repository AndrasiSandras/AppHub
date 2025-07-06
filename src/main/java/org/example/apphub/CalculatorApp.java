package org.example.apphub;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class CalculatorApp {

    private String currentInput = "";
    private String expression = "";

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
                    updateDisplay(display);
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
            updateDisplay(display);
        });
        grid.add(zeroBtn, 1, 3);

        // Törlés (C) gomb
        Button clearBtn = new Button("C");
        clearBtn.setPrefSize(50, 50);
        clearBtn.setOnAction(e -> {
            currentInput = "";
            expression = "";
            display.clear();
        });
        grid.add(clearBtn, 0, 3);

        // Egyenlőség (=)
        Button equalBtn = new Button("=");
        equalBtn.setPrefSize(50, 50);
        equalBtn.setOnAction(e -> {
            if (!currentInput.isEmpty()) {
                expression += currentInput;
            }

            try {
                double result = evaluate(expression);
                display.setText(expression + " = " + result);
                currentInput = String.valueOf(result);
                expression = "";
            } catch (Exception ex) {
                display.setText("Hibás kifejezés");
                currentInput = "";
                expression = "";
            }
        });
        grid.add(equalBtn, 3, 4);

        // Negatív szám beviteléhez: -/+
        Button negBtn = new Button("+/-");
        negBtn.setPrefSize(50, 50);
        negBtn.setOnAction(e -> {
            if (!currentInput.isEmpty()) {
                if (currentInput.startsWith("-")) {
                    currentInput = currentInput.substring(1);
                } else {
                    currentInput = "-" + currentInput;
                }
                updateDisplay(display);
            }
        });
        grid.add(clearBtn, 2, 4);

        // Zárójelek: (
        Button openParBtn = new Button("(");
        openParBtn.setPrefSize(50, 50);
        openParBtn.setOnAction(e -> {
            expression += "(";
            updateDisplay(display);
        });
        grid.add(openParBtn, 0, 4);

        // Zárójelek: )
        Button closeParBtn = new Button(")");
        closeParBtn.setPrefSize(50, 50);
        closeParBtn.setOnAction(e -> {
            expression += currentInput + ")";
            currentInput = "";
            updateDisplay(display);
        });
        grid.add(closeParBtn, 1, 4);

        // Tizedes pont
        Button dotBtn = new Button(".");
        dotBtn.setPrefSize(50, 50);
        dotBtn.setOnAction(e -> {
            if (!currentInput.contains(".")) {
                currentInput += ".";
                updateDisplay(display);
            }
        });
        grid.add(dotBtn, 2, 3);

        // Műveleti gombok
        String[] ops = {"+", "-", "*", "/"};
        for (int i = 0; i < ops.length; i++) {
            String op = ops[i];
            Button btn = new Button(op);
            btn.setPrefSize(50, 50);
            btn.setOnAction(e -> {
                if (!currentInput.isEmpty()) {
                    expression += currentInput + op;
                    currentInput = "";
                    updateDisplay(display);
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

    // Frissíti a kijelzőt az expression + currentInput alapján
    private void updateDisplay(TextField display) {
        display.setText(expression + currentInput);
    }

    // Kifejezés kiértékelése (műveleti sorrend betarása)
    private double evaluate(String expr) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript"); // igen, JavaScript engine értékeli ki
        Object result = engine.eval(expr);
        return Double.parseDouble(result.toString());
    }

}
