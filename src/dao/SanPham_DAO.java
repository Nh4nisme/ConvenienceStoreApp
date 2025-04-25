package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
}
