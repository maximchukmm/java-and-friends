package edu.tutorials.oracle.swing.mvcgame02;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Control panel
 */
class Control extends JPanel {

    private Model model;
    private View view;
    private JButton reset = new JButton("Reset");

    public Control(Model model, View view) {
        this.model = model;
        this.view = view;
        this.add(reset);
        reset.addActionListener(new ButtonHandler());
    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            if ("Reset".equals(cmd)) {
                model.reset();
            }
        }
    }
}
