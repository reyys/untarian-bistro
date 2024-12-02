package org.example.component.panel;

import org.example.entity.Item;
import org.example.entity.Order;
import org.example.entity.OrderItem;
import org.example.repository.ItemRepository;
import org.example.repository.OrderItemRepository;
import org.example.repository.OrderRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class OrderAdminPanel extends JPanel {
    private final OrderRepository orderRepository = new OrderRepository();
    private final OrderItemRepository orderItemRepository = new OrderItemRepository();
    private final ItemRepository itemRepository = new ItemRepository();

    private final JTable ordersTable;
    private final JTable orderItemsTable;
    private final JLabel itemIdLabel;
    private final JLabel itemNameLabel;
    private final JLabel itemDescriptionLabel;
    private final JLabel itemPriceLabel;
    private final JLabel itemImageLabel;

    public OrderAdminPanel() {
        setLayout(new BorderLayout());

        // Orders table
        String[] orderColumnNames = {"ID", "Total Price", "Table Number", "Created At", "Updated At"};
        DefaultTableModel ordersTableModel = new DefaultTableModel(orderColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ordersTable = new JTable(ordersTableModel);
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ordersTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && ordersTable.getSelectedRow() != -1) {
                int selectedRow = ordersTable.getSelectedRow();
                int orderId = (int) ordersTable.getValueAt(selectedRow, 0);
                updateOrderItemsTable(orderId);
            }
        });

        JScrollPane ordersScrollPane = new JScrollPane(ordersTable);
        add(ordersScrollPane, BorderLayout.CENTER);

        // Order items table
        String[] orderItemColumnNames = {"ID", "Order ID", "Item ID"};
        DefaultTableModel orderItemsTableModel = new DefaultTableModel(orderItemColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        orderItemsTable = new JTable(orderItemsTableModel);
        orderItemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderItemsTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && orderItemsTable.getSelectedRow() != -1) {
                int selectedRow = orderItemsTable.getSelectedRow();
                int itemId = (int) orderItemsTable.getValueAt(selectedRow, 2);
                updateItemDetailsPanel(itemId);
            }
        });

        JScrollPane orderItemsScrollPane = new JScrollPane(orderItemsTable);
        add(orderItemsScrollPane, BorderLayout.SOUTH);

        // Item details panel
        JPanel itemDetailsPanel = new JPanel();
        itemDetailsPanel.setLayout(new BoxLayout(itemDetailsPanel, BoxLayout.Y_AXIS));
        itemDetailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        itemIdLabel = new JLabel();
        itemNameLabel = new JLabel();
        itemDescriptionLabel = new JLabel();
        itemPriceLabel = new JLabel();
        itemImageLabel = new JLabel();

        itemDetailsPanel.add(itemIdLabel);
        itemDetailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        itemDetailsPanel.add(itemNameLabel);
        itemDetailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        itemDetailsPanel.add(itemDescriptionLabel);
        itemDetailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        itemDetailsPanel.add(itemPriceLabel);
        itemDetailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        itemDetailsPanel.add(itemImageLabel);

        add(itemDetailsPanel, BorderLayout.EAST);

        // Load orders
        loadOrders();
    }

    private void loadOrders() {
        List<Order> orders = orderRepository.findAll();
        DefaultTableModel ordersTableModel = (DefaultTableModel) ordersTable.getModel();
        ordersTableModel.setRowCount(0); // Clear existing rows
        for (Order order : orders) {
            ordersTableModel.addRow(new Object[]{
                    order.getId(),
                    order.getTotalPrice(),
                    order.getTableNumber(),
                    order.getCreatedAt(),
                    order.getUpdatedAt()
            });
        }
    }

    private void updateOrderItemsTable(int orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        DefaultTableModel orderItemsTableModel = (DefaultTableModel) orderItemsTable.getModel();
        orderItemsTableModel.setRowCount(0); // Clear existing rows
        for (OrderItem orderItem : orderItems) {
            orderItemsTableModel.addRow(new Object[]{
                    orderItem.getId(),
                    orderItem.getOrderId(),
                    orderItem.getItemId(),
            });
        }
    }

    private void updateItemDetailsPanel(int itemId) {
        Item item = itemRepository.findById(itemId);
        if (item != null) {
            itemIdLabel.setText("ID: " + item.getId());
            itemNameLabel.setText("Name: " + item.getName());
            itemDescriptionLabel.setText("Description: " + item.getDescription());
            itemPriceLabel.setText("Price: Rp" + item.getPrice());

            try {
                URL url = new URI(item.getImageUrl()).toURL();
                ImageIcon imageIcon = new ImageIcon(url);
                Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                itemImageLabel.setIcon(new ImageIcon(image));
            } catch (Exception e) {
                itemImageLabel.setIcon(null);
            }
        } else {
            itemIdLabel.setText("ID: ");
            itemNameLabel.setText("Name: ");
            itemDescriptionLabel.setText("Description: ");
            itemPriceLabel.setText("Price: ");
            itemImageLabel.setIcon(null);
        }
    }
}