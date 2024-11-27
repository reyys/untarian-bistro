package org.example.component.panel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserPanel extends JFrame {

    public UserPanel() {
        setTitle("Restaurant User Panel");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Left Side: Menu List (3 columns)
        JPanel menuList = new JPanel();
        menuList.setLayout(new BoxLayout(menuList, BoxLayout.Y_AXIS));
        menuList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Top, Left, Bottom, Right


        // Heading Panel
        JPanel heading = new JPanel();
        heading.setLayout(new BorderLayout());
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        heading.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // Full width
        heading.add(new JLabel("Welcome, Reynaldo"), BorderLayout.WEST);
        heading.add(new JLabel("Discover whatever you need easily", SwingConstants.RIGHT), BorderLayout.EAST);
        menuList.add(heading);

        // List Panel
        JPanel list = new JPanel();
        list.setLayout(new GridLayout(3, 3, 10, 10)); // 3x3 grid with gaps

        // Sample data
        List<String> cardData = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            cardData.add("Item " + i);
        }

        // Dynamically generate cards
        for (String item : cardData) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            card.setPreferredSize(new Dimension(0, 200)); // Fixed height, flexible width

            ImageIcon imageIcon = new ImageIcon("src/burger.jpg");
            Image image = imageIcon.getImage();
            Image newimg = image.getScaledInstance(150, 100, Image.SCALE_DEFAULT);
            imageIcon = new ImageIcon(newimg);

            JLabel imageLabel = new JLabel(imageIcon); // Placeholder image
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel titleLabel = new JLabel(item);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

            JLabel descriptionLabel = new JLabel("Description for " + item);
            descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));

            JLabel priceLabel = new JLabel("$12.98");
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
            priceLabel.setForeground(Color.BLUE);

            card.add(imageLabel);
            card.add(Box.createRigidArea(new Dimension(0, 5))); // Spacer
            card.add(titleLabel);
            card.add(descriptionLabel);
            card.add(priceLabel);

            // Add shadow effect (if supported by Look and Feel)
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(1, 1, 4, 4, Color.LIGHT_GRAY),
                    card.getBorder()
            ));

            list.add(card);
        }

        menuList.add(list);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4; // Spanning 3 columns
        add(menuList, gbc);

        // Right Side: Menu Order (1 column)
        JPanel menuOrder = new JPanel();
        menuOrder.setLayout(new BorderLayout());
        menuOrder.add(new JLabel("Order Summary", SwingConstants.CENTER), BorderLayout.CENTER);
        menuOrder.setBackground(Color.WHITE);
        menuOrder.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        gbc.gridx = 4;
        gbc.gridwidth = 1; // Single column
        add(menuOrder, gbc);

        setVisible(true);
    }

    public static void main(String[] args) {
        UserPanel userPanel = new UserPanel();
    }
}
