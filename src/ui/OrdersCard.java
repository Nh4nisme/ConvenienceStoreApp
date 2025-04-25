package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.text.DecimalFormat;
import java.sql.Date;
import java.util.List;

import Components.RoundedButton;
import Components.UserInfoCard;
import dao.HoaDon_DAO;
import entity.HoaDon;
import entity.KhachHang;
import entity.SanPham;

public class OrdersCard extends JPanel {
    private JTable ordersTable, detailsTable;
    private DefaultTableModel ordersModel, detailsModel;
    private HoaDon_DAO hoaDonDAO;
    private JTextField searchField, fromDateField, toDateField;
    private Color buttonColor = new Color(67, 141, 184);
    private Color textColor = Color.WHITE;
    private UserInfoCard card;
    private JSplitPane splitPane;

    public OrdersCard() {
        hoaDonDAO = new HoaDon_DAO();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Orders Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        headerPanel.add(card = new UserInfoCard("./icon/employee.png"), BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Create the top panel with search/filter controls
        JPanel topPanel = createTopPanel();

        // Create the split pane with two tables
        splitPane = createSplitPaneWithTables();

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(splitPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        loadOrdersData();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);

        // Search components
        topPanel.add(new JLabel("Find by Customer ID:"));
        searchField = new JTextField(10);
        topPanel.add(searchField);

        RoundedButton searchBtn = new RoundedButton("Search", 30);
        searchBtn.setBackground(buttonColor);
        searchBtn.setForeground(textColor);
        searchBtn.addActionListener(e -> {
            String searchTerm = searchField.getText().trim();
            if (!searchTerm.isEmpty()) {
                searchOrdersByCustomer(searchTerm);
            } else {
                loadOrdersData();
            }
        });
        topPanel.add(searchBtn);

        // Date filter components
        topPanel.add(new JLabel("From (yyyy-MM-dd):"));
        fromDateField = new JTextField(10);
        topPanel.add(fromDateField);

        topPanel.add(new JLabel("To (yyyy-MM-dd):"));
        toDateField = new JTextField(10);
        topPanel.add(toDateField);

        RoundedButton filterBtn = new RoundedButton("Filter by Date", 30);
        filterBtn.setBackground(buttonColor);
        filterBtn.setForeground(textColor);
        filterBtn.addActionListener(e -> {
            try {
                String from = fromDateField.getText().trim();
                String to = toDateField.getText().trim();

                if (!from.isEmpty() && !to.isEmpty()) {
                    java.sql.Date fromDate = Date.valueOf(from);
                    java.sql.Date toDate = Date.valueOf(to);
                    filterOrdersByDate(fromDate, toDate);
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter both dates!");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format! Use yyyy-MM-dd");
            }
        });
        topPanel.add(filterBtn);

        // Refresh button
        RoundedButton refreshBtn = new RoundedButton("Refresh", 30);
        refreshBtn.setBackground(buttonColor);
        refreshBtn.setForeground(textColor);
        refreshBtn.addActionListener(e -> {
            searchField.setText("");
            fromDateField.setText("");
            toDateField.setText("");
            loadOrdersData();
            clearDetailsTable();
        });
        topPanel.add(refreshBtn);

        return topPanel;
    }

    private JSplitPane createSplitPaneWithTables() {
        // Create orders table (top)
        String[] ordersColumns = {"Order ID", "Date", "Total", "Customer ID", "Customer Name", "Employee ID"};
        ordersModel = new DefaultTableModel(ordersColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ordersTable = new JTable(ordersModel);
        ordersTable.setRowHeight(30);
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ordersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = ordersTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String orderId = ordersTable.getValueAt(selectedRow, 0).toString();
                        loadOrderDetails(orderId);
                    }
                }
            }
        });
        JScrollPane ordersScrollPane = new JScrollPane(ordersTable);
        ordersScrollPane.setBorder(BorderFactory.createTitledBorder("Orders List"));

     // Create details table (bottom) - Chỉ hiển thị thông tin sản phẩm
        String[] detailsColumns = {"Product ID", "Product Name", "Quantity", "Unit Price", "Total"};
        detailsModel = new DefaultTableModel(detailsColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        detailsTable = new JTable(detailsModel);
        detailsTable.setRowHeight(30);
        JScrollPane detailsScrollPane = new JScrollPane(detailsTable);
        detailsScrollPane.setBorder(BorderFactory.createTitledBorder("Order Details - Products"));

        // Create split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, ordersScrollPane, detailsScrollPane);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerLocation(300);

        return splitPane;
    }

    private void loadOrdersData() {
        ordersModel.setRowCount(0);
        List<HoaDon> list = hoaDonDAO.getAllHoaDon();
        for (HoaDon hd : list) {
            // Giả sử hoaDonDAO có phương thức getKhachHangByMa để lấy thông tin khách hàng
            KhachHang kh = hoaDonDAO.getKhachHangByMa(hd.getMaKhachHang());
            String tenKhachHang = (kh != null) ? kh.getTenKhachHang() : "Unknown";
            
            ordersModel.addRow(new Object[]{
                hd.getMaHoaDon(),
                hd.getNgayLap(),
                formatCurrency(hd.getTongTien()),
                hd.getMaKhachHang(),
                tenKhachHang,
                hd.getMaNhanVien()
            });
        }
    }

    private void searchOrdersByCustomer(String maKH) {
        ordersModel.setRowCount(0);
        List<HoaDon> list = hoaDonDAO.getHoaDonTheoKhachHang(maKH);
        for (HoaDon hd : list) {
            KhachHang kh = hoaDonDAO.getKhachHangByMa(hd.getMaKhachHang());
            String tenKhachHang = (kh != null) ? kh.getTenKhachHang() : "Unknown";
            
            ordersModel.addRow(new Object[]{
                hd.getMaHoaDon(),
                hd.getNgayLap(),
                formatCurrency(hd.getTongTien()),
                hd.getMaKhachHang(),
                tenKhachHang,
                hd.getMaNhanVien()
            });
        }
        clearDetailsTable();
    }

    private void filterOrdersByDate(java.sql.Date from, java.sql.Date to) {
        ordersModel.setRowCount(0);
        List<HoaDon> list = hoaDonDAO.getHoaDonTheoNgay(from, to);
        for (HoaDon hd : list) {
            KhachHang kh = hoaDonDAO.getKhachHangByMa(hd.getMaKhachHang());
            String tenKhachHang = (kh != null) ? kh.getTenKhachHang() : "Unknown";
            
            ordersModel.addRow(new Object[]{
                hd.getMaHoaDon(),
                hd.getNgayLap(),
                formatCurrency(hd.getTongTien()),
                hd.getMaKhachHang(),
                tenKhachHang,
                hd.getMaNhanVien()
            });
        }
        clearDetailsTable();
    }

    private void loadOrderDetails(String maHD) {
        detailsModel.setRowCount(0);
        HoaDon hd = hoaDonDAO.getHoaDonTheoMa(maHD);
        if (hd != null) {
            for (var ct : hd.getDanhSachChiTiet()) {
                // Lấy thông tin sản phẩm từ database
                SanPham sp = hoaDonDAO.getSanPhamByMa(ct.getMaSanPham());
                String tenSanPham = (sp != null) ? sp.getTenSanPham() : "Unknown";
                
                detailsModel.addRow(new Object[]{
                    ct.getMaSanPham(),     // Mã sản phẩm
                    tenSanPham,            // Tên sản phẩm
                    ct.getSoLuong(),       // Số lượng
                    formatCurrency(ct.getDonGia()),  // Đơn giá
                    formatCurrency(ct.getThanhTien()) // Thành tiền
                });
            }
        }
    }

    private void clearDetailsTable() {
        detailsModel.setRowCount(0);
    }

    private String formatCurrency(double amount) {
        DecimalFormat formatter = new DecimalFormat("#,### VNĐ");
        return formatter.format(amount);
    }
}