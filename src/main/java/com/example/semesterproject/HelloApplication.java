package com.example.semesterproject;

import javafx.application.Application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

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

                gridPane.add(buttons[i][j], i, j);
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ((MineButton)buttons[i][j]).checkMine((MineButton[][])buttons);
            }
        }
        Text feedback = new Text();
        Button exitButton = new Button("Exit");
        HBox feedbackRow = new HBox(feedback, exitButton);
        feedbackRow.setAlignment(Pos.CENTER);
        feedback.setFont(new Font(20));
        feedbackRow.setVisible(false);

        ClickHandler clickHandler = new ClickHandler((MineButton[][])buttons, feedbackRow, mineCount); //Passed in buttons array

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j].setOnMouseClicked(clickHandler);
            }
        }

        HBox title = new HBox();
        title.getChildren().add(new Label("Flag Count: " + String.valueOf(mineCount)));
        title.setAlignment(Pos.CENTER);

        VBox gamePane = new VBox();
        gamePane.getChildren().addAll(title, gridPane, feedbackRow);
        gamePane.setSpacing(20);
        gamePane.setAlignment(Pos.CENTER);

        VBox startPane = new VBox();

        Text text = new Text("Welcome to Minesweeper Clone!");
        text.setFont(new Font(28));

        Button button = new Button("Start Game");

        startPane.getChildren().addAll(text, button);
        startPane.setSpacing(15);
        startPane.setAlignment(Pos.CENTER);

        Scene gameScene = new Scene(gamePane, 500, 500);
        Scene startScene = new Scene(startPane, 500, 500);

        button.setOnAction(e -> {
            stage.setScene(gameScene);
        });
        exitButton.setOnAction(e -> {
            stage.close();
            stage.setScene(startScene);
        });

        stage.setTitle("Minesweeper");
        stage.setScene(startScene);
        stage.show();
    }

    public class ClickHandler implements EventHandler<MouseEvent> {

        public MineButton[][] buttons; //Field that creates a MineButton[][] array.
        public HBox feedbackPane;
        public int mineCount;
        public ClickHandler(MineButton[][] buttons, HBox feedbackPane, int mineCount) { //Constructor that allows for MineButton[][] array parameter to be passed through.
            this.buttons = buttons; //Sets buttons field to passed in array.
            this.feedbackPane = feedbackPane;
            this.mineCount = mineCount;

        }
        boolean gameOver = false;
        int tileCount = 0;

    @Override

        public void handle(MouseEvent e) {
            int row = buttons.length; //Initializing integer row to length of buttons array
            int column = buttons.length; //Initializing integer column to length of buttons array


            MineButton clickedButton = (MineButton) e.getSource(); //Creating MineButton object reference "clickedButton" that represents the button being clicked.

            if(gameOver){ //if gameOver is true
                return; //game doesn't allow any further actions to grid.
            }

            if (e.getButton() == MouseButton.PRIMARY){
                if(!clickedButton.hasFlag){
                    if (clickedButton.hasMine()) { //If the clicked button has a mine
                        System.out.println("Game Over"); //Prompt game end to user
                        /** This reveals all mines once user clicks one mine*/
                        for (int i = 0; i < row; i++) {
                            for (int j = 0; j < column; j++) {
                                if (buttons[i][j].hasMine()){
                                    buttons[i][j].setButtonText();
                                }
                            }
                        }

                        for (Node nodeIn: feedbackPane.getChildren() //For all nodes in feedbackPane
                        ) { if(nodeIn instanceof Text){ //if node is an object of type Text
                            ((Text) nodeIn).setText("You have lost! "); //Set text to losing text.
                        }
                        }
                        feedbackPane.setVisible(true); //Set feedback pane to be visible
                        gameOver = true; //Set gameOver true

                    } else if (clickedButton.surroundMine == 0 && !clickedButton.isRevealed) { //If clicked button's surroundMine count == 0
                        clickedButton.isRevealed = true; //Set isRevealed value to true
                        tileCount++;
                        System.out.println(tileCount);
                        clickedButton.setButtonText(); // Reveal text of clicked button
                        for (int i = clickedButton.getX() - 1; i <= clickedButton.getX() + 1; i++) { //Nested for-loop. Checks surrounding buttons.
                            for (int j = clickedButton.getY() - 1; j <= clickedButton.getY() + 1; j++) { //Nested for-loop. Checks surrounding buttons.
                                if (i > -1 && i < row && j > -1 && j < column) { //Checks whether coordinates are within boundary of board
                                    MineButton neighborButton = getButtonAt(i, j); //Create MineButton object reference "neighborButton" that represents neighboring buttons
                                    if (!neighborButton.isRevealed) { //If the neighboring button has not been revealed already
                                        neighborButton.fireEvent(e); // Simulate a click on the neighboring button, firing clickButton event again.
                                    }
                                }
                            }
                        }

                    } else if (!clickedButton.isRevealed){ //If clicked button's surroundMine count > 0
                        clickedButton.isRevealed = true; //Set isRevealed value to true
                        tileCount++;
                        System.out.println(tileCount);

                        clickedButton.setButtonText(); //Reveal text of clicked button
                    }
                }
            }

            if (tileCount == row * column - mineCount) {
                for (Node nodeIn: feedbackPane.getChildren() //For all nodes in feedbackPane
                ) { if(nodeIn instanceof Text){ //if node is an object of type Text
                    ((Text) nodeIn).setText("You have won! "); //Set text to losing text.
                }
                }
                feedbackPane.setVisible(true);
                gameOver = true;
            }

            if (e.getButton() == MouseButton.SECONDARY){ //If right click
                if(!clickedButton.isRevealed){ //While flag isn't revealed
                    clickedButton.setFlagText(); //Call set flag text method
                }
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

