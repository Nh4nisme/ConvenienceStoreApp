package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import dao.CaLamViec_DAO;
import dao.NhanVienCaLamViec_DAO;
import dao.TaiKhoan_DAO;
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

        String[] options = {"Change Password", "Logout", "Shift", "Cancel"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Hello, " + getDisplayName(),
                "User Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[3]
        );

        if (choice == 0) handleChangePassword();
        else if (choice == 1) handleLogout();
        else if (choice == 2) showShiftList();
    }

    private void showShiftList() {
        if (tk == null) {
            JOptionPane.showMessageDialog(this, "No user is currently logged in.");
            return;
        }

        String maNV = tk.getNhanVien().getMaNhanVien();
        List<String> shifts = new NhanVienCaLamViec_DAO().getShiftsByEmployeeID(maNV);
        System.out.println("Fetching shifts for employee ID: " + maNV);

        if (shifts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No shift records found.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String shift : shifts) {
                sb.append(shift).append("\n");
            }

            JTextArea area = new JTextArea(sb.toString());
            area.setEditable(false);
            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(500, 300));

            JOptionPane.showMessageDialog(this, scroll, "Your Shift History", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleLogout() {
        if (tk != null) {
            String maNV = tk.getNhanVien().getMaNhanVien();
            String maCa = new CaLamViec_DAO().getMaCaFromCurrentTime();
            System.out.println("Logging out for MaNV: " + maNV + ", MaCa: " + maCa);
            int result = new NhanVienCaLamViec_DAO().updateShiftOutTime(maNV, maCa);
            System.out.println("CapNhat: " + result);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Shift out successfully.");

            } else {
                JOptionPane.showMessageDialog(this, "Failed to log shift out.");
            }
        }
        // Đăng xuất người dùng
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
            String username = tk.getTenDangNhap();
            int result = new TaiKhoan_DAO().updatePassword(username, newPass);
            if (result == 1) {
                JOptionPane.showMessageDialog(this, "Password changed successfully:" + newPass);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to change password. User not found.");
            }
        }
    }

    private String getDisplayName() {
        return (tk != null) ? tk.getTenDangNhap() : "Guest";
    }
}
