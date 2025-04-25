package dao;

import connect.ConnectDB;
import entity.NhanVien;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    public NhanVien getNhanVienByMa(String maNhanVien) {
        NhanVien nv = null;
        ConnectDB.getInstance().connect();
        Connection conn = ConnectDB.getConnection();

        if (conn == null) {
            System.err.println("❌ Không thể kết nối tới cơ sở dữ liệu.");
            return null;
        }

        String sql = "SELECT * FROM NhanVien WHERE MaNhanVien = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNhanVien);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("MaNhanVien"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setNgaySinh(Date.valueOf(rs.getDate("NgaySinh").toLocalDate())); // Convert to LocalDate
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nv;
    }
}
