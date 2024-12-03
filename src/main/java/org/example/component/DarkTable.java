package org.example.component;

import org.example.component.theme.ColorTheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DarkTable extends JTable {
    public DarkTable() {
        setFillsViewportHeight(true);

        // Table customization
        getTableHeader().setOpaque(false);
        getTableHeader().setBackground(ColorTheme.BACKGROUND_COLOR);
        getTableHeader().setForeground(ColorTheme.FOREGROUND_COLOR);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getTableHeader().setReorderingAllowed(false);

        // Cell customization
        setRowHeight(30);
        setGridColor(ColorTheme.BORDER_COLOR);
        setBackground(ColorTheme.CARD_COLOR);
        setForeground(ColorTheme.FOREGROUND_COLOR);
    }

    public DarkTable(DefaultTableModel tableModel) {
        this();
        setModel(tableModel);
    }
}
