package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connect.ConnectDB;
import entity.KhachHang;

public class KhachHang_DAO {

	public List<KhachHang> getAllKhachHang() {
	    List<KhachHang> ds = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT MaKhachHang, TenKhachHang, SoDienThoai, DiemTichLuy FROM KhachHang");
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            KhachHang kh = new KhachHang(
	                rs.getInt("MaKhachHang"),
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

	public boolean themKhachHang(KhachHang kh) {
	    try {
	        Connection con = ConnectDB.getConnection();
	        PreparedStatement stmt = con.prepareStatement("INSERT INTO KhachHang(TenKhachHang, SoDienThoai, DiemTichLuy) VALUES(?, ?, ?)");
	        stmt.setString(1, kh.getTenKhachHang());
	        stmt.setString(2, kh.getSoDienThoai());
	        stmt.setDouble(3, kh.getDiemTichLuy());
	        return stmt.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public boolean capNhatKhachHang(KhachHang kh) {
	    try {
	        Connection con = ConnectDB.getConnection();
	        PreparedStatement stmt = con.prepareStatement("UPDATE KhachHang SET TenKhachHang = ?, SoDienThoai = ?, DiemTichLuy = ? WHERE MaKhachHang = ?");
	        stmt.setString(1, kh.getTenKhachHang());
	        stmt.setString(2, kh.getSoDienThoai());
	        stmt.setDouble(3, kh.getDiemTichLuy());
	        stmt.setInt(4, kh.getMaKhachHang());
	        return stmt.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public boolean xoaKhachHang(int maKH) {
	    try {
	        Connection con = ConnectDB.getConnection();
	        PreparedStatement stmt = con.prepareStatement("DELETE FROM KhachHang WHERE MaKhachHang = ?");
	        stmt.setInt(1, maKH);
	        return stmt.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public List<KhachHang> timTheoTen(String ten) {
	    List<KhachHang> ds = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT MaKhachHang, TenKhachHang, SoDienThoai, DiemTichLuy FROM KhachHang WHERE TenKhachHang LIKE ?");
	        stmt.setString(1, "%" + ten + "%");
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            KhachHang kh = new KhachHang(
	                rs.getInt("MaKhachHang"),
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
	        Connection con = ConnectDB.getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT MaKhachHang, TenKhachHang, SoDienThoai, DiemTichLuy FROM KhachHang WHERE SoDienThoai = ?");
	        stmt.setString(1, sdt);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            kh = new KhachHang(
	                rs.getInt("MaKhachHang"),
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
	        Connection con = ConnectDB.getConnection();
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
	        Connection con = ConnectDB.getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT MaKhachHang, TenKhachHang, SoDienThoai, DiemTichLuy FROM KhachHang WHERE DiemTichLuy >= ?");
	        stmt.setDouble(1, diemToiThieu);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            KhachHang kh = new KhachHang(
	                rs.getInt("MaKhachHang"),
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