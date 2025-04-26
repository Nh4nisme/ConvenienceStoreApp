package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import connect.ConnectDB;
import entity.SanPham;

public class SanPham_DAO {

    public List<SanPham> getAllSanPham() {
        List<SanPham> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            CallableStatement stmt = con.prepareCall("{call sp_GetAllSanPham}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham(
                        rs.getString("MaSanPham"),
                        rs.getString("TenSanPham"),
                        rs.getString("DonViTinh"),
                        rs.getDouble("GiaBan"),
                        rs.getInt("SoLuongTon"),
                        rs.getString("MaLoai"),
                        rs.getString("LinkAnh")
                );
                ds.add(sp);
            }
        } catch (SQLException e) {
            System.err.println("Ngoại lệ SQL: Không thể kết nối đến cơ sở dữ liệu hoặc thực thi câu truy vấn.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Có lỗi không mong muốn xảy ra.");
            e.printStackTrace();
        }
        return ds;
    }

    public boolean themSanPham(SanPham sp) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            CallableStatement stmt = con.prepareCall("{call sp_ThemSanPham(?,?,?,?,?,?,?)}");

            stmt.setString(1, sp.getMaSanPham());
            stmt.setString(2, sp.getTenSanPham());
            stmt.setString(3, sp.getDonViTinh());
            stmt.setDouble(4, sp.getGiaBan());
            stmt.setInt(5, sp.getSoLuongTon());
            stmt.setString(6, sp.getMaLoai());
            stmt.setString(7, sp.getLinkAnh());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Ngoại lệ SQL: Không thể thêm sản phẩm vào cơ sở dữ liệu.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Có lỗi không mong muốn xảy ra.");
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatSanPham(SanPham sp) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            CallableStatement stmt = con.prepareCall("{call sp_CapNhatSanPham(?,?,?,?,?,?,?)}");
            stmt.setString(1, sp.getMaSanPham());
            stmt.setString(2, sp.getTenSanPham());
            stmt.setString(3, sp.getDonViTinh());
            stmt.setDouble(4, sp.getGiaBan());
            stmt.setInt(5, sp.getSoLuongTon());
            stmt.setString(6, sp.getMaLoai());
            stmt.setString(7, sp.getLinkAnh());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Ngoại lệ SQL: Không thể cập nhật sản phẩm trong cơ sở dữ liệu.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Có lỗi không mong muốn xảy ra.");
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaSanPham(String maSP) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            CallableStatement stmt = con.prepareCall("{call sp_XoaSanPham(?)}");
            stmt.setString(1, maSP);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ngoại lệ SQL: Không thể xóa sản phẩm khỏi cơ sở dữ liệu.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Có lỗi không mong muốn xảy ra.");
            e.printStackTrace();
        }
        return false;
    }

    public SanPham timTheoMa(String ma) {
        SanPham sp = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            CallableStatement stmt = con.prepareCall("{call sp_TimSanPhamTheoMa(?)}");
            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
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
        } catch (SQLException e) {
            System.err.println("Ngoại lệ SQL: Không thể tìm sản phẩm theo tên.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Có lỗi không mong muốn xảy ra.");
            e.printStackTrace();
        }
        return sp;
    }
    
    public String timMaSanPhamTheoTen(String tenSanPham) {
        String maSanPham = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT MaSanPham FROM SanPham WHERE TenSanPham = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, tenSanPham);

            rs = stmt.executeQuery();
            if (rs.next()) {
                maSanPham = rs.getString("MaSanPham");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return maSanPham;
    }

    public boolean giamSoLuongTon(String maSP, int soLuong) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            CallableStatement stmt = con.prepareCall("{call sp_GiamSoLuongTon(?, ?)}");
            stmt.setString(1, maSP);
            stmt.setInt(2, soLuong);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ngoại lệ SQL: Không thể giảm số lượng tồn kho.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Có lỗi không mong muốn xảy ra.");
            e.printStackTrace();
        }
        return false;
    }

    public List<SanPham> layTonKhoThap(int nguong) {
        List<SanPham> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            CallableStatement stmt = con.prepareCall("{call sp_SanPhamTonKhoThap(?)}");
            stmt.setInt(1, nguong);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham(
                        rs.getString("MaSanPham"),
                        rs.getString("TenSanPham"),
                        rs.getString("DonViTinh"),
                        rs.getDouble("GiaBan"),
                        rs.getInt("SoLuongTon"),
                        rs.getString("MaLoai"),
                        rs.getString("LinkAnh")
                );
                ds.add(sp);
            }
        } catch (SQLException e) {
            System.err.println("Ngoại lệ SQL: Không thể lấy sản phẩm có tồn kho thấp.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Có lỗi không mong muốn xảy ra.");
            e.printStackTrace();
        }
        return ds;
    }

    public List<String> getTenLoaiSanPham() {
        List<String> dsTenLoai = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT DISTINCT l.TenLoai FROM SanPham sp JOIN LoaiSanPham l ON sp.MaLoai = l.MaLoai";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Lặp qua các kết quả và thêm vào danh sách
            while (rs.next()) {
                dsTenLoai.add(rs.getString("TenLoai"));
            }
        } catch (SQLException e) {
            System.err.println("Ngoại lệ SQL: Không thể lấy tên loại sản phẩm.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Có lỗi không mong muốn xảy ra.");
            e.printStackTrace();
        }
        return dsTenLoai;
    }
    
    public String[][] getTopSanPhamBanChayAsArray() {
        List<String[]> list = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            CallableStatement stmt = con.prepareCall("{call sp_TopSanPhamBanChay}");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String tenSanPham = rs.getString("TenSanPham");
                String soLuong = String.valueOf(rs.getInt("TongSoLuongBan"));
                list.add(new String[]{tenSanPham, soLuong});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list.toArray(new String[0][2]);
    }
    
    public String[][] getDoanhThuTuanTrongThang() {
        List<String[]> list = new ArrayList<>();
        try {
            // Kết nối đến cơ sở dữ liệu
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            
            // Gọi thủ tục lưu trữ sp_DoanhThuTuanTrongThang
            CallableStatement stmt = con.prepareCall("{call sp_DoanhThuTuanTrongThang}");
            ResultSet rs = stmt.executeQuery();
            
            // Chuẩn bị một map để lưu trữ doanh thu của từng tuần
            Map<Integer, Double> revenueMap = new HashMap<>();
            
            // Xử lý kết quả truy vấn
            while (rs.next()) {
                int week = rs.getInt("Tuan");
                double doanhThu = rs.getDouble("DoanhThu");
                revenueMap.put(week, doanhThu);
            }
            
            // Lấy tuần đầu tiên và tuần cuối cùng trong tháng để đảm bảo có tất cả các tuần
            int firstWeek = 1;  // Tuần đầu tiên của tháng luôn là tuần 1
            int lastWeek = 4;   // Tháng có tối đa 4 tuần
            
            // Duyệt qua tất cả các tuần trong tháng
            for (int week = firstWeek; week <= lastWeek; week++) {
                double doanhThu = revenueMap.getOrDefault(week, 0.0);  // Mặc định là 0 nếu không có doanh thu cho tuần
                list.add(new String[]{String.valueOf(week), String.format("%.2f", doanhThu)});
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list.toArray(new String[0][2]);
    }


}
