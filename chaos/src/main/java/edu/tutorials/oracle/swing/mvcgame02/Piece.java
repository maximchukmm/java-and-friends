package edu.tutorials.oracle.swing.mvcgame02;

import java.awt.Color;

enum Piece {

    Red(Color.red), Green(Color.green), Blue(Color.blue);
    public Color color;

    private Piece(Color color) {
        this.color = color;
    }
}
