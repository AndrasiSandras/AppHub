package org.example.apphub;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NotesApp {

    private File currentFile = null; // nyomon követjük az aktuálisan megnyitott/mentett fájlt
    Label fileNameLabel = new Label("Fájl: *untitled*"); // kezdetben nincs megnyitott fájl

    public void start() {
        // Új ablak létrehozása
        Stage stage = new Stage();
        stage.setTitle("Jegyzetfüzet");

        // Szövegmező, ahova a felhasználó írhat
        TextArea textArea = new TextArea();
        textArea.setPromptText("Írj ide jegyzetet...");

        // Mentés gomb
        Button saveBtn = new Button("Mentés");
        saveBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Jegyzet mentése");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Szövegfájlok (*.txt)", "*.txt")
            );

            // Megnyitjuk a fájlválasztót
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                saveTextToFile(textArea.getText(), file);
                currentFile = file;
                fileNameLabel.setText("Fájl: " + file.getName());
            }
        });

        // Megnyitás gomb
        Button openBtn = new Button("Megnyitás");
        openBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Jegyzet megnyitása");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Szövegfájlok (*.txt)", "*.txt")
            );

            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                String content = loadTextFromFile(file);
                if (content != null) {
                    textArea.setText(content);
                    currentFile = file;
                    fileNameLabel.setText("Fájl: " + file.getName());
                }
            }
        });

        Button deleteBtn = new Button("Törlés");
        deleteBtn.setOnAction(e -> {
            if (currentFile != null && currentFile.exists()) {
                boolean deleted = currentFile.delete();
                if (deleted) {
                    showInfo("A fájl törölve lett: " + currentFile.getName());
                    textArea.clear();
                    currentFile = null;
                    fileNameLabel.setText("Fájl: *untitled*");
                } else {
                    showError("Nem sikerült törölni a fájlt.");
                }
            } else {
                showError("Nincs fájl kiválasztva vagy a fájl nem létezik.");
            }
        });

        // Layout: függőleges, alul a gomb
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        HBox buttonBar = new HBox(10, saveBtn, openBtn, deleteBtn);
        layout.getChildren().addAll(fileNameLabel, textArea, buttonBar);

        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    // Mentési segédfüggvény
    private void saveTextToFile(String content, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException ex) {
            showError("Nem sikerült menteni a fájlt: " + ex.getMessage());
        }
    }

    // Betöltési segédfüggvény
    private String loadTextFromFile(File file) {
        try {
            return new String(java.nio.file.Files.readAllBytes(file.toPath()));
        } catch (IOException ex) {
            showError("Nem sikerült megnyitni a fájlt: " + ex.getMessage());
            return null;
        }
    }

    // Információs üzenet
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Információ");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Egyszerű hibaüzenet megjelenítése
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
