package ui;

import javax.swing.*;
import java.awt.*;
import connect.ConnectDB;
import dao.SanPham_DAO;
import entity.SanPham;

public class Home extends JFrame {
    public static JPanel cardPanel;
    public static CardLayout cardLayout;

    public Home() {
        setTitle("24 STORE Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

//        CardLayout để chứa các card
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(new DashBoardCard(), "Dashboard");
        cardPanel.add(new ProductsCard(), "Products");
        cardPanel.add(new CustomersCard(), "Customers");
        cardPanel.add(new OnBoardCard(), "Onboard");
        add(new SiderBar(cardLayout, cardPanel), BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

//    public static void main(String[] args) {
//        // Kết nối cơ sở dữ liệu trước khi khởi động UI
//        ConnectDB.getInstance().connect();
//
//        // Chạy giao diện UI trong một luồng riêng biệt
//        SwingUtilities.invokeLater(() -> {
//            new Home();
//            // Có thể load sản phẩm ở đây nếu cần truyền sang UI
//        });
//
//        // Tạo đối tượng dao và lấy danh sách sản phẩm
//        SanPham_DAO dao = new SanPham_DAO();
//        java.util.List<SanPham> list = dao.getAllSanPham();
//
//        // In danh sách sản phẩm ra console
//        if (list.isEmpty()) {
//            System.out.println("Không có sản phẩm nào.");
//        } else {
//            for (SanPham sp : list) {
//                System.out.println(sp);  // In ra thông tin sản phẩm
//            }
//        }
//    }


}
