package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GD extends JFrame {

    private JPanel siderBar;
    private final String[] menuItems = {"Dashboard", "Products", "Orders", "Accounts", "Onboard"};
    private final String[] menuIcons = {
        "dashboard.png",
        "product.png",
        "order.png",
        "account.png",
        "onboard.png"
    };

    private final Color defaultColor = new Color(198, 221, 234);
    private final Color selectedColor = new Color(255, 217, 158);
    private final Color bg = new Color(239, 238, 238);

    // Danh sách chứa tất cả các menu item để reset màu khi cần
    private final java.util.List<JPanel> menuItemList = new ArrayList<>();
	private JPanel pCenter;
	private JPanel pTop;

    public GD() {
        setTitle("24 STORE Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH); 

        createSiderBar();
        add(siderBar, BorderLayout.WEST);
        createDashBoard();
        add(pCenter, BorderLayout.CENTER);

        setVisible(true);
    }

    private void createSiderBar() {
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        int siderBarWidth = (int) (frameWidth * 0.3);
        int siderBarHeight = frameHeight;

        siderBar = new JPanel();
        siderBar.setBackground(new Color(67, 141, 184));
        siderBar.setLayout(new BoxLayout(siderBar, BoxLayout.Y_AXIS));
        siderBar.setPreferredSize(new Dimension(siderBarWidth, 0));

        for (int i = 0; i < menuItems.length; i++) {
            JPanel menuItemPanel = createMenuItem(menuIcons[i], menuItems[i], siderBarWidth, siderBarHeight);
            menuItemList.add(menuItemPanel); // lưu vào danh sách để sau này reset màu
            siderBar.add(menuItemPanel);
            siderBar.add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }

    private JPanel createMenuItem(String icon, String text, int siderBarWidth, int siderBarHeight) {
        JPanel menuItem = new JPanel();
        menuItem.setLayout(new BoxLayout(menuItem, BoxLayout.X_AXIS));
        menuItem.setBackground(defaultColor);
        menuItem.setAlignmentX(Component.RIGHT_ALIGNMENT);
        menuItem.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        int menuItemWidth = (int) (siderBarWidth * 0.9);
        int menuItemHeight = (int) (siderBarHeight * 0.11);
        menuItem.setMaximumSize(new Dimension(menuItemWidth, menuItemHeight));

        String iconPath = "./icon/" + icon;
        ImageIcon imageIcon = new ImageIcon(iconPath);
        JLabel iconLabel = new JLabel(imageIcon);

        JLabel textLabel = new JLabel("  " + text);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        menuItem.add(iconLabel);
        menuItem.add(Box.createRigidArea(new Dimension(10, 0)));
        menuItem.add(textLabel);

        // Sự kiện click
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (JPanel item : menuItemList) {
                    item.setBackground(defaultColor);
                }
                menuItem.setBackground(selectedColor);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });

        return menuItem;
    }
    
    private JPanel createDashBoard() {
        pCenter = new JPanel();
        pCenter.setLayout(new BorderLayout());
        pCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //Title
        JPanel pTitle = new JPanel();
        JLabel lblDashboard = new JLabel("Dashboard");
        lblDashboard.setFont(new Font("Segoe UI", Font.BOLD, 28));
        pTitle.add(lblDashboard);

        // Tổng panel chứa cả phần 4x1 và 2x2
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Phần trên: 4 ô nhỏ
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150)); // 👈 Chiều cao phần 4 ô nhỏ
        statsPanel.setBackground(bg);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
     // 4 ô nhỏ (thêm icon)
        statsPanel.add(createCard("Total Sales", "x.xxx", "./icon/sales.png"));
        statsPanel.add(createCard("Products", "xxxxx", "./icon/products.png"));
        statsPanel.add(createCard("Orders", "x.xxx", "./icon/orders.png"));
        statsPanel.add(createCard("Employees", "xx", "./icon/employees.png"));


        // Phần dưới: 2x2 ô lớn
        JPanel grid2x2 = new JPanel(new GridLayout(2, 2, 15, 15));
        grid2x2.setBackground(bg);
        grid2x2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        grid2x2.add(createListPanel("Top Products", new String[]{"Soda", "Cookies", "Condom"}));
        grid2x2.add(createListPanel("Revenue", new String[]{"Week 1", "Week 2", "Week 3"}));
        grid2x2.add(createListPanel("Top Employees", new String[]{"XXXXX XXXX", "XXXXX XXXX"}));
        grid2x2.add(createTablePanel());

        contentPanel.add(pTitle);
        contentPanel.add(statsPanel);
        contentPanel.add(grid2x2);

        pCenter.add(contentPanel, BorderLayout.CENTER);
        return pCenter;
    }

    
 // Tạo thẻ thống kê nhỏ (card)
    private JPanel createCard(String title, String value, String iconPath) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.X_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Icon
        JLabel iconLabel = new JLabel(new ImageIcon(iconPath));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Khoảng cách với text

        // Text
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


    // Tạo panel danh sách đơn giản
    private JPanel createListPanel(String title, String[] items) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setBackground(Color.WHITE);
        for (String item : items) {
            JLabel lblItem = new JLabel(item + "    xxx");
            lblItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            list.add(lblItem);
        }

        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(list, BorderLayout.CENTER);
        return panel;
    }

    // Tạo bảng Recent Orders
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

    
    


    public static void main(String[] args) {
        SwingUtilities.invokeLater(GD::new);
    }
}
