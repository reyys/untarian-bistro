package org.example.component.panel;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import org.example.component.theme.ColorTheme;
import org.example.entity.Item;
import org.example.repository.ItemRepository;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;

public class MenuAdminPanel extends JPanel {
    private final ItemRepository itemRepository = new ItemRepository();

    private JTable itemsTable;
    private int selectedRow = -1;
    private JTextField nameField, priceField, stockField;
    private JTextArea descriptionArea;
    private JLabel imagePreviewLabel;
    private File selectedLocalImageFile;

    public MenuAdminPanel() {
        setLayout(new BorderLayout(10, 10));
        createTable();
        createForm();
    }



    private void createTable() {
        String[] columnNames = {"ID", "Name", "Price", "Description", "Image URL", "Stock", "Created At",
                "Updated At"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        itemsTable = new JTable(tableModel);
        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemsTable.getTableHeader().setReorderingAllowed(false);
        itemsTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && itemsTable.getSelectedRow() != -1) {
                selectedRow = itemsTable.getSelectedRow();
                nameField.setText((String) itemsTable.getValueAt(selectedRow, 1));
                priceField.setText(String.valueOf(itemsTable.getValueAt(selectedRow, 2)));
                descriptionArea.setText((String) itemsTable.getValueAt(selectedRow, 3));
                stockField.setText(String.valueOf(itemsTable.getValueAt(selectedRow, 5)));

                if (itemsTable.getValueAt(selectedRow, 4) == null
                        || ((String) itemsTable.getValueAt(selectedRow, 4)).isEmpty()) {
                    imagePreviewLabel.setIcon(null);
                    imagePreviewLabel.setText("No Image");
                    return;
                }

                imagePreviewLabel.setText("Loading...");
                // https://stackoverflow.com/a/11026881
                new SwingWorker<Image, Void>() {
                    @Override
                    protected Image doInBackground() throws Exception {
                        URL url = new URI((String) itemsTable.getValueAt(selectedRow, 4)).toURL();
                        return ImageIO.read(url).getScaledInstance(200, 200, Image.SCALE_DEFAULT);
                    }

                    @Override
                    protected void done() {
                        try {
                            Image image = get();
                            imagePreviewLabel.setIcon(new ImageIcon(image));
                        } catch (Exception e) {
                            imagePreviewLabel.setIcon(null);
                        }
                    }
                }.execute();
            }
        });

        refreshTable();

        JScrollPane scrollPane = new JScrollPane(itemsTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // Price field
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        priceField = new JTextField(20);
        formPanel.add(priceField, gbc);

        // Description field
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionArea = new JTextArea(5, 20); // 5 rows, 20 columns
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(nameField.getFont());
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        formPanel.add(descriptionScrollPane, gbc);

        // Stock field
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1;
        stockField = new JTextField(20);
        formPanel.add(stockField, gbc);

        // Select Image button
        gbc.gridx = 0;
        gbc.gridy = 4;
        JButton selectImageButton = new JButton("Select Image");
        selectImageButton.addActionListener(_ -> selectImage());
        formPanel.add(selectImageButton, gbc);

        // Image preview label
        gbc.gridx = 0;
        gbc.gridy = 5;
        imagePreviewLabel = new JLabel();
        imagePreviewLabel.setPreferredSize(new Dimension(200, 200));
        imagePreviewLabel.setVerticalAlignment(SwingConstants.TOP);
        imagePreviewLabel.setBackground(Color.red);
        formPanel.add(imagePreviewLabel, gbc);

        // Button panel
        JPanel buttonPanel = getButtonPanel();

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(formPanel, BorderLayout.NORTH);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);
    }

    private JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(_ -> clearForm());
        buttonPanel.add(clearButton);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(_ -> saveItem());
        buttonPanel.add(saveButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(_ -> deleteItem());
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }

    private void saveItem() {
        String name = nameField.getText();
        String description = descriptionArea.getText();
        double price;
        int stock;
        String imageUrl;

        if (name.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            price = Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            stock = Integer.parseInt(stockField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid stock", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedLocalImageFile != null) {
            // Only upload new image if one was selected
            imageUrl = uploadImage();
        } else if (selectedRow != -1) {
            // Keep existing image URL if updating and no new image selected
            imageUrl = (String) itemsTable.getValueAt(selectedRow, 4);
        } else {
            // If no image selected and not updating, show error
            JOptionPane.showMessageDialog(this, "Please select an image", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedRow == -1) {
            // Insert
            itemRepository.save(new Item(name, price, description, imageUrl, stock));
            System.out.println("Inserting new item");
        } else {
            // Update
            int id = (int) itemsTable.getValueAt(selectedRow, 0);
            itemRepository.update(new Item(id, name, price, description, imageUrl, stock));
            System.out.println("Updating item with ID: " + id);
        }

        refreshTable();
        clearForm();
    }

    private void selectImage() {
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        j.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Image (.jpg, .jpeg, .png)", "jpg", "jpeg",
                "png");
        j.addChoosableFileFilter(restrict);

        int r = j.showSaveDialog(null);

        if (r == JFileChooser.APPROVE_OPTION) {
            selectedLocalImageFile = j.getSelectedFile();
            ImageIcon imageIcon = new ImageIcon(selectedLocalImageFile.getAbsolutePath());
            Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imagePreviewLabel.setIcon(new ImageIcon(image));
            // imageUrlField.setText(selectedLocalImageFile.getAbsolutePath());
        } else {
            selectedLocalImageFile = null;
            // if the user cancels the file chooser, the image preview should be cleared
            // only if there is no image
            if (imagePreviewLabel.getIcon() == null) {
                imagePreviewLabel.setText("No Image");
            }
        }
    }

    private String uploadImage() {
        System.out.println("Uploading Image: " + selectedLocalImageFile.getName());

        ImageKit.getInstance().setConfig(new Configuration(
                "public_kwlMd/3tGzJ2m9nWlDslzAl4j78=",
                "private_rvaatW1uPU9i0iPi4JoJ9bbR+x8=",
                "https://ik.imagekit.io/reyys"));

        try {
            FileCreateRequest fileCreateRequest = new FileCreateRequest(
                    Files.readAllBytes(selectedLocalImageFile.toPath()),
                    selectedLocalImageFile.getName());

            Result result = ImageKit.getInstance().upload(fileCreateRequest);
            return result.getUrl();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "";
        }
    }

    private void deleteItem() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) itemsTable.getValueAt(selectedRow, 0);
        itemRepository.deleteById(id);

        refreshTable();
        clearForm();
    }

    private void clearForm() {
        itemsTable.clearSelection();
        nameField.setText("");
        priceField.setText("");
        descriptionArea.setText("");
        stockField.setText("");
        imagePreviewLabel.setText("");
        imagePreviewLabel.setIcon(null);
        selectedLocalImageFile = null;
    }

    private void refreshTable() {
        DefaultTableModel tableModel = (DefaultTableModel) itemsTable.getModel();
        tableModel.setRowCount(0);
        for (Item item : itemRepository.findAll()) {
            tableModel.addRow(new Object[]{item.getId(), item.getName(), item.getPrice(), item.getDescription(),
                    item.getImageUrl(), item.getStock(), item.getCreatedAt(), item.getUpdatedAt()});
        }
    }
}