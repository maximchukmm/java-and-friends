package edu.swing;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;


public class JavaLesson27 extends JFrame {
    JButton button1;
    String outputString = "";
    JTree theTree;
    DefaultMutableTreeNode documents, work, games, emails;
    DefaultMutableTreeNode fileSystem = new DefaultMutableTreeNode("C Drive");

    public JavaLesson27() {
        this.setSize(400, 400);
        this.setTitle("My Seventh Form");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel thePanel = new JPanel();

        button1 = new JButton("Get Answer");
        ListenForButton lForButton = new ListenForButton();
        button1.addActionListener(lForButton);
        thePanel.add(button1);

        theTree = new JTree(fileSystem);
        theTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        theTree.setVisibleRowCount(8);

        documents = addAFile("Docs", fileSystem);
        addAFile("Taxes.exl", documents);
        addAFile("Story.txt", documents);
        addAFile("Schedule.txt", documents);

        emails = addAFile("Emails", documents);
        addAFile("CallBob.txt", emails);

        work = addAFile("Work Applications", fileSystem);
        addAFile("Spreadsheet.exe", work);
        addAFile("Wordprocessor.exe", work);

        games = addAFile("Games", fileSystem);
        addAFile("SpaceInvaders.exe", games);
        addAFile("PacMan.exe", games);

        JScrollPane scrollBox = new JScrollPane(theTree);
        Dimension d = scrollBox.getPreferredSize();
        d.width = 200;
        scrollBox.setPreferredSize(d);
        thePanel.add(scrollBox);

        this.add(thePanel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new JavaLesson27();
    }

    private DefaultMutableTreeNode addAFile(String fileName, DefaultMutableTreeNode parentFolder) {
        DefaultMutableTreeNode newFile = new DefaultMutableTreeNode(fileName);
        parentFolder.add(newFile);
        return newFile;
    }

    private class ListenForButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button1) {
                Object treeObject = theTree.getLastSelectedPathComponent();
                DefaultMutableTreeNode theFile = (DefaultMutableTreeNode) treeObject;
                String treeNode = (String) theFile.getUserObject();

                outputString += "The Selected Node: " + treeNode + "\n";
                outputString += "Number of Children: " + theFile.getChildCount() + "\n";
                outputString += "Number of Siblings: " + theFile.getSiblingCount() + "\n";
                outputString += "Parent: " + theFile.getParent() + "\n";
                outputString += "Next Node: " + theFile.getNextNode() + "\n";
                outputString += "Previous Node: " + theFile.getPreviousNode() + "\n";

                outputString += "\nChildren of Node\n";
                for (Enumeration enumValue = theFile.children(); enumValue.hasMoreElements(); ) {
                    outputString += enumValue.nextElement() + "\n";
                }

                outputString += "\nPath From Root\n";
                TreeNode[] pathNodes = theFile.getPath();
                for (TreeNode indivNodes : pathNodes) {
                    outputString += indivNodes + "\n";
                }

                JOptionPane.showMessageDialog(JavaLesson27.this, outputString, "Information", JOptionPane.INFORMATION_MESSAGE);
                outputString = "";
            }
        }
    }
}
