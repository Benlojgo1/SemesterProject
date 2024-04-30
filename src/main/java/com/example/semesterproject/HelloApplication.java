package com.example.semesterproject;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Create the GridPane for button layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        // Create buttons and add them to the grid
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button button = new Button();

                /** Set button size */
                button.setPrefSize(50, 50);

                // Add button to the gridpane at specific row and column
                gridPane.add(button,i, j);
            }
        }

        // Set the scene with the gridPane and title
        Scene scene = new Scene(gridPane);
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}