package edu.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class JavaLesson24 extends JFrame {
    JComboBox favouriteShows;
    JButton button1;
    String infoOnComponent = "";

    public JavaLesson24() {
        this.setSize(new Dimension(400, 400));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("My Fourth Frame");

        JPanel thePanel = new JPanel();

        String[] shows = {"Breaking Bad", "Life on Mars", "Doctor Who"};

        favouriteShows = new JComboBox(shows);
        favouriteShows.addItem("Pushing Daisies");
        thePanel.add(favouriteShows);

        button1 = new JButton("Get Answer");
        ListenForButtons lForButton = new ListenForButtons();
        button1.addActionListener(lForButton);
        thePanel.add(button1);


        this.add(thePanel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new JavaLesson24();
    }

    private class ListenForButtons implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button1) {
                favouriteShows.setEditable(true);
                infoOnComponent += "Item at 0: " + favouriteShows.getItemAt(0) + "\n";
                infoOnComponent += "Num of shows: " + favouriteShows.getItemCount() + "\n";
                infoOnComponent += "Selected ID: " + favouriteShows.getSelectedIndex() + "\n";
                infoOnComponent += "Selected Value: " + favouriteShows.getSelectedItem() + "\n";
                infoOnComponent += "Are Editable: " + favouriteShows.isEditable() + "\n";
                JOptionPane.showMessageDialog(JavaLesson24.this, infoOnComponent, "Information", JOptionPane.INFORMATION_MESSAGE);
                infoOnComponent = "";
            }
        }
    }
}
