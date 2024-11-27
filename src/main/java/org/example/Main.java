package org.example;

import org.example.component.panel.AdminPanel;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Untarian Bistro Project!");

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new AdminPanel();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        });
    }
}