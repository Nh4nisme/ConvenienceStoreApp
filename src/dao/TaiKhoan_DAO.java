package dao;

import connect.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;

import java.sql.*;

public class TaiKhoan_DAO {
    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        TaiKhoan tk = null;

        // Gọi kết nối CSDL
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
                tk.setVaiTro(rs.getString("VaiTro"));

                // Lấy mã nhân viên và tìm đối tượng NhanVien tương ứng
                String maNhanVien = rs.getString("MaNhanVien");
                if (maNhanVien != null) {
                    NhanVien nv = new NhanVienDAO().getNhanVienByMa(maNhanVien);
                    tk.setNhanVien(nv);
                }

                System.out.println("👤 Xin chào, " + rs.getString("HoTen"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tk;
    }


    public int updatePassword(String tenDangNhap, String matKhau) {
        int result = -1;
        ConnectDB.getInstance().connect();
        Connection conn = ConnectDB.getConnection();

        if (conn == null) {
            System.err.println("❌ Không thể kết nối tới cơ sở dữ liệu.");
            return -1;
        }

        try (CallableStatement stmt = conn.prepareCall("{? = call sp_UpdatePassword(?, ?)}")) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, tenDangNhap);
            stmt.setString(3, matKhau);
            stmt.execute();
            result = stmt.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
