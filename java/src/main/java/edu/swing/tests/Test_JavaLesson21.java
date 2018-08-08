package edu.swing.tests;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Test_JavaLesson21 extends JFrame {
    JButton button1;
    JTextField textField1;
    JTextArea textAreaForButton;
    JTextArea textAreaForTextField;
    JTextArea textAreaForWindow;
    JTextArea textAreaForMouse;
    int buttonClicked;
    int mouseExited;

    public Test_JavaLesson21() {
        this.setSize(400, 400);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        int xPos = (dim.width - this.getWidth()) / 2;
        int yPos = (dim.height - this.getHeight()) / 2;

        this.setLocation(xPos, yPos);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("My Second Frame");

        JPanel thePanel = new JPanel();
        thePanel.setName("thePanel");
        GridLayout gl = new GridLayout(5, 2);
        thePanel.setLayout(gl);
        this.add(thePanel);

        button1 = new JButton("Click Here");
        ListenForButton lForButton = new ListenForButton();
        button1.addActionListener(lForButton);
        thePanel.add(button1);

        textAreaForButton = new JTextArea("For Button1\n", 15, 15);
        textAreaForButton.setLineWrap(true);
        textAreaForButton.setWrapStyleWord(true);
        textAreaForButton.setEditable(false);

        JScrollPane scrollbarForTextAreaForButton = new JScrollPane(textAreaForButton,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        thePanel.add(scrollbarForTextAreaForButton);

        textField1 = new JTextField(null, 15);
        ListenForKeys lForKeys = new ListenForKeys();
        textField1.addKeyListener(lForKeys);
        thePanel.add(textField1);

        textAreaForTextField = new JTextArea("For TextField1\n", 15, 15);
        textAreaForTextField.setLineWrap(true);
        textAreaForTextField.setWrapStyleWord(true);

        JScrollPane scrollbarForTextAreaForTextField = new JScrollPane(textAreaForTextField,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        thePanel.add(scrollbarForTextAreaForTextField);

        textAreaForWindow = new JTextArea("For Window\n", 15, 15);
        textAreaForWindow.setLineWrap(true);
        textAreaForWindow.setWrapStyleWord(true);

        JScrollPane scrollbarForTextAreaForWindow = new JScrollPane(textAreaForWindow,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        thePanel.add(scrollbarForTextAreaForWindow);

        ListenForWindow lForWindow = new ListenForWindow();
        this.addWindowListener(lForWindow);

        textAreaForMouse = new JTextArea("For mouse\n", 15, 15);
        textAreaForMouse.setLineWrap(true);
        textAreaForMouse.setWrapStyleWord(true);

        JScrollPane scrollbarForTextAreaForMouse = new JScrollPane(textAreaForMouse,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        thePanel.add(scrollbarForTextAreaForMouse);

        ListenForMouse lForMouse = new ListenForMouse();
        thePanel.addMouseListener(lForMouse);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Test_JavaLesson21();
    }

    private class ListenForButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button1) {
                buttonClicked++;
                textAreaForButton.append("Button clicked " + buttonClicked + " times\n");
                textAreaForButton.setCaretPosition(textAreaForButton.getDocument().getLength());
            }
        }
    }

    private class ListenForKeys implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            textAreaForTextField.append("Key hit: " + e.getKeyChar() + "\n");
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private class ListenForWindow implements WindowListener {
        @Override
        public void windowOpened(WindowEvent e) {
            textAreaForWindow.append("Window created\n");
        }

        @Override
        public void windowClosing(WindowEvent e) {

        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {
            textAreaForWindow.append("Window is minimized\n");
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            textAreaForWindow.append("Window in normal state\n");
        }

        @Override
        public void windowActivated(WindowEvent e) {
            textAreaForWindow.append("Window is activated\n");
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            textAreaForWindow.append("Window is deactivated\n");
        }
    }

    private class ListenForMouse implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            textAreaForMouse.append("Mouse panel pos: " + e.getX() + " " + e.getY() + "\n");
            textAreaForMouse.append("Mouse screen pos: " + e.getXOnScreen() + " " + e.getYOnScreen() + "\n");
            textAreaForMouse.append("Mouse button: " + e.getButton() + "\n");
            textAreaForMouse.append("Mouse clicks: " + e.getClickCount() + "\n");
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {
            JPanel panel = (JPanel) e.getSource();
            textAreaForMouse.append("Mouse exited from " + panel.getName() + " : " + mouseExited + "\n");
            mouseExited++;
        }
    }
}

