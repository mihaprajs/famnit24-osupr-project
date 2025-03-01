package osupr.project;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private final TestRPanel panel;
    RCallerTest test = new RCallerTest();

    GUI(){
        setTitle("OSUPR Project");
        setLayout(new BorderLayout());
        setSize(600, 400);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        panel = new TestRPanel();
        mainPanel.add(panel, "panel");

        add(mainPanel);

        menuBar();
        testR();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new GUI();
    }

    private void menuBar(){
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem testSetUp = new JMenuItem("Test SetUp");
        testSetUp.addActionListener(e -> testR());
        fileMenu.add(testSetUp);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
    }

    public void testR() {
        double result = test.testSetUp();

        if (result != 3.0) {
            panel.testSetUpLabel.setText("Please make sure everything is installed correctly.");
        }else
            panel.testSetUpLabel.setText("Everything is ok!");
    }
}