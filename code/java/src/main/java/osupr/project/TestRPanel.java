package osupr.project;

import javax.swing.*;
import java.awt.*;

public class TestRPanel extends JPanel {
    JLabel testSetUpLabel = new JLabel("",SwingConstants.CENTER);

    public TestRPanel() {
        setLayout(new GridLayout(5, 1));
        JLabel titleLabel = new JLabel("OSUPR Project",SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(titleLabel);

        JLabel instructionLabel1 = new JLabel("Before proceeding, please test your system.",SwingConstants.CENTER);
        instructionLabel1.setFont(new Font("Arial", Font.PLAIN, 20));
        add(instructionLabel1);

        JLabel instructionLabel2 = new JLabel("To test your system go to FILE -> Test SetUp.",SwingConstants.CENTER);
        instructionLabel2.setFont(new Font("Arial", Font.PLAIN, 15));
        add(instructionLabel2);

        testSetUpLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(testSetUpLabel);

        JButton continueBtn = new JButton("Continue");
        continueBtn.addActionListener(e -> {});
        add(continueBtn);
    }
}
