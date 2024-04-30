package com.example.semesterproject;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class MineButton extends Button {
    int x;
    int y;
    public boolean mine;
    public int surroundMine;
    public boolean isRevealed;

    public boolean hasFlag;
    MineButton() {
        mine = false;
        surroundMine = 0;
        isRevealed = false;
        hasFlag = false;
    }
    MineButton(int x, int y) {
        this.x = x;
        this.y = y;
        mine = false;
        surroundMine = 0;
    }

    public boolean hasMine() {
        return mine;
    }
    public void generateMine() {
        if (Math.random() * 100 + 1 <= 15) {
            mine = true;
        }
    }

    public void checkMine(MineButton[][] buttons) {

        int row = buttons.length;
        int column = buttons.length;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i > -1 && i < row && j > -1 && j < column) {
                    if (buttons[i][j].hasMine()) {
                        surroundMine++;
                    }
                }
            }
        }
    }


    public void setButtonText() { //Renamed method from setText() to setButtonText to avoid confusion with JavaFX method of same name
        if (hasMine()){
            setText("💣");
            setTextFill(Color.RED);
        } else{
                setText(String.valueOf(surroundMine));
        }
    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }

    public void setFlagText() {
        if (hasFlag){
            setText("");
            hasFlag = false;
        }
        else {
            setText("🚩");
            hasFlag = true;
        }
    }
}