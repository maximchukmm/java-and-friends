package edu.tutorials.oracle.swing.mvcgame02;

import java.util.Observable;
import java.util.Random;

/**
 * Model
 */
class Model extends Observable {

    private static final Random rnd = new Random();
    private static final Piece[] pieces = Piece.values();
    private Piece hidden = init();

    private Piece init() {
        return pieces[rnd.nextInt(pieces.length)];
    }

    public void reset() {
        hidden = init();
        setChanged();
        notifyObservers();
    }

    public void check(Piece guess) {
        setChanged();
        notifyObservers(guess.equals(hidden));
    }
}
