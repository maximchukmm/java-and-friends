package edu.tutorials.oracle.swing.mvcgame02;

import javax.swing.JFrame;
import java.awt.EventQueue;

/**
 *<br> http://stackoverflow.com/q/3066590/230513
 *<br> 15-Mar-2011 r8 http://stackoverflow.com/questions/5274962
 *<br> 26-Mar-2013 r17 per comment
 */
public class MVCGame implements Runnable {

    public static void main(String[] args) {
        EventQueue.invokeLater(new MVCGame());
    }

    @Override
    public void run() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MainPanel());
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}

