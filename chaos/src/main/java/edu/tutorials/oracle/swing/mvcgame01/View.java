package edu.tutorials.oracle.swing.mvcgame01;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;

public class View extends Frame {
    Model model;
    JButton checkAnswer;
    private JPanel button;
    private static final Color COLORS[] = {Color.black, Color.white, Color.red, Color.yellow, Color.green, Color.blue, new Color(7, 254, 250)};

    public View(String name, int w, int h, Model m) {
        model = m;
        setTitle(name);
        setSize(w, h);
        setResizable(false);
        this.setLayout(new BorderLayout());

        button = new JPanel();
        button.setSize(new Dimension(400, 100));
        button.setVisible(true);
        checkAnswer = new JButton("Check");
        checkAnswer.setSize(new Dimension(200, 30));
        button.add(checkAnswer);
        this.add(button, BorderLayout.SOUTH);
        button.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(238, 238, 238));
        g.fillRect(0, 0, 400, 590);

        for (int i = 0; i < model.pins.length; i++) {
            paintPins(model.pins[i][0], g);
            paintPins(model.pins[i][1], g);
            paintPins(model.pins[i][2], g);
            paintPins(model.pins[i][3], g);
            paintPins(model.pins[i][4], g);
        }
    }

    @Override
    public void update(Graphics g) {
        if (model.isUpdate) {
            paint(g);
        } else {
            model.isUpdate = true;
            paintPins(model.pins[model.repaintPin - 1][0], g);
            paintPins(model.pins[model.repaintPin - 1][1], g);
            paintPins(model.pins[model.repaintPin - 1][2], g);
            paintPins(model.pins[model.repaintPin - 1][3], g);
            paintPins(model.pins[model.repaintPin - 1][4], g);
        }
    }

    void repaintPins(int pin) {
        model.repaintPin = pin;
        model.isUpdate = false;
        repaint();
    }

    public void paintPins(Pin p, Graphics g) {
        int X = p.getX();
        int Y = p.getY();
        int color = p.getColor();
        int radius = p.getRadius();
        int x = X - radius;
        int y = Y - radius;

        if (color > 0) {
            g.setColor(COLORS[color]);
            g.fillOval(x, y, 2 * radius, 2 * radius);
        } else {
            g.setColor(new Color(238, 238, 238));
            g.drawOval(x, y, 2 * radius - 1, 2 * radius - 1);
        }
        g.setColor(Color.black);
        g.drawOval(x, y, 2 * radius, 2 * radius);
    }
}
