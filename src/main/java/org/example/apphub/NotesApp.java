package org.example.apphub;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NotesApp {

    public void start() {
        Stage stage = new Stage();
        stage.setTitle("Jegyzetfüzet");

        // Szövegmező, ahova a felhasználó írhat
        TextArea textArea = new TextArea();
        textArea.setPromptText("Írj ide jegyzeteket...");

        VBox layout = new VBox(10);
        layout.getChildren().add(textArea);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}
