package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductsCard extends JPanel {

    public ProductsCard() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Products");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 0));
        add(title, BorderLayout.NORTH);

        // Search + Button Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        topPanel.add(new JLabel("Find Products:"));
        JTextField searchField = new JTextField(15);
        topPanel.add(searchField);

        JButton addBtn = new JButton("Add Products");
        JButton removeBtn = new JButton("Remove");
        topPanel.add(addBtn);
        topPanel.add(removeBtn);

        for (int i = 0; i < 5; i++) {
            JButton categoryBtn = new JButton("category");
            topPanel.add(categoryBtn);
        }

        add(topPanel, BorderLayout.CENTER);

        // Table
        String[] columns = {"", "Products ID", "Name Products", "Date exp", "Stocks", "Action"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setRowHeight(30);

        // Dummy data
        for (int i = 0; i < 10; i++) {
            model.addRow(new Object[]{
                false,
                "#23668881",
                "<html><a href='#'>xxx</a></html>",
                "DEC 30,2024 05:18",
                "300",
                getActionPanel()
            });
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.SOUTH);
    }

    // Action column (edit/delete icons)
    private JPanel getActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setBackground(Color.WHITE);

        JButton editBtn = new JButton("✏️");
        JButton delBtn = new JButton("🗑️");

        editBtn.setBorderPainted(false);
        editBtn.setFocusPainted(false);
        delBtn.setBorderPainted(false);
        delBtn.setFocusPainted(false);

        panel.add(editBtn);
        panel.add(delBtn);
        return panel;
    }
}
