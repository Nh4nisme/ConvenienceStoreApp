package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import dao.KhachHang_DAO;
import entity.KhachHang;

public class CustomersCard extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private KhachHang_DAO khachHangDAO;
    private JTextField searchField;
    
    public CustomersCard() {
        khachHangDAO = new KhachHang_DAO();
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Tiêu đề
        JLabel title = new JLabel("Customers");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 0));
        add(title, BorderLayout.NORTH);
        
        // Panel chức năng (tìm kiếm, thêm, xóa)
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.CENTER);
        
        // Bảng hiển thị khách hàng
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.SOUTH);
        
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
        JButton searchBtn = new JButton("Search");
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
        JButton addBtn = new JButton("Add new Customer");
        addBtn.addActionListener(e -> showAddKhachHangDialog());
        topPanel.add(addBtn);
        
        // Nút sửa (chỉ hoạt động khi có hàng được chọn)
        JButton editBtn = new JButton("Edit");
        editBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int maKH = (int) table.getValueAt(selectedRow, 0); // Lấy mã KH từ cột 0
                showEditKhachHangDialog(maKH);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để sửa!");
            }
        });
        topPanel.add(editBtn);
        
        // Nút xóa (chỉ hoạt động khi có hàng được chọn)
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int maKH = (int) table.getValueAt(selectedRow, 0); // Lấy mã KH từ cột 0
                deleteKhachHang(maKH);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xóa!");
            }
        });
        topPanel.add(deleteBtn);
        
        // Nút làm mới
        JButton refreshBtn = new JButton("Refresh");
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
        
        // Các cột của bảng (đã bỏ cột "Actions")
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
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Các trường nhập liệu
        JTextField tenField = new JTextField(20);
        JTextField sdtField = new JTextField(20);
        JTextField diemField = new JTextField("0", 20);
        
        formPanel.add(new JLabel("Tên Khách Hàng:"));
        formPanel.add(tenField);
        formPanel.add(new JLabel("Số Điện Thoại:"));
        formPanel.add(sdtField);
        formPanel.add(new JLabel("Điểm Tích Lũy:"));
        formPanel.add(diemField);
        
        // Panel nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton saveButton = new JButton("Lưu");
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
                    
                    KhachHang kh = new KhachHang(0, ten, sdt, diem);
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
        
        JButton cancelButton = new JButton("Hủy");
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
    
    private void showEditKhachHangDialog(int maKH) {
        // Tìm khách hàng theo mã
        List<KhachHang> dsKhachHang = khachHangDAO.getAllKhachHang();
        KhachHang khachHang = null;
        
        for (KhachHang kh : dsKhachHang) {
            if (kh.getMaKhachHang() == maKH) {
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
        JButton saveButton = new JButton("Lưu");
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
        
        JButton cancelButton = new JButton("Hủy");
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
    
    private void deleteKhachHang(int maKH) {
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