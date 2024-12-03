package org.example.component.frame;

import org.example.component.panel.MenuAdminPanel;
import org.example.component.panel.OrderAdminPanel;
import org.example.component.theme.ColorTheme;

import javax.swing.*;

public class AdminFrame extends JFrame {
    public AdminFrame() {
        setTitle("Admin Panel");
        setSize(1280, 770);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(ColorTheme.BACKGROUND_COLOR);
        tabbedPane.setForeground(ColorTheme.FOREGROUND_COLOR);


        // Add the MenuAdminPanel
        MenuAdminPanel menuAdminPanel = new MenuAdminPanel();
        tabbedPane.addTab("Menu Management", menuAdminPanel);

        // Add the OrderAdminPanel
        OrderAdminPanel orderAdminPanel = new OrderAdminPanel();
        tabbedPane.addTab("Order Management", orderAdminPanel);

        // Add the tabbedPane to the frame
        add(tabbedPane);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserFrame::new);
    }
}