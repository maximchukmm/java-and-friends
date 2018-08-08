package edu.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;


public class JavaLesson26 extends JFrame {
    JButton button1;
    JSpinner spinner1, spinner2, spinner3, spinner4;
    String ouputString = "";

    public JavaLesson26() {
        this.setSize(new Dimension(400, 400));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("My Sixth Frame");
        this.setLocationRelativeTo(null);

        JPanel thePanel = new JPanel();

        button1 = new JButton("Get Answer");
        ListenForButton lForButton = new ListenForButton();
        button1.addActionListener(lForButton);
        thePanel.add(button1);

        spinner1 = new JSpinner();
        thePanel.add(spinner1);

        spinner2 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        thePanel.add(spinner2);

        String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        spinner3 = new JSpinner(new SpinnerListModel(weekDays));
        Dimension d = spinner3.getPreferredSize();
        d.width = 100;
        spinner3.setPreferredSize(d);
        thePanel.add(spinner3);

        Date todaysDate = new Date();
        spinner4 = new JSpinner(new SpinnerDateModel(todaysDate, null, null, Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner4, "dd/MM/yyyy");
        spinner4.setEditor(dateEditor);
        thePanel.add(spinner4);

        this.add(thePanel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new JavaLesson26();
    }

    private class ListenForButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button1) {
                ouputString += "Spinner 1 Value: " + spinner1.getValue() + "\n";
                ouputString += "Spinner 2 Value: " + spinner2.getValue() + "\n";
                ouputString += "Spinner 3 Value: " + spinner3.getValue() + "\n";
                ouputString += "Spinner 4 Value: " + spinner4.getValue() + "\n";

                JOptionPane.showMessageDialog(JavaLesson26.this, ouputString, "Information", JOptionPane.INFORMATION_MESSAGE);
                ouputString = "";
            }
        }
    }
}
