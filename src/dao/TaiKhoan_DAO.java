package dao;// TaiKhoanDAO.java
import connect.ConnectDB;
import entity.TaiKhoan;

import java.sql.*;

public class TaiKhoan_DAO {
    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        TaiKhoan tk = null;

        // GỌI connect() trước khi lấy kết nối
        ConnectDB.getInstance().connect();
        Connection conn = ConnectDB.getConnection();

        if (conn == null) {
            System.err.println("❌ Không thể kết nối tới cơ sở dữ liệu.");
            return null;
        }

        try (CallableStatement stmt = conn.prepareCall("{call sp_Login(?, ?)}")) {
            stmt.setString(1, tenDangNhap);
            stmt.setString(2, matKhau);

            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getString("TenDangNhap") != null) {
                tk = new TaiKhoan();
                tk.setTenDangNhap(rs.getString("TenDangNhap"));
                tk.setMaNhanVien(rs.getString("MaNhanVien"));
                tk.setVaiTro(rs.getString("VaiTro"));

                System.out.println("👤 Xin chào, " + rs.getString("HoTen"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tk;
    }

}
