package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Components.RoundedButton;
import Components.UserInfoCard;
import dao.KhachHang_DAO;
import entity.KhachHang;

public class CustomersCard extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private KhachHang_DAO khachHangDAO;
    private JTextField searchField;
    private Color buttonColor = new Color(67, 141, 184);
    private Color textColor = Color.WHITE;
    private UserInfoCard card;
    
    public CustomersCard() {
        khachHangDAO = new KhachHang_DAO();
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header Panel (Title + User Info Card)
        JPanel headerPanel = new JPanel(new BorderLayout());
        
        JLabel title = new JLabel("Customers");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        headerPanel.add(card = new UserInfoCard("./icon/employee.png"), BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Panel chức năng (tìm kiếm, thêm, xóa)
        JPanel topPanel = createTopPanel();
        
        // Bảng hiển thị khách hàng
        JPanel tablePanel = createTablePanel();
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Load dữ liệu khách hàng
        loadKhachHangData();
    }
    
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Tìm kiếm
        topPanel.add(new JLabel("Find Customer:"));
        searchField = new JTextField(15);
        topPanel.add(searchField);
        
        // Nút tìm kiếm
        RoundedButton searchBtn = new RoundedButton("Search", 30);
        searchBtn.setBackground(buttonColor);
        searchBtn.setForeground(textColor);
        searchBtn.addActionListener(e -> {
            String searchTerm = searchField.getText().trim();
            if (!searchTerm.isEmpty()) {
                searchKhachHang(searchTerm);
            } else {
                loadKhachHangData();
            }
        });
        topPanel.add(searchBtn);
        
        // Nút thêm mới
        RoundedButton addBtn = new RoundedButton("Add new Customer", 30);
        addBtn.setBackground(buttonColor);
        addBtn.setForeground(textColor);
        addBtn.addActionListener(e -> showAddKhachHangDialog());
        topPanel.add(addBtn);
        
        // Nút sửa (chỉ hoạt động khi có hàng được chọn)
        RoundedButton editBtn = new RoundedButton("Edit", 30);
        editBtn.setBackground(buttonColor);
        editBtn.setForeground(textColor);
        editBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String maKH = table.getValueAt(selectedRow, 0).toString();
                showEditKhachHangDialog(maKH);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để sửa!");
            }
        });
        topPanel.add(editBtn);
        
        // Nút xóa (chỉ hoạt động khi có hàng được chọn)
        RoundedButton deleteBtn = new RoundedButton("Delete", 30);
        deleteBtn.setBackground(buttonColor);
        deleteBtn.setForeground(textColor);
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String maKH = table.getValueAt(selectedRow, 0).toString();
                deleteKhachHang(maKH);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xóa!");
            }
        });
        topPanel.add(deleteBtn);
        
        // Nút làm mới
        RoundedButton refreshBtn = new RoundedButton("Refresh", 30);
        refreshBtn.setBackground(buttonColor);
        refreshBtn.setForeground(textColor);
        refreshBtn.addActionListener(e -> {
            searchField.setText("");
            loadKhachHangData();
        });
        topPanel.add(refreshBtn);
        
        return topPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        
        // Các cột của bảng
        String[] columns = {"ID Customers", "Name", "Phone", "Accumulated points"};
        
        // Tạo model cho bảng (không cho phép chỉnh sửa)
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };
        
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    private void loadKhachHangData() {
        // Xóa dữ liệu cũ
        model.setRowCount(0);
        
        // Lấy danh sách khách hàng từ DAO
        List<KhachHang> dsKhachHang = khachHangDAO.getAllKhachHang();
        
        // Thêm dữ liệu vào bảng
        for (KhachHang kh : dsKhachHang) {
            model.addRow(new Object[]{
                kh.getMaKhachHang(),
                kh.getTenKhachHang(),
                kh.getSoDienThoai(),
                kh.getDiemTichLuy()
            });
        }
    }
    
    private void searchKhachHang(String searchTerm) {
        // Xóa dữ liệu cũ
        model.setRowCount(0);
        
        // Tìm khách hàng theo tên
        List<KhachHang> dsKhachHang = khachHangDAO.timTheoTen(searchTerm);
        
        // Thêm dữ liệu vào bảng
        for (KhachHang kh : dsKhachHang) {
            model.addRow(new Object[]{
                kh.getMaKhachHang(),
                kh.getTenKhachHang(),
                kh.getSoDienThoai(),
                kh.getDiemTichLuy()
            });
        }
    }
    
    
    private void showAddKhachHangDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Khách Hàng", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Các trường nhập liệu
        JTextField maField = new JTextField(10);
        JTextField tenField = new JTextField(20);
        JTextField sdtField = new JTextField(20);
        JTextField diemField = new JTextField("0", 20);
        
        formPanel.add(new JLabel("Mã Khách Hàng:"));
        formPanel.add(maField);
        formPanel.add(new JLabel("Tên Khách Hàng:"));
        formPanel.add(tenField);
        formPanel.add(new JLabel("Số Điện Thoại:"));
        formPanel.add(sdtField);
        formPanel.add(new JLabel("Điểm Tích Lũy:"));
        formPanel.add(diemField);
        
        // Panel nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        RoundedButton saveButton = new RoundedButton("Lưu", 30);
        saveButton.setBackground(buttonColor);
        saveButton.setForeground(textColor);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String ma = maField.getText().trim();
                    String ten = tenField.getText().trim();
                    String sdt = sdtField.getText().trim();
                    double diem = Double.parseDouble(diemField.getText().trim());
                    
                    if (ten.isEmpty() || sdt.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ thông tin!");
                        return;
                    }
                    
                    KhachHang kh = new KhachHang(ma, ten, sdt, diem);
                    if (khachHangDAO.themKhachHang(kh)) {
                        JOptionPane.showMessageDialog(dialog, "Thêm khách hàng thành công!");
                        dialog.dispose();
                        loadKhachHangData();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Thêm khách hàng thất bại!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Điểm tích lũy phải là số!");
                }
            }
        });
        
        RoundedButton cancelButton = new RoundedButton("Hủy", 30);
        cancelButton.setBackground(Color.GRAY);
        cancelButton.setForeground(textColor);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void showEditKhachHangDialog(String maKH) {
        // Tìm khách hàng theo mã
        List<KhachHang> dsKhachHang = khachHangDAO.getAllKhachHang();
        KhachHang khachHang = null;
        
        for (KhachHang kh : dsKhachHang) {
            if (kh.getMaKhachHang().equals(maKH)) {
                khachHang = kh;
                break;
            }
        }
        
        if (khachHang == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!");
            return;
        }
        
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Cập Nhật Khách Hàng", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Các trường nhập liệu
        JTextField tenField = new JTextField(khachHang.getTenKhachHang(), 20);
        JTextField sdtField = new JTextField(khachHang.getSoDienThoai(), 20);
        JTextField diemField = new JTextField(String.valueOf(khachHang.getDiemTichLuy()), 20);
        
        formPanel.add(new JLabel("Tên Khách Hàng:"));
        formPanel.add(tenField);
        formPanel.add(new JLabel("Số Điện Thoại:"));
        formPanel.add(sdtField);
        formPanel.add(new JLabel("Điểm Tích Lũy:"));
        formPanel.add(diemField);
        
        // Panel nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        final KhachHang finalKhachHang = khachHang;
        RoundedButton saveButton = new RoundedButton("Lưu", 30);
        saveButton.setBackground(buttonColor);
        saveButton.setForeground(textColor);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String ten = tenField.getText().trim();
                    String sdt = sdtField.getText().trim();
                    double diem = Double.parseDouble(diemField.getText().trim());
                    
                    if (ten.isEmpty() || sdt.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ thông tin!");
                        return;
                    }
                    
                    finalKhachHang.setTenKhachHang(ten);
                    finalKhachHang.setSoDienThoai(sdt);
                    finalKhachHang.setDiemTichLuy(diem);
                    
                    if (khachHangDAO.capNhatKhachHang(finalKhachHang)) {
                        JOptionPane.showMessageDialog(dialog, "Cập nhật khách hàng thành công!");
                        dialog.dispose();
                        loadKhachHangData();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Cập nhật khách hàng thất bại!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Điểm tích lũy phải là số!");
                }
            }
        });
        
        RoundedButton cancelButton = new RoundedButton("Hủy", 30);
        cancelButton.setBackground(Color.GRAY);
        cancelButton.setForeground(textColor);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void deleteKhachHang(String maKH) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa khách hàng này không?",
                "Xác Nhận Xóa",
                JOptionPane.YES_NO_OPTION);
                
        if (confirm == JOptionPane.YES_OPTION) {
            if (khachHangDAO.xoaKhachHang(maKH)) {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
                loadKhachHangData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại!");
            }
        }
    }
}