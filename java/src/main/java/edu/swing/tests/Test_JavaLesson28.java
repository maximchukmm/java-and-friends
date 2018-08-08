package edu.swing.tests;

import javax.swing.*;

public class Test_JavaLesson28 extends JFrame {
    JButton button1, button2, button3, button4, button5;

    public Test_JavaLesson28() {
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("My Eigth Form");

        button1 = new JButton("Button1");
        button2 = new JButton("Button2");
        button3 = new JButton("Button3");
        button4 = new JButton("Button4");
        button5 = new JButton("Button5");

        /*FLOW LAYOUT*/
//        JPanel thePanel = new JPanel();
//        thePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 20));
//        thePanel.add(button1);
//        thePanel.add(button2);

        /*BORDER LAYOUT*/
//        JPanel thePanel = new JPanel();
//        thePanel.setLayout(new BorderLayout());
//
////        thePanel.add(button1, BorderLayout.NORTH);
////        thePanel.add(button2, BorderLayout.SOUTH);
////        thePanel.add(button3, BorderLayout.EAST);
////        thePanel.add(button4, BorderLayout.WEST);
////        thePanel.add(button5, BorderLayout.CENTER);
//
//        JPanel thePanel2 = new JPanel();
//        thePanel2.add(button1);
//        thePanel2.add(button2);
//        thePanel.add(thePanel2, BorderLayout.NORTH);

        /*BOX LAYOUT*/
//         Box theBox =  Box.createHorizontalBox();
//         theBox.add(button1);
//         theBox.add(Box.createHorizontalStrut(4));
//         theBox.add(button2);
//         theBox.add(Box.createHorizontalGlue());
//         theBox.add(button3);
// //        theBox.add(Box.createHorizontalStrut(4));
// //        theBox.add(button4);
//         this.add(theBox);

        /*Another box layout*/
        JPanel thePanel = new JPanel();
        thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.Y_AXIS));
        thePanel.add(button1);
        thePanel.add(button2);
        thePanel.add(button3);
        this.add(thePanel);


        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Test_JavaLesson28();
    }
}
