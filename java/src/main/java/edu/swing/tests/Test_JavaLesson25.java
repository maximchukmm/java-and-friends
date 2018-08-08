package edu.swing.tests;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Test_JavaLesson25 extends JFrame {
    JButton button1, button2;
    String infoOnComponents = "";
    JList favouriteMovies, favouriteColors;
    DefaultListModel defListModel = new DefaultListModel();
    JScrollPane scroller;

    public Test_JavaLesson25() {
        this.setSize(new Dimension(250, 400));
        this.setLocationRelativeTo(null);
        this.setTitle("My Fifth Frame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel thePanel = new JPanel();

        button1 = new JButton("Get Answer");
        ListenForButton lForButton = new ListenForButton();
        button1.addActionListener(lForButton);
        thePanel.add(button1);

        String[] movies = {"Matrix", "Minority Report", "Big", "Back To The Future"};

        favouriteMovies = new JList(movies);
        favouriteMovies.setFixedCellHeight(30);
        favouriteMovies.setFixedCellWidth(150);
        favouriteMovies.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        thePanel.add(favouriteMovies);

        //String[] colors = {"Black", "Blue", "Orange", "Yellow", "White", "Pink", "Red", "Green"};
        String[] colors = {"10", "01", "04", "02", "09", "08", "07"};

        for (String color : colors) {
            defListModel.addElement(color);
        }

        favouriteColors = new JList(defListModel);
        favouriteColors.setVisibleRowCount(4);
        favouriteColors.setFixedCellHeight(30);
        favouriteColors.setFixedCellWidth(150);

        scroller = new JScrollPane(favouriteColors,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        thePanel.add(scroller);

        button2 = new JButton("Sort colors");
        button2.addActionListener(lForButton);
        thePanel.add(button2);

        this.add(thePanel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Test_JavaLesson25();
    }

    private class ListenForButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button1) {
                if (defListModel.contains("Black")) infoOnComponents += "Black is here\n";
                if (!defListModel.isEmpty()) infoOnComponents += "Isn't empty\n";

                infoOnComponents += "Elements in the list: " + defListModel.size() + "\n";
                infoOnComponents += "Last element: " + defListModel.lastElement() + "\n";
                infoOnComponents += "Firts element: " + defListModel.firstElement() + "\n";
                infoOnComponents += "In index one: " + defListModel.get(1) + "\n";

                Object[] arrayOfList = defListModel.toArray();
                for (Object color : arrayOfList) infoOnComponents += color + "\n";

                JOptionPane.showMessageDialog(Test_JavaLesson25.this, infoOnComponents, "Information", JOptionPane.INFORMATION_MESSAGE);

                infoOnComponents = "";
            }
            if (e.getSource() == button2) {
                Object[] objects = defListModel.toArray();
                String[] strings = new String[objects.length];
                for (int i = 0; i < objects.length; i++) strings[i] = (String) objects[i];

                for (int i = 0; i < strings.length; i++) {
                    String min = strings[i];
                    int imin = i;
                    for (int j = i + 1; j < strings.length; j++) {
                        if (min.compareTo(strings[j]) > 0) {
                            min = strings[j];
                            imin = j;
                        }
                    }
                    if (imin != i) {
                        String temp = strings[i];
                        strings[i] = min;
                        strings[imin] = temp;
                    }
                }

                defListModel.removeAllElements();
                for (String s : strings) defListModel.addElement(s);
            }
        }
    }
}
