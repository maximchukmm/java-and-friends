package edu.tutorials.oracle.swing.mvcgame02;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

class MainPanel extends JPanel {

    public MainPanel() {
        super(new BorderLayout());
        Model model = new Model();
        View view = new View(model);
        Control control = new Control(model, view);
        JLabel label = new JLabel("Guess what color!", JLabel.CENTER);
        this.add(label, BorderLayout.NORTH);
        this.add(view, BorderLayout.CENTER);
        this.add(control, BorderLayout.SOUTH);
    }
}
