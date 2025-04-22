package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ProductsCard extends JPanel {

    private Color buttonColor = new Color(67, 141, 184);
    private Color textColor = Color.WHITE;

    public ProductsCard() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header Panel (Title + Employees Card)
        JPanel headerPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Products");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel employeeCard = new JPanel();
        employeeCard.setLayout(new BoxLayout(employeeCard, BoxLayout.X_AXIS));
        employeeCard.setBackground(Color.WHITE);
        employeeCard.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel iconLabel = new JLabel(new ImageIcon("./icon/employees.png"));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Employees");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel valueLabel = new JLabel("xx");
        valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textPanel.add(titleLabel);
        textPanel.add(valueLabel);

        employeeCard.add(iconLabel);
        employeeCard.add(textPanel);
        headerPanel.add(employeeCard, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Top panel with search and buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));

        topPanel.add(new JLabel("Find Products:"));
        JTextField searchField = new JTextField(15);
        topPanel.add(searchField);

        JButton addBtn = new JButton("Add Products");
        JButton removeBtn = new JButton("Remove");
        topPanel.add(addBtn);
        topPanel.add(removeBtn);

        addBtn.setBackground(buttonColor);
        addBtn.setForeground(textColor);
        addBtn.setFocusPainted(false);
        addBtn.setOpaque(true);

        removeBtn.setBackground(buttonColor);
        removeBtn.setForeground(textColor);
        removeBtn.setFocusPainted(false);
        removeBtn.setOpaque(true);

        JPanel categoryPanel = new JPanel(new GridLayout(2, 6, 10, 10));
        categoryPanel.setBackground(Color.WHITE);

     for (int i = 0; i < 12; i++) {
         JLabel categoryLabel = new JLabel("Category " + (i + 1));
         categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
         categoryLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
         categoryPanel.add(categoryLabel);
     }

     topPanel.add(categoryPanel);
        String[] columns = {"", "Products ID", "Name Products", "Date exp", "Stocks", "Action"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class;  // Checkbox column
                if (columnIndex == 5) return Icon.class;     // Icon column
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Only allow checkbox column to be editable
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30);

        // Dummy data
        for (int i = 0; i < 10; i++) {
            ImageIcon trashIcon = new ImageIcon("./icon/trash.png"); // Icon for the "Action" column
            model.addRow(new Object[]{
                false,
                "#23668881",
                "<html><a href='#'>xxx</a></html>",
                "DEC 30,2024 05:18",
                "300",
                trashIcon
            });
        }
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.getColumnModel().getColumn(5).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                if (value instanceof Icon) {
                    label.setIcon((Icon) value);
                }
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });
        

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }
}
