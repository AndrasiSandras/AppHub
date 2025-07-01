package org.example.apphub;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CalculatorApp {

    public void start() {
        // Új ablak (Stage) létrehozása
        Stage stage = new Stage();
        stage.setTitle("Számológép");

        // Tartalom: egyelőre csak egy szöveg
        Label label = new Label("Itt lesz a számológép!");

        VBox layout = new VBox(10);
        layout.getChildren().add(label);

        Scene scene = new Scene(layout, 250, 150);
        stage.setScene(scene);
        stage.show();
    }
}
