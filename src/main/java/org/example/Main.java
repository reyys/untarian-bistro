package org.example;

import org.example.component.frame.AdminFrame;
import org.example.component.frame.UserFrame;
import org.example.component.theme.ColorTheme;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                createMainMenu();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        });
    }

    private static void createMainMenu() {
        JFrame frame = new JFrame("Untarian Bistro - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(ColorTheme.BACKGROUND_COLOR);
        frame.getContentPane().setForeground(ColorTheme.FOREGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Welcome to Untarian Bistro");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(ColorTheme.FOREGROUND_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(titleLabel, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setPreferredSize(new Dimension(300, 35)); // Set the same width for buttons
        buttonPanel.setBackground(ColorTheme.BACKGROUND_COLOR);

        // User Button
        JButton userButton = new JButton("User Panel");
        userButton.addActionListener(e -> {
            frame.dispose();
            new UserFrame();
        });

        // Admin Button
        JButton adminButton = new JButton("Admin Panel");
        adminButton.addActionListener(e -> {
            frame.dispose();
            new AdminFrame();
        });

        // Add buttons to the panel
        buttonPanel.add(userButton);
        buttonPanel.add(adminButton);

        // Add the button panel to the frame
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        frame.add(buttonPanel, gbc);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
