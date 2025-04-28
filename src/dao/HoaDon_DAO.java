package dao;

import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
                    rs.getInt("DiemTichLuy")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }
    
 // Thêm phương thức này vào class HoaDon_DAO
    public String[][] getTop3NhanVienDoanhThu() {
        String[][] result = new String[3][2]; // 3 hàng, 2 cột (Tên NV, Mã NV)
        for (int i = 0; i < 3; i++) {
            result[i][0] = "N/A"; // Tên nhân viên
            result[i][1] = "N/A"; // Mã nhân viên
        }
        
        try {
            Connection con = ins.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                "SELECT TOP 3 nv.MaNhanVien, nv.HoTen, SUM(hd.TongTien) as TongDoanhThu " +
                "FROM HOADON hd " +
                "JOIN NHANVIEN nv ON hd.MaNhanVien = nv.MaNhanVien " +
                "GROUP BY nv.MaNhanVien, nv.HoTen " +
                "ORDER BY TongDoanhThu DESC"
            );
            ResultSet rs = stmt.executeQuery();
            int index = 0;
            while (rs.next() && index < 3) {
                result[index][0] = rs.getString("HoTen") != null ? rs.getString("HoTen") : "N/A";
                result[index][1] = rs.getString("MaNhanVien") != null ? rs.getString("MaNhanVien") : "N/A";
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    // Hàm để lấy 5 hóa đơn có tổng tiền cao nhất
    public String[][] getTop5HoaDonTongTien() {
        String[][] result = new String[5][4]; // 5 hàng, 4 cột (Order ID, Date, Customer, Total Amount)
        for (int i = 0; i < 5; i++) {
            result[i][0] = "N/A"; // Order ID
            result[i][1] = "N/A"; // Date
            result[i][2] = "N/A"; // Customer
            result[i][3] = "N/A"; // Total Amount
        }
        
        try {
            Connection con = ins.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                "SELECT TOP 5 hd.MaHoaDon, hd.NgayLap, kh.TenKhachHang, hd.TongTien " +
                "FROM HOADON hd " +
                "JOIN KHACHHANG kh ON hd.MaKhachHang = kh.MaKhachHang " +
                "ORDER BY hd.TongTien DESC"
            );
            ResultSet rs = stmt.executeQuery();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
            DecimalFormat numberFormat = new DecimalFormat("#,###");
            int index = 0;
            while (rs.next() && index < 5) {
                result[index][0] = rs.getString("MaHoaDon") != null ? rs.getString("MaHoaDon") : "N/A";
                result[index][1] = rs.getTimestamp("NgayLap") != null ? dateFormat.format(rs.getTimestamp("NgayLap")) : "N/A";
                result[index][2] = rs.getString("TenKhachHang") != null ? rs.getString("TenKhachHang") : "N/A";
                result[index][3] = numberFormat.format(rs.getDouble("TongTien"));
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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
    
    public boolean themHoaDon(HoaDon hoaDon) {
        boolean isSuccess = false;
        try {
            Connection con = ins.getConnection();
            String sql = "INSERT INTO HoaDon (MaHoaDon, MaNhanVien, MaKhachHang, NgayLap, TongTien) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, hoaDon.getMaHoaDon());
            stmt.setString(2, hoaDon.getMaNhanVien());
            stmt.setString(3, hoaDon.getMaKhachHang());
            stmt.setDate(4, hoaDon.getNgayLap());
            stmt.setDouble(5, hoaDon.getTongTien());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
    
}