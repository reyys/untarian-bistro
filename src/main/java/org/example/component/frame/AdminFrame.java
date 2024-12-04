package org.example.component.frame;

import org.example.component.panel.MenuAdminPanel;
import org.example.component.panel.OrderAdminPanel;
import org.example.component.theme.ColorTheme;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import java.awt.*;

public class AdminFrame extends JFrame {
    public AdminFrame() {
        setTitle("Admin Panel");
        setSize(1440, 770);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(ColorTheme.BACKGROUND_COLOR);
        tabbedPane.setForeground(ColorTheme.FOREGROUND_COLOR);
        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
                g.setColor(ColorTheme.BACKGROUND_COLOR);
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
                    boolean isSelected) {
                g.setColor(ColorTheme.BORDER_COLOR);
                g.drawRect(x, y, w, h);
            }
        });

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
}