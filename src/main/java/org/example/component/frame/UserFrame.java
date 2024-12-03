package org.example.component.frame;

import org.example.component.theme.ColorTheme;
import org.example.entity.Item;
import org.example.entity.Order;
import org.example.entity.OrderItem;
import org.example.repository.ItemRepository;
import org.example.repository.OrderItemRepository;
import org.example.repository.OrderRepository;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserFrame extends JFrame {

    private final OrderRepository orderRepository = new OrderRepository();
    private final OrderItemRepository orderItemRepository = new OrderItemRepository();
    private final ItemRepository itemRepository = new ItemRepository();
    private final List<OrderItem> orderItems = new ArrayList<>();
    private final JPanel orderItemsPanel = new JPanel();

    private final JTextField tableNumberField = new JTextField(10);
    private final JLabel totalPriceLabel = new JLabel("Total: Rp0");


    public UserFrame() {
        setTitle("Restaurant User Panel");
        setSize(1440, 770);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(ColorTheme.BACKGROUND_COLOR);

        // Use a dark theme for the layout
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Left Side: Menu List Panel
        JPanel menuListPanel = new JPanel();
        menuListPanel.setLayout(new BorderLayout());
        menuListPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        menuListPanel.setBackground(ColorTheme.BACKGROUND_COLOR);

        // Heading Panel
        JPanel headingPanel = new JPanel(new BorderLayout());
        headingPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        headingPanel.setBackground(ColorTheme.BACKGROUND_COLOR);

        JLabel welcomeLabel = new JLabel("Selamat Datang, Pengguna");
        welcomeLabel.setForeground(ColorTheme.FOREGROUND_COLOR);
        headingPanel.add(welcomeLabel, BorderLayout.WEST);

        JLabel discoverLabel = new JLabel("Eksplor menu terpopuler kami di Untarian Bistro", SwingConstants.RIGHT);
        discoverLabel.setForeground(ColorTheme.FOREGROUND_COLOR);
        headingPanel.add(discoverLabel, BorderLayout.EAST);

        menuListPanel.add(headingPanel, BorderLayout.NORTH);

        // Scrollable List Panel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(0, 3, 15, 15));
        listPanel.setBackground(ColorTheme.BACKGROUND_COLOR);

        // Populate menu items
        List<Item> menuItems = itemRepository.findAll();
        for (Item menuItem : menuItems) {
            JPanel card = createMenuCard(menuItem);
            listPanel.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(ColorTheme.BACKGROUND_COLOR);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        menuListPanel.add(scrollPane, BorderLayout.CENTER);
        menuListPanel.setBackground(ColorTheme.BACKGROUND_COLOR);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        add(menuListPanel, gbc);

        // Right Side: Menu Order (1 column)

        // Parent panel for menuOrderPanel
        JPanel menuOrderParentPanel = new JPanel();
        menuOrderParentPanel.setLayout(new BorderLayout());
        menuOrderParentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Adding empty border to provide padding
        menuOrderParentPanel.setBackground(ColorTheme.BACKGROUND_COLOR);

        JPanel menuOrderPanel = new JPanel();
        menuOrderPanel.setLayout(new BorderLayout());
        menuOrderPanel.setBackground(ColorTheme.BACKGROUND_COLOR);

        // Add menuOrderPanel to the parent panel
        menuOrderParentPanel.add(menuOrderPanel, BorderLayout.CENTER);

        // Panel to render the items in the order
        orderItemsPanel.setLayout(new BoxLayout(orderItemsPanel, BoxLayout.Y_AXIS));
        orderItemsPanel.setBackground(ColorTheme.BACKGROUND_COLOR);

        updateOrderItemsPanel(orderItemsPanel);

        JScrollPane orderItemsScrollPane = new JScrollPane(orderItemsPanel);
        orderItemsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        orderItemsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        orderItemsScrollPane.setBorder(BorderFactory.createEmptyBorder());
        menuOrderPanel.add(orderItemsScrollPane, BorderLayout.CENTER);
        menuOrderPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ColorTheme.BORDER_COLOR, 1),
                "Ringkasan Pesanan",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                ColorTheme.FOREGROUND_COLOR
        ));

        // Table number input panel (This will be shown above the other components)
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(ColorTheme.BACKGROUND_COLOR);
        tablePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel tableNumberLabel = new JLabel("Table Number: ");
        tableNumberLabel.setForeground(ColorTheme.FOREGROUND_COLOR);
        tablePanel.add(tableNumberLabel);
        tablePanel.add(tableNumberField);

        // Total price label
        JPanel totalPricePanel = new JPanel();
        totalPricePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        totalPricePanel.setBackground(ColorTheme.BACKGROUND_COLOR);
        totalPriceLabel.setForeground(ColorTheme.FOREGROUND_COLOR);
        totalPricePanel.add(totalPriceLabel);

        // Place Order button
        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.setForeground(Color.BLACK);
        placeOrderButton.addActionListener(e -> placeOrder());

        // Panel for the table number, total price, and place order button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(ColorTheme.BACKGROUND_COLOR);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10,10,20,10));


        // Set alignment of the bottom panel to the left
        bottomPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Set alignment for each component inside the bottom panel
        tablePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        totalPricePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        placeOrderButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add components to the bottom panel
        bottomPanel.add(tablePanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        bottomPanel.add(totalPricePanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        bottomPanel.add(placeOrderButton);

        // Add the bottom panel to the menu order panel
        menuOrderPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add menuOrderPanel to the main layout
        gbc.gridx = 6;
        gbc.gridwidth = 1;
        add(menuOrderParentPanel, gbc);

        setVisible(true);
    }

    private JPanel createMenuCard(Item item) {
        // Create the card panel
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(ColorTheme.CARD_COLOR);
        card.setBorder(BorderFactory.createLineBorder(ColorTheme.BORDER_COLOR, 1));
        card.setPreferredSize(new Dimension(0, 0));

        // Image with 100% width of the card
        ImageIcon imageIcon;
        try {
            URL url = new URL(item.getImageUrl());
            imageIcon = new ImageIcon(url);
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(350, 200, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.out.println("Failed to load image from URL: " + item.getImageUrl());
            e.printStackTrace();
            imageIcon = new ImageIcon();
        }

        // If image is invalid, load a placeholder
        if (imageIcon.getIconWidth() == -1) {
            System.out.println("Invalid image icon, loading placeholder...");
            imageIcon = new ImageIcon("burger.jpg");
        }

        // Image label for the card
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        imageLabel.setPreferredSize(new Dimension(250, 200));

        // Panel for title, description, and price with padding
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(ColorTheme.CARD_COLOR);
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title label
        JLabel titleLabel = new JLabel(item.getName());
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(ColorTheme.FOREGROUND_COLOR);

        // Description label
        JLabel descriptionLabel = new JLabel(item.getDescription());
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionLabel.setForeground(ColorTheme.FOREGROUND_COLOR);

        // Price label
        JLabel priceLabel = new JLabel("Rp " + item.getPrice());
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 12));
        priceLabel.setForeground(ColorTheme.FOREGROUND_COLOR);

        JButton addToCartButton = new JButton("Masukan ke cart");
        addToCartButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        addToCartButton.setForeground(Color.BLACK);
        addToCartButton.setMargin(new Insets(0, 0, 0, 0));
        addToCartButton.setBorderPainted(true);
        addToCartButton.setFocusPainted(false);

        // Add action listener to the button
        addToCartButton.addActionListener(_ -> {
            OrderItem orderItem = new OrderItem(0, item.getId());
            orderItems.add(orderItem);
            JOptionPane.showMessageDialog(this, item.getName() + " added to cart");

            // Update the order items panel after adding the item to the cart
            updateOrderItemsPanel(orderItemsPanel);
            recalculateTotalPrice();
        });

        // Add title, description, and price to the text panel
        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        textPanel.add(descriptionLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer

        textPanel.add(priceLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        textPanel.add(addToCartButton);

        // Add image label and text panel to the card
        card.add(imageLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(textPanel);

        return card;
    }

    private void recalculateTotalPrice() {
        double total = 0;
        for (OrderItem orderItem : orderItems) {
            Item item = itemRepository.findById(orderItem.getItemId());
            total += item.getPrice();
        }
        totalPriceLabel.setText("Total: Rp" + total);
    }

    private double calculateTotalPrice() {
        double total = 0;
        for (OrderItem orderItem : orderItems) {
            Item item = itemRepository.findById(orderItem.getItemId());
            total += item.getPrice();
        }
        return total;
    }

    // Update the Order Items panel
    private void updateOrderItemsPanel(JPanel orderItemsPanel) {
        orderItemsPanel.removeAll(); // Clear the current items in the panel

        List<Item> items = new ArrayList<>();
        orderItems.forEach(orderItem -> items.add(itemRepository.findById(orderItem.getItemId())));

        for (Item item : items) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setBackground(ColorTheme.BACKGROUND_COLOR);
            itemPanel.setPreferredSize(new Dimension(0, 150));

            // Adding padding inside the itemPanel
            itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            JLabel itemName = new JLabel(item.getName());
            itemName.setFont(new Font("Arial", Font.BOLD, 14));
            itemName.setForeground(Color.WHITE);

            JLabel itemDescription = new JLabel(item.getDescription());
            itemDescription.setFont(new Font("Arial", Font.PLAIN, 12));
            itemDescription.setForeground(Color.WHITE);

            JLabel itemPrice = new JLabel("Rp" + item.getPrice());
            itemPrice.setFont(new Font("Arial", Font.BOLD, 12));
            itemPrice.setForeground(Color.WHITE);

            // Adding the elements to the itemPanel
            itemPanel.add(itemName);
            itemPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer between title and description
            itemPanel.add(itemDescription);
            itemPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer between description and price
            itemPanel.add(itemPrice);

            // Divider for separation between items
            itemPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer between items

            // Add the item panel to the order items panel
            orderItemsPanel.add(itemPanel);

            // Create a panel to act as the divider
            JPanel divider = new JPanel();
            divider.setPreferredSize(new Dimension(0, 1));  // Set height to 1px
            divider.setBackground(Color.GRAY);  // Set background to gray

            orderItemsPanel.add(divider);

            // Add margin between item panels (similar to margin-top in Tailwind)
            orderItemsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer between each itemPanel
        }

        // Refresh the panel to show the updates
        orderItemsPanel.revalidate();
        orderItemsPanel.repaint();
    }

    private void placeOrder() {
        // Get the table number from the input field
        String tableNumberText = tableNumberField.getText().trim();
        if (tableNumberText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a table number.");
            return;
        }

        try {
            int tableNumber = Integer.parseInt(tableNumberText);
            double totalPrice = calculateTotalPrice();
            totalPriceLabel.setText("Total: Rp" + totalPrice);

            // Create an Order object
            Order order = new Order(totalPrice, tableNumber, orderItems);
            // Save the order
            order = orderRepository.save(order);

            // Save the order items
            for (OrderItem orderItem : orderItems) {
                orderItem.setOrderId(order.getId());
                orderItemRepository.save(orderItem);
            }

            JOptionPane.showMessageDialog(this, "Order placed successfully!");

            // Reset the order
            orderItems.clear();
            updateOrderItemsPanel(orderItemsPanel);
            totalPriceLabel.setText("Total: Rp0");
            tableNumberField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid table number. Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserFrame::new);
    }
}
