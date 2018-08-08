package edu.swing.tests;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Test_JavaLesson24 extends JFrame {
    JComboBox favouriteShows;
    JButton button1, button2, button3;
    JTextField textField1;
    String infoOnComponent = "";

    public Test_JavaLesson24() {
        this.setSize(new Dimension(400, 400));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("My Fourth Frame");

        JPanel thePanel = new JPanel();
        thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.Y_AXIS));

        String[] shows = {"Breaking Bad", "Life on Mars", "Doctor Who"};

        favouriteShows = new JComboBox(shows);
        thePanel.add(favouriteShows);

        button1 = new JButton("Get Answer");
        ListenForButtons lForButton = new ListenForButtons();
        button1.addActionListener(lForButton);
        thePanel.add(button1);

        button2 = new JButton("Add new item");
        button2.addActionListener(lForButton);
        thePanel.add(button2);

        button3 = new JButton("Delete selected item");
        button3.addActionListener(lForButton);
        thePanel.add(button3);

        textField1 = new JTextField("1", 20);
        thePanel.add(textField1);

        this.add(thePanel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Test_JavaLesson24();
    }

    private class ListenForButtons implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button1) {
                infoOnComponent += "Item at 0: " + favouriteShows.getItemAt(0) + "\n";
                infoOnComponent += "Num of shows: " + favouriteShows.getItemCount() + "\n";
                infoOnComponent += "Selected ID: " + favouriteShows.getSelectedIndex() + "\n";
                infoOnComponent += "Selected Value: " + favouriteShows.getSelectedItem() + "\n";
                JOptionPane.showMessageDialog(Test_JavaLesson24.this, infoOnComponent, "Information", JOptionPane.INFORMATION_MESSAGE);
                infoOnComponent = "";
            } else if (e.getSource() == button2) {
                if (!textField1.getText().equals("")) {
                    try {
                        textField1.setText((new Integer(Integer.parseInt(textField1.getText()) + 1)).toString());
                        favouriteShows.insertItemAt(textField1.getText(), 0);
                        favouriteShows.setSelectedIndex(0);
                    } catch (NumberFormatException excep) {
                        System.out.println(excep);
                    }
                }
            } else if (e.getSource() == button3) {
                if (favouriteShows.getItemCount() != 0) {
                    favouriteShows.removeItemAt(favouriteShows.getSelectedIndex());
                }
            }
        }
    }
}
