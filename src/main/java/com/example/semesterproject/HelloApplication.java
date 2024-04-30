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
import javafx.scene.paint.Color;
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

        ClickHandler clickHandler = new ClickHandler((MineButton[][])buttons); //Passed in buttons array
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
        public MineButton[][] buttons; //Field that creates a MineButton[][] array.
        public ClickHandler(MineButton[][] buttons) { //Constructor that allows for MineButton[][] array parameter to be passed through.
            this.buttons = buttons; //Sets buttons field to passed in array.
        }
        @Override
        public void handle(ActionEvent e) {
            int row = buttons.length; //Initializing integer row to length of buttons array
            int column = buttons.length; //Initializing integer column to length of buttons array
            MineButton clickedButton = (MineButton) e.getSource(); //Creating MineButton object reference "clickedButton" that represents the button being clicked.

            if (clickedButton.hasMine()) { //If the clicked button has a mine
                clickedButton.setButtonText(); //Call setButtonText on clicked button
                System.out.println("Game Over"); //Prompt game end to user
            } else if (clickedButton.surroundMine == 0) { //If clicked button's surroundMine count == 0
                clickedButton.isRevealed = true; //Set isRevealed value to true
                clickedButton.setButtonText(); // Reveal text of clicked button
                for (int i = clickedButton.getX() - 1; i <= clickedButton.getX() + 1; i++) { //Nested for-loop. Checks surrounding buttons.
                    for (int j = clickedButton.getY() - 1; j <= clickedButton.getY() + 1; j++) { //Nested for-loop. Checks surrounding buttons.
                        if (i > -1 && i < row && j > -1 && j < column) { //Checks whether coordinates are within boundary of board
                            MineButton neighborButton = getButtonAt(i, j); //Create MineButton object reference "neighborButton" that represents neighboring buttons
                            if (!neighborButton.isRevealed) { //If the neighboring button has not been revealed already
                                neighborButton.fire(); // Simulate a click on the neighboring button, firing clickButton event again.
                            }
                        }
                    }
                }
            } else { //If clicked button's surroundMine count > 0
                clickedButton.isRevealed = true; //Set isRevealed value to true
                clickedButton.setButtonText(); //Reveal text of clicked button
            }
        }
        public MineButton getButtonAt(int x, int y){ //Public method getButtonAt.
            return buttons[x][y]; //Returns button object at passed in coordinates.
        }
    }

    public static void main(String[] args) {
        launch();
    }
}