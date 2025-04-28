package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connect.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.SanPham;
import entity.ChiTietHoaDon;

public class HoaDon_DAO {
    ConnectDB ins = ConnectDB.getInstance();
    
    // Lấy tất cả hóa đơn từ cơ sở dữ liệu
    public List<HoaDon> getAllHoaDon() {
        List<HoaDon> ds = new ArrayList<>();
        try {
            ins.connect();
            Connection con = ins.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                "SELECT hd.MaHoaDon, hd.NgayLap, hd.TongTien, hd.MaNhanVien, " +
                "hd.MaKhachHang, kh.TenKhachHang, kh.SoDienThoai, kh.DiemTichLuy " +
                "FROM HoaDon hd JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang"
            );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng hóa đơn từ kết quả truy vấn
                HoaDon hd = new HoaDon(
                    rs.getString("MaHoaDon"),
                    rs.getString("MaNhanVien"),
                    rs.getString("MaKhachHang"),
                    rs.getDate("NgayLap"),
                    rs.getDouble("TongTien")
                );
                
                // Lấy chi tiết hóa đơn
                List<ChiTietHoaDon> chiTietList = getChiTietHoaDon(hd.getMaHoaDon());
                for (ChiTietHoaDon ct : chiTietList) {
                    hd.themChiTietHoaDon(ct);
                }
                
                ds.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    // Lấy danh sách chi tiết của một hóa đơn
    public List<ChiTietHoaDon> getChiTietHoaDon(String maHoaDon) {
        List<ChiTietHoaDon> ds = new ArrayList<>();
        try {
            Connection con = ins.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                "SELECT MaChiTiet, MaHoaDon, MaSanPham, SoLuong, DonGia " +
                "FROM ChiTietHoaDon WHERE MaHoaDon = ?"
            );
            stmt.setString(1, maHoaDon);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ChiTietHoaDon ct = new ChiTietHoaDon(
                    rs.getString("MaChiTiet"),
                    rs.getString("MaHoaDon"),
                    rs.getString("MaSanPham"),
                    rs.getInt("SoLuong"),
                    rs.getDouble("DonGia")
                );
                ds.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    
 // Lấy hóa đơn theo mã hóa đơn hoặc mã khách hàng
    public List<HoaDon> getHoaDon(String criteria, String searchType) {
        List<HoaDon> ds = new ArrayList<>();
        try {
            Connection con = ins.getConnection();
            String sql = "SELECT hd.MaHoaDon, hd.NgayLap, hd.TongTien, hd.MaNhanVien, " +
                        "hd.MaKhachHang, kh.TenKhachHang, kh.SoDienThoai, kh.DiemTichLuy " +
                        "FROM HoaDon hd JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang ";
            
            if ("BY_MA_HOA_DON".equals(searchType)) {
                sql += "WHERE hd.MaHoaDon = ?";
            } else if ("BY_MA_KHACH_HANG".equals(searchType)) {
                sql += "WHERE kh.MaKhachHang = ?";
            } else {
                throw new IllegalArgumentException("Invalid search type: " + searchType);
            }
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, criteria);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getString("MaHoaDon"),
                    rs.getString("MaNhanVien"),
                    rs.getString("MaKhachHang"),
                    rs.getDate("NgayLap"),
                    rs.getDouble("TongTien")
                );
                
                List<ChiTietHoaDon> chiTietList = getChiTietHoaDon(hd.getMaHoaDon());
                for (ChiTietHoaDon ct : chiTietList) {
                    hd.themChiTietHoaDon(ct);
                }
                
                ds.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    // Lấy danh sách hóa đơn theo ngày
    public List<HoaDon> getHoaDonTheoNgay(Date ngayBatDau, Date ngayKetThuc) {
        List<HoaDon> ds = new ArrayList<>();
        try {
            Connection con = ins.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                "SELECT hd.MaHoaDon, hd.NgayLap, hd.TongTien, hd.MaNhanVien, " +
                "hd.MaKhachHang, kh.TenKhachHang, kh.SoDienThoai, kh.DiemTichLuy " +
                "FROM HoaDon hd JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang " +
                "WHERE hd.NgayLap BETWEEN ? AND ?"
            );
            stmt.setDate(1, ngayBatDau);
            stmt.setDate(2, ngayKetThuc);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng hóa đơn từ kết quả truy vấn
                HoaDon hd = new HoaDon(
                    rs.getString("MaHoaDon"),
                    rs.getString("MaNhanVien"),
                    rs.getString("MaKhachHang"),
                    rs.getDate("NgayLap"),
                    rs.getDouble("TongTien")
                );
                
                // Lấy chi tiết hóa đơn
                List<ChiTietHoaDon> chiTietList = getChiTietHoaDon(hd.getMaHoaDon());
                for (ChiTietHoaDon ct : chiTietList) {
                    hd.themChiTietHoaDon(ct);
                }
                
                ds.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    // Lấy số lượng hóa đơn hiện tại
    public int getCurrentOrderCount() {
        int count = 0;
        try {
            Connection con = ins.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) AS count FROM HoaDon");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    // Lấy thống kê doanh thu theo ngày
    public List<Object[]> getDoanhThuTheoNgay(Date ngayBatDau, Date ngayKetThuc) {
        List<Object[]> ds = new ArrayList<>();
        try {
            Connection con = ins.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                "SELECT NgayLap, SUM(TongTien) as DoanhThu, COUNT(*) as SoHoaDon " +
                "FROM HoaDon WHERE NgayLap BETWEEN ? AND ? " +
                "GROUP BY NgayLap ORDER BY NgayLap"
            );
            stmt.setDate(1, ngayBatDau);
            stmt.setDate(2, ngayKetThuc);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] row = new Object[3];
                row[0] = rs.getDate("NgayLap");
                row[1] = rs.getDouble("DoanhThu");
                row[2] = rs.getInt("SoHoaDon");
                ds.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    public KhachHang getKhachHangByMa(String maKH) {
        KhachHang kh = null;
        try {
            Connection con = ins.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM KhachHang WHERE MaKhachHang = ?"
            );
            stmt.setString(1, maKH);
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
    
    public SanPham getSanPhamByMa(String maSP) {
        SanPham sp = null;
        try {
            Connection con = ins.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM SanPham WHERE MaSanPham = ?"
            );
            stmt.setString(1, maSP);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sp = new SanPham(
                    rs.getString("MaSanPham"),
                    rs.getString("TenSanPham"),
                    rs.getString("DonViTinh"),
                    rs.getDouble("GiaBan"),
                    rs.getInt("SoLuongTon"),
                    rs.getString("MaLoai"),
                    rs.getString("LinkAnh")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sp;
    }
}