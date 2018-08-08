package edu.tutorials.oracle.swing.mvcgame01;

import java.util.Random;

public class Model {
    static final int
        LINE = 5,
        SCORE = 10, OPTIONS = 20;
    Pin pins[][] = new Pin[21][LINE];
    int combination[] = new int[LINE];
    int curPin = 0;
    int turn = 1;
    Random generator = new Random();
    int repaintPin;
    boolean pinsRepaint = false;
    int pinsToRepaint;
    boolean isUpdate = true, isPlaying = true, isRowFull = false;
    static final int HIT_X[] = {270, 290, 310, 290, 310}, HIT_Y[] = {506, 496, 496, 516, 516};

    public Model() {

        for (int i = 0; i < SCORE; i++) {
            for (int j = 0; j < LINE; j++) {
                pins[i][j] = new Pin(20, 0);
                pins[i][j].setPosition(j * 50 + 30, 510 - i * 50);
                pins[i + SCORE][j] = new Pin(8, 0);
                pins[i + SCORE][j].setPosition(HIT_X[j], HIT_Y[j] - i * 50);
            }
        }
        for (int i = 0; i < LINE; i++) {
            pins[OPTIONS][i] = new Pin(20, i + 2);
            pins[OPTIONS][i].setPosition(370, i * 50 + 56);
        }

    }

    void fillHole(int color) {
        pins[turn - 1][curPin].setColor(color + 1);
        pinsRepaint = true;
        pinsToRepaint = turn;
        curPin = (curPin + 1) % LINE;
        if (curPin == 0) {
            isRowFull = true;
        }
        pinsRepaint = false;
        pinsToRepaint = 0;
    }

    void check() {
        int junkPins[] = new int[LINE], junkCode[] = new int[LINE];
        int pinCount = 0, pico = 0;

        for (int i = 0; i < LINE; i++) {
            junkPins[i] = pins[turn - 1][i].getColor();
            junkCode[i] = combination[i];
        }
        for (int i = 0; i < LINE; i++) {
            if (junkPins[i] == junkCode[i]) {
                pins[turn + SCORE][pinCount].setColor(1);
                pinCount++;
                pico++;
                junkPins[i] = 98;
                junkCode[i] = 99;
            }
        }
        for (int i = 0; i < LINE; i++) {
            for (int j = 0; j < LINE; j++)
                if (junkPins[i] == junkCode[j]) {
                    pins[turn + SCORE][pinCount].setColor(2);
                    pinCount++;
                    junkPins[i] = 98;
                    junkCode[j] = 99;
                    j = LINE;
                }
        }
        pinsRepaint = true;
        pinsToRepaint = turn + SCORE;
        pinsRepaint = false;
        pinsToRepaint = 0;

        if (pico == LINE) {
            isPlaying = false;
        } else if (turn >= 10) {
            isPlaying = false;
        } else {
            curPin = 0;
            isRowFull = false;
            turn++;
        }
    }

    void combination() {
        for (int i = 0; i < LINE; i++) {
            combination[i] = generator.nextInt(6) + 1;
        }
    }
}

class Pin {
    private int color, X, Y, radius;

    public Pin() {
        X = 0;
        Y = 0;
        radius = 0;
        color = 0;
    }

    public Pin(int r, int c) {
        X = 0;
        Y = 0;
        radius = r;
        color = c;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int r) {
        radius = r;
    }

    public void setPosition(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public void setColor(int c) {
        color = c;
    }

    public int getColor() {
        return color;
    }
}
