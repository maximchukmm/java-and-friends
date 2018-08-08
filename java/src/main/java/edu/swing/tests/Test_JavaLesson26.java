package edu.swing.tests;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;


public class Test_JavaLesson26 extends JFrame {
    JButton button1;
    JSpinner spinner1, spinner2, spinner3, spinner4;
    JLabel label1;
    String ouputString = "";

    public Test_JavaLesson26() {
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

        ListenForSpinner lForSpinner = new ListenForSpinner();
        spinner4.addChangeListener(lForSpinner);

        label1 = new JLabel("label");
        thePanel.add(label1);

        this.add(thePanel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Test_JavaLesson26();
    }

    private class ListenForButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button1) {
                ouputString += "Spinner 1 Value: " + spinner1.getValue() + "\n";
                ouputString += "Spinner 2 Value: " + spinner2.getValue() + "\n";
                ouputString += "Spinner 3 Value: " + spinner3.getValue() + "\n";
                ouputString += "Spinner 4 Value: " + spinner4.getValue() + "\n";

                JOptionPane.showMessageDialog(Test_JavaLesson26.this, ouputString, "Information", JOptionPane.INFORMATION_MESSAGE);
                ouputString = "";
            }
        }
    }

    private class ListenForSpinner implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == spinner4) {
                label1.setText(spinner4.getValue().toString());
            }
        }
    }
}
