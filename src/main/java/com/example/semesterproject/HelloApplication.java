package com.example.semesterproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        int mineCount = 0;
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        MineButton[][] buttons = new MineButton[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j] = new MineButton(i, j);
                buttons[i][j].generateMine();
                if (buttons[i][j].hasMine()) {
                    mineCount++;
                }
                gridPane.add(buttons[i][j], i, j);
            }
        }
        HBox title = new HBox();
        title.getChildren().add(new Label(String.valueOf(mineCount)));
        VBox pane = new VBox();
        pane.getChildren().addAll(title, gridPane);
        Scene scene = new Scene(pane);
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}