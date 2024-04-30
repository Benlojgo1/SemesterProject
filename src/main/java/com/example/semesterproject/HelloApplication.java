package com.example.semesterproject;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        int mineCount = 0;
        // Create the GridPane for button layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        Button[][] buttons = new MineButton[8][8];
        // Create buttons and add them to the grid
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j] = new MineButton(i, j);
                /** generates mines*/
                ((MineButton)buttons[i][j]).generateMine();
                if (((MineButton)buttons[i][j]).hasMine()) {
                    mineCount++;
                }
                /** Set button size */
                buttons[i][j].setPrefSize(50, 50);

                // Add button to the gridpane at specific row and column
                gridPane.add(buttons[i][j], i, j);
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ((MineButton) buttons[i][j]).checkMine((MineButton[][]) buttons);
            }
        }

        ClickHandler clickHandler = new ClickHandler();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j].setOnMouseClicked(clickHandler);
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

    class ClickHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            if (e.getButton() == MouseButton.SECONDARY) {
                ((MineButton) e.getSource()).setFlagText();
            }
            else {
                if (((MineButton) e.getSource()).hasMine()) {
                    ((MineButton) e.getSource()).setLoseText();
                } else {
                    ((MineButton) e.getSource()).setText();
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

