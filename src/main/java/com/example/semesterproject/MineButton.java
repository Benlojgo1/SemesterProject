package com.example.semesterproject;

import javafx.scene.control.Button;

public class MineButton extends Button {
    int x;
    int y;
    public boolean mine;
    public int surroundMine;
    MineButton() {
        mine = false;
        surroundMine = 0;
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

    public void setFlagText() {
        setText("🚩");
    }
    public void setLoseText() {
        setText("💣");
    }

    public void setText() {
        setText(String.valueOf(surroundMine));
    }
}