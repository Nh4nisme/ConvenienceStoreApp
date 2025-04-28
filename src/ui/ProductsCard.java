package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.util.Arrays;
import java.util.List;

import Components.RoundedButton;
import Components.UserInfoCard;
import dao.SanPham_DAO;
import entity.SanPham;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductsCard extends JPanel implements ActionListener {

    private Color buttonColor = new Color(67, 141, 184);
    private Color textColor = Color.WHITE;
    private RoundedButton addBtn;
    private RoundedButton updateBtn;
    SanPham_DAO dao = new SanPham_DAO();
    List<SanPham> ds = dao.getAllSanPham();
    List<String> catery = dao.getTenLoaiSanPham();
    private JTable table;
    private SanPham selectedProduct = null;
    private RoundedButton searchBtn;
    private JTextField searchField;
    ImageIcon trashIcon = new ImageIcon("./icon/trash.png");
    private String productId;
    private UserInfoCard card;

    public ProductsCard() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header Panel (Title + Employees Card)
        JPanel headerPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Products");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        headerPanel.add(card = new UserInfoCard("./icon/employee.png"), BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Top panel with search and buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));

        topPanel.add(new JLabel("Find Products:"));
        searchField = new JTextField(15);
        topPanel.add(searchField);

        addBtn = new RoundedButton("Add Products", 30);
        updateBtn = new RoundedButton("Update", 30);
        searchBtn = new RoundedButton("Search", 30);
        topPanel.add(searchBtn);
        topPanel.add(addBtn);
        topPanel.add(updateBtn);

        searchBtn.setBackground(buttonColor);
        searchBtn.setForeground(Color.WHITE);

        addBtn.setBackground(buttonColor);
        addBtn.setForeground(Color.WHITE);

        updateBtn.setBackground(buttonColor);
        updateBtn.setForeground(Color.WHITE);

        // Adding action listeners for buttons
        searchBtn.addActionListener(this);
        addBtn.addActionListener(this);
        updateBtn.addActionListener(this);

        JPanel categoryPanel = new JPanel(new GridLayout(2, 6, 10, 10));
        categoryPanel.setBackground(Color.WHITE);

        for (int i = 0; i < catery.size(); i++) {
            JLabel categoryLabel = new JLabel(catery.get(i));
            categoryLabel.setHorizontalAlignment(SwingConstants.LEFT);
            categoryLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            categoryPanel.add(categoryLabel);
        }

        topPanel.add(categoryPanel);

        String[] columns = {"", "Products ID", "Name Products", "Price", "Stocks", "Action"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Icon.class;  // Cột đầu tiên là Icon
                if (columnIndex == 5) return Icon.class;  // Cột hành động cũng là Icon
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa ô nào cả
            }
        };

        table = new JTable(model);
        table.setRowHeight(80);


        hienThiDanhSachSanPham(ds);

        table.addMouseListener(new java.awt.event.MouseAdapter() {


            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());

                if (column == 5) {
                    String productId = table.getValueAt(row, 1).toString();

                    int confirm = JOptionPane.showConfirmDialog(null,
                            "Bạn có chắc muốn xóa sản phẩm " + productId + " không?",
                            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.removeRow(row);
                    }
                } else {
                    productId = table.getValueAt(row, 1).toString();
                    System.out.println("Mã sản phẩm: " + productId);
                }
            }
        });

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

    private void hienThiDanhSachSanPham(List<SanPham> ds) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (SanPham sp : ds) {
            ImageIcon icon = new ImageIcon("./img/" + sp.getLinkAnh());
            Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);

            model.addRow(new Object[]{
                    scaledIcon,
                    sp.getMaSanPham(),
                    sp.getTenSanPham(),
                    sp.getGiaBan(),
                    sp.getSoLuongTon(),
                    trashIcon
            });
        }
    }


    private void showAddProductDialog(SanPham product) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm sản phẩm", true);
        dialog.setSize(400, 350);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtMaSP = new JTextField();
        JTextField txtTenSP = new JTextField();
        JTextField txtDonViTinh = new JTextField();
        JTextField txtGia = new JTextField();
        JTextField txtSoLuong = new JTextField();
        JTextField txtLinkAnh = new JTextField();

        JComboBox<String> comboMaLoai = new JComboBox<>();
        for (SanPham s : ds) comboMaLoai.addItem(s.getMaLoai());

        if (product != null) {
            txtMaSP.setEditable(false);
            txtMaSP.setText(product.getMaSanPham());
            txtTenSP.setText(product.getTenSanPham());
            txtDonViTinh.setText(product.getDonViTinh());
            txtGia.setText(String.valueOf(product.getGiaBan()));
            txtSoLuong.setText(String.valueOf(product.getSoLuongTon()));
            comboMaLoai.setSelectedItem(product.getMaLoai());
            txtLinkAnh.setText(product.getLinkAnh());
        }

        formPanel.add(new JLabel("Mã sản phẩm:"));
        formPanel.add(txtMaSP);
        formPanel.add(new JLabel("Tên sản phẩm:"));
        formPanel.add(txtTenSP);
        formPanel.add(new JLabel("Đơn vị tính:"));
        formPanel.add(txtDonViTinh);
        formPanel.add(new JLabel("Giá bán:"));
        formPanel.add(txtGia);
        formPanel.add(new JLabel("Số lượng tồn:"));
        formPanel.add(txtSoLuong);
        formPanel.add(new JLabel("Mã loại:"));
        formPanel.add(comboMaLoai);
        formPanel.add(new JLabel("Link ảnh:"));
        formPanel.add(txtLinkAnh);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Lưu");
        JButton cancelButton = new JButton("Hủy");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(ev -> {
            try {
                // Kiểm tra dữ liệu trống
                if (txtMaSP.getText().isEmpty() || txtTenSP.getText().isEmpty() ||
                        txtDonViTinh.getText().isEmpty() || txtGia.getText().isEmpty() ||
                        txtSoLuong.getText().isEmpty() || txtLinkAnh.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Parse giá và số lượng
                double gia = Double.parseDouble(txtGia.getText());
                int soLuong = Integer.parseInt(txtSoLuong.getText());

                SanPham newSP = new SanPham(
                        txtMaSP.getText(),
                        txtTenSP.getText(),
                        txtDonViTinh.getText(),
                        gia,
                        soLuong,
                        comboMaLoai.getSelectedItem().toString(),
                        txtLinkAnh.getText()
                );

                if (product != null) {
                    dao.capNhatSanPham(newSP);
                    updateTableRow(newSP);
                    JOptionPane.showMessageDialog(dialog, "Cập nhật sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    dao.themSanPham(newSP);
                    addTableRow(newSP);
                    JOptionPane.showMessageDialog(dialog, "Thêm sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }

                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Giá hoặc số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Đã xảy ra lỗi khi lưu sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });


        cancelButton.addActionListener(ev -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void updateTableRow(SanPham newSP) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 1).equals(newSP.getMaSanPham())) {
                model.setValueAt(newSP.getTenSanPham(), i, 2);
                model.setValueAt(newSP.getGiaBan(), i, 3);
                model.setValueAt(newSP.getSoLuongTon(), i, 4);
                model.setValueAt(new ImageIcon("./icon/trash.png"), i, 5);
                break;
            }
        }
    }

    private void addTableRow(SanPham newSP) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        ImageIcon icon = new ImageIcon("./img/" + newSP.getLinkAnh());
        Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);

        model.addRow(new Object[]{
                scaledIcon,
                newSP.getMaSanPham(),
                newSP.getTenSanPham(),
                newSP.getGiaBan(),
                newSP.getSoLuongTon(),
                new ImageIcon("./icon/trash.png")
        });
    }


    private void timKiemSanPhamTheoMa() {
        String ma = searchField.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sản phẩm để tìm.");
            hienThiDanhSachSanPham(ds);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        SanPham sp = dao.timTheoMa(ma);
        if (sp == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm có mã: " + ma);
            hienThiDanhSachSanPham(ds);
        } else {
            hienThiDanhSachSanPham(Arrays.asList(sp));
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source.equals(addBtn)) {
            showAddProductDialog(null);
        }

        if (source.equals(updateBtn)) {
            if (table.getSelectionModel().isSelectionEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để cập nhật.");
                return;
            }

            int row = table.getSelectedRow();
            String ma = table.getValueAt(row, 1).toString();
            SanPham sp = dao.timTheoMa(ma);

            if (sp == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm với mã: " + ma);
            } else {
                showAddProductDialog(sp);
            }
        }


        if(source.equals(searchBtn)) timKiemSanPhamTheoMa();
    }
}
