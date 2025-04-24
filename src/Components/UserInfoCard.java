package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import entity.TaiKhoan;
import Session.Session;
import ui.LoginUI;

public class UserInfoCard extends JPanel {
    private final TaiKhoan tk;

    public UserInfoCard(String iconPath) {
        // Layout and styling
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Get logged-in user
        tk = Session.getInstance().getTaiKhoan();
        String title = (tk != null) ? tk.getTenDangNhap() : "Guest";
        String value = (tk != null) ? "Role: " + tk.getVaiTro() : "Not logged in";

        // Icon
        JLabel iconLabel = new JLabel(new ImageIcon(iconPath));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        // Text panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        textPanel.add(titleLabel);
        textPanel.add(valueLabel);

        // Add components to card
        add(iconLabel);
        add(textPanel);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showOptionDialog();
            }
        });
    }

    private void showOptionDialog(){
        if(tk == null) {
            JOptionPane.showMessageDialog(this, "No user login!");
            return;
        }

        String[] options = {"Change Password", "Logout", "Cancel"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Hello, " + getDisplayName(),
                "User Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[2]
        );

        if (choice == 0) handleChangePassword();
        else if (choice == 1) handleLogout();
    }

    private void handleLogout() {
        Session.getInstance().logout();
        JOptionPane.showMessageDialog(this, "Logged out successfully.");
        openLogin();
    }

    private void openLogin() {
        LoginUI loginUI = new LoginUI();
        loginUI.setVisible(true);

        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    private void handleChangePassword() {
        String newPass = JOptionPane.showInputDialog(this, "Enter new password:");
        if (newPass != null && !newPass.trim().isEmpty()) {
            // Call update password logic here
            System.out.println("Password changed to: " + newPass);
            JOptionPane.showMessageDialog(this, "Password changed successfully.");
        }
    }

    private String getDisplayName() {
        return (tk != null) ? tk.getTenDangNhap() : "Guest";
    }
}
