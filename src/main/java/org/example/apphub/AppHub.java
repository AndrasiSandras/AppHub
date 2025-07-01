package org.example.apphub;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppHub extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("App Hub");

        // Üdvözlő felirat
        Label welcomeLabel = new Label("Üdv az AppHubban!");

        // Gombok létrehozása
        Button calculatorButton = new Button("Számológép");
        Button notesButton = new Button("Jegyzetfüzet");
        Button drawButton = new Button("Rajztábla");

        // Gombokhoz eseménykezelők
        calculatorButton.setOnAction(e -> {
            System.out.println("Számológép indítása...");
            CalculatorApp calculatorApp = new CalculatorApp();
            calculatorApp.start(); // külön ablakban indul
        });

        notesButton.setOnAction(e -> {
            System.out.println("Jegyzetfüzet indítása...");
            NotesApp notesApp = new NotesApp();
            notesApp.start();
        });

        drawButton.setOnAction(e -> {
            System.out.println("Rajztábla indítása...");
            DrawApp drawApp = new DrawApp();
            drawApp.start();
        });

        // Elrendezés – egy függőleges VBox
        VBox layout = new VBox(10); // 10px térköz a komponensek között
        layout.getChildren().addAll(welcomeLabel, calculatorButton, notesButton, drawButton);

        // Scene létrehozása és beállítása
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
