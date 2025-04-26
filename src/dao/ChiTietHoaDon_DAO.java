package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connect.ConnectDB;
import entity.ChiTietHoaDon;

public class ChiTietHoaDon_DAO {
	ConnectDB ins = ConnectDB.getInstance();
	
    public boolean themChiTietHoaDon(ChiTietHoaDon chiTiet) {
        boolean isSuccess = false;
        try {
            Connection con = ins.getConnection();
            String sql = "INSERT INTO ChiTietHoaDon (MaChiTiet, MaHoaDon, MaSanPham, SoLuong, DonGia) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, chiTiet.getMaChiTiet());
            stmt.setString(2, chiTiet.getMaHoaDon());
            stmt.setString(3, chiTiet.getMaSanPham());
            stmt.setInt(4, chiTiet.getSoLuong());
            stmt.setDouble(5, chiTiet.getDonGia());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
                System.out.println("Chi tiết hóa đơn đã được thêm thành công.");
            } else {
                System.out.println("Lỗi khi thêm chi tiết hóa đơn.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
    
 // Lấy số lượng hóa đơn hiện tại
    public int getCurrentOrderDetailCount() {
        int count = 0;
        try {
            Connection con = ins.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) AS count FROM ChiTietHoaDon");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
