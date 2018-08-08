package edu.tutorials.oracle.swing.mvcgame01;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Controller implements MouseListener, ActionListener {
    private Model model;
    private View view;

    public Controller(Model m, View v) {
        model = m;
        view = v;

        view.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        view.addMouseListener(this);
        view.checkAnswer.addActionListener(this);
        model.combination();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.checkAnswer) {
            if (model.isRowFull) {
                model.check();
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        Point mouse = new Point();

        mouse = e.getPoint();
        if (model.isPlaying) {
            if (mouse.x > 350) {
                int button = 1 + (int) ((mouse.y - 32) / 50);
                if ((button >= 1) && (button <= 5)) {
                    model.fillHole(button);
                    if (model.pinsRepaint) {
                        view.repaintPins(model.pinsToRepaint);
                    }
                }
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
