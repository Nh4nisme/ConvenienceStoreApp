package ui;

import Components.UserInfoCard;
import entity.SanPham;
import entity.TaiKhoan;
import Session.Session;
import dao.HoaDon_DAO;
import dao.SanPham_DAO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;

public class DashBoardCard extends JPanel {
    private SanPham_DAO daoSP = new SanPham_DAO();
    private HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
    
    private final String[][] weeks = {
        {"Week1", "xxx"}, {"Week2", "xxx"}, {"Week3", "xxx"}
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

        // Lấy dữ liệu top nhân viên trực tiếp từ hàm getTop3NhanVienDoanhThu
        String[][] employeeData = hoaDonDAO.getTop3NhanVienDoanhThu();

        TaiKhoan tk = Session.getInstance().getTaiKhoan();
        statsPanel.add(createCard("Total Sales", "x.xxx", "./icon/sales.png"));
        statsPanel.add(createCard("Products", "xxxxx", "./icon/products.png"));
        statsPanel.add(createCard("Orders", String.valueOf(hoaDonDAO.getCurrentOrderCount()), "./icon/orders.png"));
        statsPanel.add(card = new UserInfoCard("./icon/employee.png"));

        JPanel grid2x2 = new JPanel(new GridLayout(2, 2, 15, 15));
        grid2x2.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        grid2x2.add(createListPanel("Top Products", daoSP.getTopSanPhamBanChayAsArray()));
        grid2x2.add(createLineChartPanel("Revenue", daoSP.getDoanhThuTuanTrongThang()));
        grid2x2.add(createListPanel("Top Employees", employeeData));
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

        String[] columnNames = {"Name", "ID"};
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
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

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

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < data.length; i++) {
            String week = data[i][0];
            double revenue = Double.parseDouble(data[i][1]);
            dataset.addValue(revenue, "Doanh Thu", week);
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Doanh Thu Theo Tuần",
                "Tuần",
                "Doanh Thu",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

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

        String[] columns = {"Order ID", "Date", "Customer", "Total Amount"};
        String[][] data = hoaDonDAO.getTop5HoaDonTongTien();

        JTable table = new JTable(data, columns);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setEnabled(false);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
}