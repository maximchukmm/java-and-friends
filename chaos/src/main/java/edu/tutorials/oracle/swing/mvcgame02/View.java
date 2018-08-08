package edu.tutorials.oracle.swing.mvcgame02;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * View
 */
class View extends JPanel {

    private static final String s = "Click a button.";
    private Model model;
    private ColorIcon icon = new ColorIcon(80, Color.gray);
    private JLabel label = new JLabel(s, icon, JLabel.CENTER);

    public View(Model model) {
        super(new BorderLayout());
        this.model = model;
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        this.add(label, BorderLayout.CENTER);
        this.add(genButtonPanel(), BorderLayout.SOUTH);
        model.addObserver(new ModelObserver());
    }

    private JPanel genButtonPanel() {
        JPanel panel = new JPanel();
        for (Piece p : Piece.values()) {
            PieceButton pb = new PieceButton(p);
            pb.addActionListener(new ButtonHandler());
            panel.add(pb);
        }
        return panel;
    }

    private class ModelObserver implements Observer {

        @Override
        public void update(Observable o, Object arg) {
            if (arg == null) {
                label.setText(s);
                icon.color = Color.gray;
            } else {
                if ((Boolean) arg) {
                    label.setText("Win!");
                } else {
                    label.setText("Keep trying.");
                }
            }
        }
    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            PieceButton pb = (PieceButton) e.getSource();
            icon.color = pb.piece.color;
            label.repaint();
            model.check(pb.piece);
        }
    }

    private static class PieceButton extends JButton {

        Piece piece;

        public PieceButton(Piece piece) {
            this.piece = piece;
            this.setIcon(new ColorIcon(16, piece.color));
        }
    }

    private static class ColorIcon implements Icon {

        private int size;
        private Color color;

        public ColorIcon(int size, Color color) {
            this.size = size;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.fillOval(x, y, size, size);
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }
    }
}
