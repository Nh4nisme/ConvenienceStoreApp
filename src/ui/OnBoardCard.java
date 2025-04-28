package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Components.RoundedButton;
import Components.UserInfoCard;
import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.SanPham_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.SanPham;

public class OnBoardCard extends JPanel {
	SanPham_DAO daoSP = new SanPham_DAO();
	HoaDon_DAO daoHD = new HoaDon_DAO();
	KhachHang_DAO daoKH = new KhachHang_DAO();
	ChiTietHoaDon_DAO daoCT = new ChiTietHoaDon_DAO();
	
	List<SanPham> ds = daoSP.getAllSanPham();
	List<String> catery = daoSP.getTenLoaiSanPham();
	
	ImageIcon trashIcon = new ImageIcon("./icon/trash.png");
	private JTable productTable;
	private JTable cartTable;
	private DefaultTableModel cartModel;
	private JLabel totalAmountLabel;
	private RoundedButton checkoutButton;
	private DefaultTableModel productModel;
	private JTextField txtSearch;
	private JLabel lblAmount;
	private JLabel dateLabel;
	private JButton btnDelete;
	private UserInfoCard card;
	private JTextField txtPhone;
	private RoundedButton searchBtn;

	public OnBoardCard() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Header
		JPanel headerPanel = new JPanel(new BorderLayout());
		JLabel title = new JLabel("Onboard");
		title.setFont(new Font("Segoe UI", Font.BOLD, 28));
		headerPanel.add(title, BorderLayout.WEST);

//		JPanel employeeCard = new JPanel();
//		employeeCard.setLayout(new BoxLayout(employeeCard, BoxLayout.X_AXIS));
//		employeeCard.setBackground(Color.WHITE);
//		employeeCard.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
//		JLabel iconLabel = new JLabel(new ImageIcon("./icon/employees.png"));
//		iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

//		JPanel textPanel = new JPanel();
//		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
//		textPanel.setOpaque(false);
//		JLabel titleLabel = new JLabel("Employees");
//		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
//		JLabel valueLabel = new JLabel("xx");
//		valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
//		textPanel.add(titleLabel);
//		textPanel.add(valueLabel);
//		employeeCard.add(iconLabel);
//		employeeCard.add(textPanel);
		headerPanel.add(card = new UserInfoCard("./icon/employee.png"), BorderLayout.EAST);
		add(headerPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(new BorderLayout(20, 0));
		JPanel leftPanel = new JPanel(new BorderLayout(0, 10));

		JPanel searchPanel = new JPanel(new BorderLayout());
		JLabel findProductsLabel = new JLabel("Find Products:");
		txtSearch = new JTextField();
		txtSearch.setBorder(BorderFactory.createCompoundBorder(
		        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
		        BorderFactory.createEmptyBorder(5, 5, 5, 5)
		));
		searchBtn = new RoundedButton("Search", 30);
		searchBtn.setBackground(new Color(67, 141, 184));
        searchBtn.setForeground(Color.WHITE);

		searchPanel.add(findProductsLabel, BorderLayout.WEST);
		searchPanel.add(txtSearch, BorderLayout.CENTER);
		searchPanel.add(searchBtn, BorderLayout.EAST);
		searchBtn.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        timKiemSanPhamTheoMa();
		    }
		});
		
		leftPanel.add(searchPanel, BorderLayout.NORTH);


		JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		categoryPanel.setBackground(Color.LIGHT_GRAY);
		for (int i = 0; i < catery.size(); i++) {
			JLabel lblCatery = new JLabel(catery.get(i));
			lblCatery.setPreferredSize(new Dimension(95, 25));
			categoryPanel.add(lblCatery);
		}
		leftPanel.add(categoryPanel, BorderLayout.CENTER);

		// Product Table
		productModel = new DefaultTableModel(new String[] { "", "Product ID", "Name", "Price", "Stock" }, 0) {
			@Override
			public Class<?> getColumnClass(int column) {
				return column == 0 ? Boolean.class : Object.class;
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 0;
			}
		};

		hienThiDanhSachSanPham(ds);

		productTable = new JTable(productModel);
		productTable.setRowHeight(30);
		productTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane productScrollPane = new JScrollPane(productTable);
		leftPanel.add(productScrollPane, BorderLayout.SOUTH);

		// Right: Cart Panel
		JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
		JLabel orderSummaryLabel = new JLabel("Order summary");
		orderSummaryLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
		rightPanel.add(orderSummaryLabel, BorderLayout.NORTH);

		cartModel = new DefaultTableModel(new String[] { "Product", "Price", "Quantity", "" }, 0);
		cartTable = new JTable(cartModel);
		cartTable.setRowHeight(30);
		cartTable.getTableHeader().setReorderingAllowed(false);

		cartTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()) {
		    @Override
		    public boolean stopCellEditing() {
		        try {
		            int value = Integer.parseInt((String) getCellEditorValue());
		            if (value < 0) throw new NumberFormatException();
		        } catch (NumberFormatException e) {
		            JOptionPane.showMessageDialog(null, "Vui lòng nhập số nguyên dương.");
		            return false;
		        }
		        return super.stopCellEditing();
		    }
		});

		

		cartTable.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
		                                                   boolean hasFocus, int row, int column) {
		        btnDelete = new JButton(trashIcon);
		        btnDelete.setBorder(null);
		        btnDelete.setContentAreaFilled(false);
		        return btnDelete;
		    }
		});
		
		cartTable.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int row = cartTable.rowAtPoint(e.getPoint());
		        int column = cartTable.columnAtPoint(e.getPoint());

		        if (row >= 0 && column == 3) {
		            String productName = cartTable.getValueAt(row, 0).toString(); // Tên sản phẩm
		            int confirm = JOptionPane.showConfirmDialog(null,
		                    "Bạn có chắc muốn xóa sản phẩm " + productName + " không?",
		                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
		            if (confirm == JOptionPane.YES_OPTION) {
		                DefaultTableModel model = (DefaultTableModel) cartTable.getModel();
		                model.removeRow(row);
		                updateTotalAmount();

		                // Đồng bộ với bảng productTable: set checkbox thành false
		                for (int i = 0; i < productModel.getRowCount(); i++) {
		                    if (productModel.getValueAt(i, 2).toString().equals(productName)) {
		                        productModel.setValueAt(false, i, 0);
		                        break;
		                    }
		                }
		            }
		        }
		    }
		});

		cartModel.addTableModelListener(e -> {
		    if (e.getColumn() == 2) {
		        updateTotalAmount();
		    }
		});

		JScrollPane cartScrollPane = new JScrollPane(cartTable);
		cartScrollPane.setBorder(BorderFactory.createEmptyBorder());
		rightPanel.add(cartScrollPane, BorderLayout.CENTER);

		JPanel checkoutPanel = new JPanel();
		checkoutPanel.setLayout(new BoxLayout(checkoutPanel, BoxLayout.Y_AXIS));

		JPanel infoPanel = new JPanel(new GridLayout(4, 2, 5, 5));

		JLabel phone = new JLabel("Phone:");
		txtPhone = new JTextField();
		infoPanel.add(phone);
		infoPanel.add(txtPhone);
		
		JLabel totalLabel = new JLabel("Total:");
		totalAmountLabel = new JLabel("0.000 VND");
		totalAmountLabel.setHorizontalAlignment(JLabel.RIGHT);
		infoPanel.add(totalLabel);
		infoPanel.add(totalAmountLabel);

		infoPanel.add(new JLabel("Amounts:"));
		lblAmount = new JLabel("0");
		lblAmount.setHorizontalAlignment(JLabel.RIGHT);
		infoPanel.add(lblAmount);

		infoPanel.add(new JLabel("Date:"));
		dateLabel = new JLabel(
			    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
			    JLabel.RIGHT
			);

		infoPanel.add(dateLabel);

		checkoutButton = new RoundedButton("Check Out", 10);
		checkoutButton.setBackground(new Color(41, 128, 185));
		checkoutButton.setForeground(Color.WHITE);
		checkoutButton.setFocusPainted(false);
		checkoutButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
		checkoutButton.setPreferredSize(new Dimension(120, 40));

		checkoutButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        try {
		            String maHoaDon = String.format("HD%05d", daoHD.getCurrentOrderCount() + 1);
		            String maKH = null;
		            String totalText = totalAmountLabel.getText().replace(" VND", "").replace(",", "").trim();
	                double total = Double.parseDouble(totalText);

		            if (!txtPhone.getText().trim().isEmpty()) {
		                KhachHang kh = daoKH.timTheoSDT(txtPhone.getText());

		                if (kh == null) {
		                    kh = new KhachHang(txtPhone.getText());
		                    daoKH.themKhachHang(kh);
		                    maKH = kh.getMaKhachHang();
		                } else {
		                    maKH = kh.getMaKhachHang();
		                }

		                daoKH.capNhatDiemTichLuy(ABORT, total);


		            }

		            java.sql.Date today = java.sql.Date.valueOf(LocalDate.now());

		            HoaDon hd = new HoaDon(maHoaDon, "NV00001", maKH, today, total);
		            daoHD.themHoaDon(hd);

		            int count = daoCT.getCurrentOrderDetailCount();
		            int index = 1;

		            for (int i = 0; i < cartModel.getRowCount(); i++) {
		                String tenSanPham = cartModel.getValueAt(i, 0).toString();
		                double donGia = Double.parseDouble(cartModel.getValueAt(i, 1).toString());
		                int soLuong = Integer.parseInt(cartModel.getValueAt(i, 2).toString());

		                String maSanPham = daoSP.timMaSanPhamTheoTen(tenSanPham);
		                String maChiTiet = String.format("CT%05d", count + index);
		                index++;

		                ChiTietHoaDon ct = new ChiTietHoaDon(maChiTiet, maHoaDon, maSanPham, soLuong, donGia);
		                System.out.println(ct.toString());
		                if (daoCT.themChiTietHoaDon(ct)) {
		                    System.out.println("✔️ Thêm chi tiết hóa đơn thành công");

		                    if (daoSP.giamSoLuongTon(maSanPham, soLuong)) {
		                        System.out.println("✔️ Đã giảm số lượng tồn kho cho sản phẩm: " + maSanPham);
		                    } else {
		                        System.err.println("❌ Không thể giảm số lượng tồn kho cho sản phẩm: " + maSanPham);
		                    }
		                }
		            }
		            JOptionPane.showMessageDialog(null, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		            productModel.setRowCount(0);
		            ds = daoSP.getAllSanPham();
		            hienThiDanhSachSanPham(ds);
		            cartModel.setRowCount(0);

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi tạo hóa đơn.");
		        }
		    }
		});



		checkoutPanel.add(Box.createVerticalStrut(10));
		checkoutPanel.add(infoPanel);
		checkoutPanel.add(Box.createVerticalStrut(20));
		checkoutPanel.add(checkoutButton);
		rightPanel.add(checkoutPanel, BorderLayout.SOUTH);

		centerPanel.add(leftPanel, BorderLayout.CENTER);
		centerPanel.add(rightPanel, BorderLayout.EAST);
		add(centerPanel, BorderLayout.CENTER);

		productModel.addTableModelListener(e -> {
			if (e.getColumn() == 0) {
				int row = e.getFirstRow();
				boolean selected = (Boolean) productModel.getValueAt(row, 0);
				String productName = (String) productModel.getValueAt(row, 2);
				double price = Double.parseDouble(productModel.getValueAt(row, 3).toString());

				if (selected) {
					addToCart(productName, price);
				} else {
					removeFromCart(productName);
				}
			}
		});

	}

	private void addToCart(String productName, double price) {
		btnDelete = new JButton("trashIcon");

		for (int i = 0; i < cartModel.getRowCount(); i++) {
			if (cartModel.getValueAt(i, 0).equals(productName)) {
				return;
			}
		}
		cartModel.addRow(new Object[] { productName, String.format("%.3f", price), "1", "trashIcon" });
		updateCart();
	}
	
	private void removeFromCart(String productName) {
		for (int i = 0; i < cartModel.getRowCount(); i++) {
			if (cartModel.getValueAt(i, 0).equals(productName)) {
				cartModel.removeRow(i);
				break;
			}
		}
		updateCart();
	}
	
	private void updateCart() {
	    double total = 0.0;
	    int totalQuantity = 0;
	    for (int i = 0; i < cartModel.getRowCount(); i++) {
	        double price = Double.parseDouble(cartModel.getValueAt(i, 1).toString());
	        int quantity = Integer.parseInt(cartModel.getValueAt(i, 2).toString());
	        total += price * quantity;
	        totalQuantity += quantity;
	    }
	    totalAmountLabel.setText(String.format("%,.3f VND", total));
	    lblAmount.setText(String.valueOf(totalQuantity));
	}

	private void updateCart(int row, int newQuantity) {
	    cartModel.setValueAt(String.valueOf(newQuantity), row, 2);
	    updateTotalAmount();
	}

	private void updateTotalAmount() {
	    double total = 0.0;
	    int totalQuantity = 0;
	    for (int i = 0; i < cartModel.getRowCount(); i++) {
	        double price = Double.parseDouble(cartModel.getValueAt(i, 1).toString());
	        int quantity = Integer.parseInt(cartModel.getValueAt(i, 2).toString());
	        total += price * quantity;
	        totalQuantity += quantity;
	    }
	    totalAmountLabel.setText(String.format("%,.3f VND", total));
	    lblAmount.setText(String.valueOf(totalQuantity));
	}

	private void hienThiDanhSachSanPham(List<SanPham> ds) {
		for (SanPham sp : ds) {
		    productModel.addRow(
		        new Object[] { false, sp.getMaSanPham(), sp.getTenSanPham(), sp.getGiaBan(), sp.getSoLuongTon() }
		    );
		}
	}
	
	private void timKiemSanPhamTheoMa() {
        String ma = txtSearch.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sản phẩm để tìm.");
            hienThiDanhSachSanPham(ds);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);

        SanPham sp = daoSP.timTheoMa(ma);
        if (sp == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm có mã: " + ma);
            hienThiDanhSachSanPham(ds);
        } else {
            hienThiDanhSachSanPham(Arrays.asList(sp));
        }
    }
	

}
