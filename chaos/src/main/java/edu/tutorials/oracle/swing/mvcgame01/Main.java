package edu.tutorials.oracle.swing.mvcgame01;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View("Mastermind", 400, 590, model);
        Controller controller = new Controller(model, view);
        view.setVisible(true);
    }
}
