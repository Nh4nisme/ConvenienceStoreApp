package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connect.ConnectDB;
import entity.KhachHang;

public class KhachHang_DAO {
	ConnectDB ins = ConnectDB.getInstance();
	
	public List<KhachHang> getAllKhachHang() {
	    List<KhachHang> ds = new ArrayList<>();
	    	    try {
	    	ins.connect();
	        Connection con = ins.getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT MaKhachHang, TenKhachHang, SoDienThoai, DiemTichLuy FROM KhachHang");
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            KhachHang kh = new KhachHang(
	                rs.getString("MaKhachHang"),
	                rs.getString("TenKhachHang"),
	                rs.getString("SoDienThoai"),
	                rs.getDouble("DiemTichLuy")
	            );
	            ds.add(kh);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return ds;
	}

	
	public int getCurrentCustomerCount() {
	    int count = 0;
	    try {
	        Connection con = ins.getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) AS count FROM KhachHang");
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            count = rs.getInt("count");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return count;
	}


	public boolean themKhachHang(KhachHang kh) {
	    try {
	        // Lấy số khách hàng hiện tại từ cơ sở dữ liệu
	        int currentCount = getCurrentCustomerCount();
	        
	        // Tạo mã khách hàng mới dựa trên số lượng khách hàng hiện tại
	        String newMa = "KH" + String.format("%05d", currentCount + 1); // Tăng lên 1 để tạo mã khách hàng mới
	        kh.setMaKhachHang(newMa);  // Cập nhật lại mã khách hàng

	        // Thực hiện thêm khách hàng vào cơ sở dữ liệu
	        Connection con = ins.getConnection();
	        PreparedStatement stmt = con.prepareStatement("INSERT INTO KhachHang(MaKhachHang, TenKhachHang, SoDienThoai, DiemTichLuy) VALUES(?, ?, ?, ?)");
	        stmt.setString(1, kh.getMaKhachHang());
	        stmt.setString(2, kh.getTenKhachHang());
	        stmt.setString(3, kh.getSoDienThoai());
	        stmt.setDouble(4, kh.getDiemTichLuy());
	        return stmt.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	
	public boolean capNhatKhachHang(KhachHang kh) {
	    try {
	        Connection con = ins.getConnection();
	        PreparedStatement stmt = con.prepareStatement("UPDATE KhachHang SET TenKhachHang = ?, SoDienThoai = ?, DiemTichLuy = ? WHERE MaKhachHang = ?");
	        stmt.setString(1, kh.getTenKhachHang());
	        stmt.setString(2, kh.getSoDienThoai());
	        stmt.setDouble(3, kh.getDiemTichLuy());
	        stmt.setString(4, kh.getMaKhachHang());
	        return stmt.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public boolean xoaKhachHang(String maKH) {
        try {
            Connection con = ins.getConnection();
            PreparedStatement stmt = con.prepareStatement("DELETE FROM KhachHang WHERE MaKhachHang = ?");
            stmt.setString(1, maKH);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

	public List<KhachHang> timKiemKhachHang(String searchTerm) {
	    List<KhachHang> ds = new ArrayList<>();
	    try {
	        Connection con = ins.getConnection();
	        PreparedStatement stmt = con.prepareStatement(
	            "SELECT MaKhachHang, TenKhachHang, SoDienThoai, DiemTichLuy " +
	            "FROM KhachHang " +
	            "WHERE MaKhachHang LIKE ? OR TenKhachHang LIKE ? OR SoDienThoai LIKE ?"
	        );
	        String wildcardTerm = "%" + searchTerm + "%";
	        stmt.setString(1, wildcardTerm);
	        stmt.setString(2, wildcardTerm);
	        stmt.setString(3, wildcardTerm);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            KhachHang kh = new KhachHang(
	                rs.getString("MaKhachHang"),
	                rs.getString("TenKhachHang"),
	                rs.getString("SoDienThoai"),
	                rs.getDouble("DiemTichLuy")
	            );
	            ds.add(kh);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return ds;
	}

	public KhachHang timTheoSDT(String sdt) {
	    KhachHang kh = null;
	    try {
	        Connection con = ins.getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT MaKhachHang, TenKhachHang, SoDienThoai, DiemTichLuy FROM KhachHang WHERE SoDienThoai = ?");
	        stmt.setString(1, sdt);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            kh = new KhachHang(
	                rs.getString("MaKhachHang"),
	                rs.getString("TenKhachHang"),
	                rs.getString("SoDienThoai"),
	                rs.getDouble("DiemTichLuy")
	            );
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return kh;
	}

	public boolean capNhatDiemTichLuy(int maKH, double diemMoi) {
	    try {
	        Connection con = ins.getConnection();
	        PreparedStatement stmt = con.prepareStatement("UPDATE KhachHang SET DiemTichLuy = ? WHERE MaKhachHang = ?");
	        stmt.setDouble(1, diemMoi);
	        stmt.setInt(2, maKH);
	        return stmt.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public List<KhachHang> layKhachHangTheoDiemTichLuy(double diemToiThieu) {
	    List<KhachHang> ds = new ArrayList<>();
	    try {
	        Connection con = ins.getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT MaKhachHang, TenKhachHang, SoDienThoai, DiemTichLuy FROM KhachHang WHERE DiemTichLuy >= ?");
	        stmt.setDouble(1, diemToiThieu);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            KhachHang kh = new KhachHang(
	                rs.getString("MaKhachHang"),
	                rs.getString("TenKhachHang"),
	                rs.getString("SoDienThoai"),
	                rs.getDouble("DiemTichLuy")
	            );
	            ds.add(kh);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return ds;
	}
}