package ui;

import Components.UserInfoCard;
import entity.TaiKhoan;
import Session.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class DashBoardCard extends JPanel {

    private final String[][] weeks = {
        {"Week1", "xxx"}, {"Week2", "xxx"}, {"Week3", "xxx"}
    };
    private final String[][] items = {
        {"Soda", "xxx"}, {"Cookies", "xxx"}, {"Condom", "xxx"}
    };
    private final String[][] employees = {
        {"xxx", "ID"}, {"xxx", "ID"}, {"xxx", "ID"}
    };

    private final UserInfoCard card;

    public DashBoardCard() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel lblDashboard = new JLabel("Dashboard");
        lblDashboard.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerPanel.add(lblDashboard, BorderLayout.WEST);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        TaiKhoan tk = Session.getInstance().getTaiKhoan();
        statsPanel.add(createCard("Total Sales", "x.xxx", "./icon/sales.png"));
        statsPanel.add(createCard("Products", "xxxxx", "./icon/products.png"));
        statsPanel.add(createCard("Orders", "x.xxx", "./icon/orders.png"));
        statsPanel.add(card = new UserInfoCard("./icon/employee.png"));

        JPanel grid2x2 = new JPanel(new GridLayout(2, 2, 15, 15));
        grid2x2.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        grid2x2.add(createListPanel("Top Products", items));
        grid2x2.add(createListPanel("Revenue", weeks));
        grid2x2.add(createListPanel("Top Employees", employees));
        grid2x2.add(createTablePanel());

        contentPanel.add(headerPanel);
        contentPanel.add(statsPanel);
        contentPanel.add(grid2x2);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, String value, String iconPath) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.X_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel iconLabel = new JLabel(new ImageIcon(iconPath));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textPanel.add(titleLabel);
        textPanel.add(valueLabel);

        card.add(iconLabel);
        card.add(textPanel);

        return card;
    }

    private JPanel createListPanel(String title, String[][] data) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        String[] columnNames = {"", ""};
        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setTableHeader(null);
        table.setEnabled(false);
        table.setBackground(Color.WHITE);
        table.setRowHeight(180 / data.length);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setIntercellSpacing(new Dimension(0, 1));

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(table, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("Recent Orders");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(lblTitle, BorderLayout.NORTH);

        String[] columns = {"Order ID", "Date", "Customer", "Status"};
        String[][] data = {
            {"#2366881", "DEC 30,2024 05:18", "xxxxx xxx xxxx", "completed"},
            {"#2366881", "DEC 30,2024 05:18", "xxxxx xxx xxxx", "completed"},
            {"#2366881", "DEC 30,2024 05:18", "xxxxx xxx xxxx", "completed"},
        };

        JTable table = new JTable(data, columns);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setEnabled(false);

        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
}