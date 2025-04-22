package ui;// LoginUI.java
import javax.swing.*;
import java.awt.event.*;

import entity.TaiKhoan;
import dao.TaiKhoan_DAO;

public class LoginUI extends JFrame {
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;

    public LoginUI() {
        setTitle("Đăng nhập");
        setSize(300, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblUser = new JLabel("Tên đăng nhập:");
        lblUser.setBounds(20, 20, 100, 25);
        add(lblUser);

        txtTenDangNhap = new JTextField();
        txtTenDangNhap.setBounds(130, 20, 130, 25);
        add(txtTenDangNhap);

        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setBounds(20, 60, 100, 25);
        add(lblPass);

        txtMatKhau = new JPasswordField();
        txtMatKhau.setBounds(130, 60, 130, 25);
        add(txtMatKhau);

        btnDangNhap = new JButton("Đăng nhập");
        btnDangNhap.setBounds(80, 100, 120, 30);
        add(btnDangNhap);

        btnDangNhap.addActionListener(e -> xuLyDangNhap());
    }

    private void xuLyDangNhap() {
        String tenDangNhap = txtTenDangNhap.getText();
        String matKhau = new String(txtMatKhau.getPassword());

        TaiKhoan_DAO dao = new TaiKhoan_DAO();
        TaiKhoan tk = dao.dangNhap(tenDangNhap, matKhau);

        if (tk != null) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công với vai trò: " + tk.getVaiTro());
            // Chuyển sang màn hình chính
        } else {
            JOptionPane.showMessageDialog(this, "Đăng nhập thất bại!");
        }
    }

    public static void main(String[] args) {
        new LoginUI().setVisible(true);
    }
}
