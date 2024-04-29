package com.example.semesterproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        Button[][] buttons = new MineButton[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j] = new MineButton(i, j);
                //create mines
                ((MineButton)buttons[i][j]).generateMine();
                //add to mine count
                if (((MineButton)(buttons[i][j])).hasMine()) {
                    mineCount++;
                }
                //add buttons to gridPane
                gridPane.add(buttons[i][j], i, j);
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ((MineButton)buttons[i][j]).checkMine((MineButton[][])buttons);
            }
        }

        ClickHandler clickHandler = new ClickHandler();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j].setOnAction(clickHandler);
            }
        }

        HBox title = new HBox();
        title.getChildren().add(new Label(String.valueOf(mineCount)));
        title.setAlignment(Pos.CENTER);
        VBox pane = new VBox();
        pane.getChildren().addAll(title, gridPane);
        Scene scene = new Scene(pane);
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    class ClickHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if (((MineButton)e.getSource()).hasMine()){
                System.out.println("Game Over");
            }
            else {
                ((MineButton)e.getSource()).setText();
            }

        }
    }

    public static void main(String[] args) {
        launch();
    }
}