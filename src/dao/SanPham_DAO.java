package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connect.ConnectDB;
import entity.SanPham;

public class SanPham_DAO {

    public List<SanPham> getAllSanPham() {
        List<SanPham> ds = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnection();
            CallableStatement stmt = con.prepareCall("{call sp_GetAllSanPham}");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham(
                    rs.getInt("MaSanPham"),
                    rs.getString("TenSanPham"),
                    rs.getString("DonViTinh"),
                    rs.getDouble("GiaBan"),
                    rs.getInt("SoLuongTon"),
                    rs.getInt("MaLoai")
                );
                ds.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean themSanPham(SanPham sp) {
        try {
            Connection con = ConnectDB.getConnection();
            CallableStatement stmt = con.prepareCall("{call sp_ThemSanPham(?,?,?,?,?)}");
            stmt.setString(1, sp.getTenSanPham());
            stmt.setString(2, sp.getDonViTinh());
            stmt.setDouble(3, sp.getGiaBan());
            stmt.setInt(4, sp.getSoLuongTon());
            stmt.setInt(5, sp.getMaLoai());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatSanPham(SanPham sp) {
        try {
            Connection con = ConnectDB.getConnection();
            CallableStatement stmt = con.prepareCall("{call sp_CapNhatSanPham(?,?,?,?,?,?)}");
            stmt.setInt(1, sp.getMaSanPham());
            stmt.setString(2, sp.getTenSanPham());
            stmt.setString(3, sp.getDonViTinh());
            stmt.setDouble(4, sp.getGiaBan());
            stmt.setInt(5, sp.getSoLuongTon());
            stmt.setInt(6, sp.getMaLoai());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaSanPham(int maSP) {
        try {
            Connection con = ConnectDB.getConnection();
            CallableStatement stmt = con.prepareCall("{call sp_XoaSanPham(?)}");
            stmt.setInt(1, maSP);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<SanPham> timTheoTen(String ten) {
        List<SanPham> ds = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnection();
            CallableStatement stmt = con.prepareCall("{call sp_TimSanPhamTheoTen(?)}");
            stmt.setString(1, ten);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham(
                    rs.getInt("MaSanPham"),
                    rs.getString("TenSanPham"),
                    rs.getString("DonViTinh"),
                    rs.getDouble("GiaBan"),
                    rs.getInt("SoLuongTon"),
                    rs.getInt("MaLoai")
                );
                ds.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean giamSoLuongTon(int maSP, int soLuong) {
        try {
            Connection con = ConnectDB.getConnection();
            CallableStatement stmt = con.prepareCall("{call sp_GiamSoLuongTon(?, ?)}");
            stmt.setInt(1, maSP);
            stmt.setInt(2, soLuong);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<SanPham> layTonKhoThap(int nguong) {
        List<SanPham> ds = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnection();
            CallableStatement stmt = con.prepareCall("{call sp_SanPhamTonKhoThap(?)}");
            stmt.setInt(1, nguong);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham(
                    rs.getInt("MaSanPham"),
                    rs.getString("TenSanPham"),
                    rs.getString("DonViTinh"),
                    rs.getDouble("GiaBan"),
                    rs.getInt("SoLuongTon"),
                    rs.getInt("MaLoai")
                );
                ds.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}
