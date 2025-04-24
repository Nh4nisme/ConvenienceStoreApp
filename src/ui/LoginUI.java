package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Components.RoundedButton;
import Components.RoundedInputField;
import Components.RoundedPanel;
import Session.Session;
import entity.TaiKhoan;
import dao.TaiKhoan_DAO;

public class LoginUI extends JFrame {
    private final JLabel lblTitleLogo;
    private final JLabel lblLogo;
    RoundedInputField txtTenDangNhap;
    RoundedInputField txtMatKhau;

    public LoginUI() {
        setTitle("Đăng nhập");
        setSize(900, 680);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color backgroundColor = new Color(0x43, 0x8D, 0xB8);
        getContentPane().setBackground(backgroundColor);

        // Panel logo trái
        JPanel pnlTopLeftLogo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ImageIcon imageIcon = new ImageIcon("icon/logo.png");
        pnlTopLeftLogo.add(Box.createHorizontalStrut(20));
        pnlTopLeftLogo.add(lblLogo = new JLabel(imageIcon));
        pnlTopLeftLogo.setBackground(backgroundColor);
        add(pnlTopLeftLogo, BorderLayout.NORTH);

        // Panel login trung tâm
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        RoundedPanel loginPanel = new RoundedPanel(30, new Color(255, 255, 255, 100));
        loginPanel.setPreferredSize(new Dimension(400, 350));
        loginPanel.setLayout(null);

        JLabel lblTitle = new JLabel("Login");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(150, 20, 100, 30);
        loginPanel.add(lblTitle);

        txtTenDangNhap = new RoundedInputField(20, false);
        txtTenDangNhap.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtTenDangNhap.setBounds(70, 80, 260, 40);
        loginPanel.add(txtTenDangNhap);

        txtMatKhau = new RoundedInputField(20, true);
        txtMatKhau.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtMatKhau.setBounds(70, 140, 260, 40);
        loginPanel.add(txtMatKhau);

        RoundedButton btnLogin = new RoundedButton("Login", 20);
        btnLogin.setBackground(new Color(67, 141, 184));
        btnLogin.setBounds(70, 260, 260, 40);
        loginPanel.add(btnLogin);

        JPanel southPanel = new JPanel();
        southPanel.add(lblTitleLogo = new JLabel("24 Convenience Store"));
        southPanel.setBackground(backgroundColor);
        lblTitleLogo.setForeground(Color.WHITE);
        lblTitleLogo.setFont(new Font("SansSerif", Font.BOLD, 80));
        add(southPanel, BorderLayout.SOUTH);

        // Bắt sự kiện login
        btnLogin.addActionListener(e -> xuLyDangNhap());

        centerPanel.add(loginPanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void xuLyDangNhap() {
        String tenDangNhap = txtTenDangNhap.getText();
        String matKhau = txtMatKhau.getText();

        if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập và mật khẩu không được để trống!");
            return;
        }

        TaiKhoan_DAO dao = new TaiKhoan_DAO();
        TaiKhoan tk = null;
        try {
            tk = dao.dangNhap(tenDangNhap, matKhau);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (tk != null) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công " + tk.getVaiTro());
            Session.getInstance().login(tk);
            openHomeScreen();
        } else {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không chính xác!");
        }

        Clear();
    }

    private void openHomeScreen() {
        Home home = new Home();
        home.setVisible(true);
        this.dispose();
    }

    private void Clear() {
        txtTenDangNhap.setText("");
        txtMatKhau.setText("");
        txtTenDangNhap.requestFocus();
    }


    public static void main(String[] args) {
        new LoginUI().setVisible(true);
    }
}
