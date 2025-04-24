package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.util.List;

import Components.RoundedButton;
import Components.UserInfoCard;
import dao.SanPham_DAO;
import entity.SanPham;

import java.awt.*;

public class ProductsCard extends JPanel {

    private Color buttonColor = new Color(67, 141, 184);
    private Color textColor = Color.WHITE;
	private RoundedButton addBtn;
	private RoundedButton removeBtn;
	SanPham_DAO dao = new SanPham_DAO();
	List<SanPham> ds = dao.getAllSanPham();
	List<String> catery = dao.getTenLoaiSanPham();
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
        JTextField searchField = new JTextField(15);
        topPanel.add(searchField);

        addBtn = new RoundedButton("Add Products", 30);
        removeBtn = new RoundedButton("Remove", 30);
        topPanel.add(addBtn);
        topPanel.add(removeBtn);

        addBtn.setBackground(buttonColor);
        addBtn.setForeground(textColor);

        removeBtn.setBackground(buttonColor);
        removeBtn.setForeground(textColor);

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


        JTable table = new JTable(model);
        table.setRowHeight(80);

        ImageIcon trashIcon = new ImageIcon("./icon/trash.png");
        for (SanPham sp : ds) {
        	ImageIcon icon = new ImageIcon("./img/"+ sp.getLinkAnh());
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
                        model.removeRow(row);
                    }
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
}
