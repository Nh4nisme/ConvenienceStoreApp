package ui;

import javax.swing.*;
import java.awt.*;
import connect.ConnectDB;

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

        // CardLayout để chứa các card
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(new DashBoardCard(), "Dashboard");
        cardPanel.add(new ProductsCard(), "Products");
        cardPanel.add(new CustomersCard(), "Customers");
        add(new SiderBar(cardLayout, cardPanel), BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Home::new);
        ConnectDB.getInstance().connect();
        //ConnectDB.disconnect();
    }
}
