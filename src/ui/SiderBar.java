package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SiderBar extends JPanel {
    private final String[] menuItems = {"Dashboard", "Products", "Customers", "Orders", "Onboard"};
    private final String[] menuIcons = {
        "dashboard.png",
        "product.png",
        "account.png",
        "order.png",
        "onboard.png"
    };
    private final String logoPath = "./icon/logo.png";
    private final Color defaultColor = new Color(198, 221, 234);
    private final Color selectedColor = new Color(255, 217, 158);
    private final List<JPanel> menuItemList = new ArrayList<>();

    // CardLayout and CardPanel references
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Constructor where we pass the CardLayout and the panel to be switched
    public SiderBar(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setBackground(new Color(67, 141, 184));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 0)); // Cố định chiều rộng thanh sidebar

        // Gọi hàm tạo sidebar
        addLogo();
        createSiderBar();

    }

    private void addLogo() {
        ImageIcon logoIcon = new ImageIcon(logoPath);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // khoảng cách top-bottom
        add(logoLabel);
    }

    private void createSiderBar() {
        int siderBarWidth = 250;  // Cố định chiều rộng SiderBar là 250px
        int siderBarHeight = 600; // Cố định chiều cao của SiderBar (ví dụ: 600px)

        for (int i = 0; i < menuItems.length; i++) {
            JPanel menuItemPanel = createMenuItem(menuIcons[i], menuItems[i], siderBarWidth, siderBarHeight);
            menuItemList.add(menuItemPanel);  // lưu vào danh sách để sau này reset màu
            add(menuItemPanel);
            add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách giữa các item
        }
    }

    private JPanel createMenuItem(String icon, String text, int siderBarWidth, int siderBarHeight) {
        JPanel menuItem = new JPanel();
        menuItem.setLayout(new BoxLayout(menuItem, BoxLayout.X_AXIS));
        menuItem.setBackground(defaultColor);
        menuItem.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuItem.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        int menuItemWidth = (int) (siderBarWidth * 0.9);  
        int menuItemHeight = (int) (siderBarHeight * 0.1);
        menuItem.setMaximumSize(new Dimension(menuItemWidth, menuItemHeight));

        String iconPath = "./icon/" + icon;
        ImageIcon imageIcon = new ImageIcon(iconPath);
        JLabel iconLabel = new JLabel(imageIcon);

        JLabel textLabel = new JLabel("  " + text);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        menuItem.add(iconLabel);
        menuItem.add(textLabel);

        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (JPanel item : menuItemList) {
                    item.setBackground(defaultColor);
                }
                // Đổi màu item đã chọn
                menuItem.setBackground(selectedColor);

                handleSwitchCard(text);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });

        return menuItem;
    }

    private void handleSwitchCard(String selectedMenu) {
        switch (selectedMenu) {
            case "Dashboard":
                cardLayout.show(cardPanel, "Dashboard");
                break;
            case "Products":
                cardLayout.show(cardPanel, "Products");
                break;
            case "Customers":
                cardLayout.show(cardPanel, "Customers");
                break;
            case "Orders":
                cardLayout.show(cardPanel, "Orders");
                break;
            case "Onboard":
                cardLayout.show(cardPanel, "Onboard");
                break;
            default:
                break;
        }
    }
}