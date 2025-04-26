package ui;

import Components.UserInfoCard;
import entity.SanPham;
import entity.TaiKhoan;
import Session.Session;
import dao.SanPham_DAO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.util.Map;

public class DashBoardCard extends JPanel {
	private SanPham_DAO daoSP = new SanPham_DAO();

    private final String[][] weeks = {
        {"Week1", "xxx"}, {"Week2", "xxx"}, {"Week3", "xxx"}
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
        grid2x2.add(createListPanel("Top Products", daoSP.getTopSanPhamBanChayAsArray()));
        grid2x2.add(createLineChartPanel("Revenue", daoSP.getDoanhThuTuanTrongThang()));
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
    
    private JPanel createLineChartPanel(String title, String[][] data) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(lblTitle, BorderLayout.NORTH);

        // Tạo dataset cho biểu đồ
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < data.length; i++) {
            String week = data[i][0];
            double revenue = Double.parseDouble(data[i][1]);  // Chuyển doanh thu sang số
            dataset.addValue(revenue, "Doanh Thu", week);  // Dữ liệu biểu đồ
        }

        // Tạo biểu đồ đường
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Doanh Thu Theo Tuần", // Tiêu đề biểu đồ
                "Tuần",                // Tiêu đề trục X
                "Doanh Thu",           // Tiêu đề trục Y
                dataset,               // Dữ liệu biểu đồ
                PlotOrientation.VERTICAL,
                true,                  // Hiển thị Legend
                true,                  // Hiển thị tooltips
                false                  // Không hiển thị URL
        );

        // Thêm biểu đồ vào panel
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        panel.add(chartPanel, BorderLayout.CENTER);

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