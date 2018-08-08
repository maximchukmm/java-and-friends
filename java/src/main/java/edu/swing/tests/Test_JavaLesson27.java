package edu.swing.tests;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;


public class Test_JavaLesson27 extends JFrame {
    JButton button1, button2, button3;
    String outputString = "";
    JTree theTree;
    DefaultMutableTreeNode documents, work, games, emails;
    DefaultMutableTreeNode fileSystem = new DefaultMutableTreeNode("C Drive");

    public Test_JavaLesson27() {
        this.setSize(400, 400);
        this.setTitle("My Seventh Form");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel thePanel = new JPanel();

        button1 = new JButton("Get Answer");
        ListenForButton lForButton = new ListenForButton();
        button1.addActionListener(lForButton);
        thePanel.add(button1);

        button2 = new JButton("Expand all");
        button2.addActionListener(lForButton);
        thePanel.add(button2);

        button3 = new JButton("Collapse all");
        button3.addActionListener(lForButton);
        thePanel.add(button3);

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
        new Test_JavaLesson27();
    }

    private DefaultMutableTreeNode addAFile(String fileName, DefaultMutableTreeNode parentFolder) {
        DefaultMutableTreeNode newFile = new DefaultMutableTreeNode(fileName);
        parentFolder.add(newFile);
        return newFile;
    }

    static void expandAll(JTree tree) {
        TreeNode root = (TreeNode) tree.getModel().getRoot();
        expandAll(tree, new TreePath(root));
    }

    static void expandAll(JTree tree, TreePath parent) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path);
            }
        }
        tree.expandPath(parent);
    }

    static void collapseAll(JTree tree) {
        TreeNode root = (TreeNode) tree.getModel().getRoot();
        collapseAll(tree, new TreePath(root));
    }

    static void collapseAll(JTree tree, TreePath parent) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                collapseAll(tree, path);
            }
        }
        tree.collapsePath(parent);
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
                outputString += "\nLast Leaf: " + theFile.getLastLeaf() + "\n";
                outputString += "First Leaf: " + theFile.getFirstLeaf() + "\n";
                outputString += "Next Leaf: " + theFile.getNextLeaf() + "\n";
                outputString += "Previous Leaf: " + theFile.getPreviousLeaf() + "\n";
                outputString += "Is Leaf: " + theFile.isLeaf() + "\n";
                outputString += "Number of leafes: " + theFile.getLeafCount() + "\n";

                outputString += "\nChildren of Node\n";
                for (Enumeration enumValue = theFile.children(); enumValue.hasMoreElements(); ) {
                    outputString += enumValue.nextElement() + "\n";
                }

                outputString += "\nPath From Root\n";
                TreeNode[] pathNodes = theFile.getPath();
                for (TreeNode indivNodes : pathNodes) {
                    outputString += indivNodes + "\n";
                }

                JOptionPane.showMessageDialog(Test_JavaLesson27.this, outputString, "Information", JOptionPane.INFORMATION_MESSAGE);
                outputString = "";
            } else if (e.getSource() == button2) {
                expandAll(theTree);
            } else if (e.getSource() == button3) {
                collapseAll(theTree);
            }
        }
    }
}
