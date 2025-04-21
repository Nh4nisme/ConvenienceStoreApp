package dao;

import connect.ConnectDB;
import entity.NhanVien;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> ds = new ArrayList<>();

        try {
            Connection con = ConnectDB.getConnection();
            CallableStatement cstmt = con.prepareCall("{call sp_NhanVien(?,?,?,?,?,?, 'SELECT')}");
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                boolean gender = rs.getString("GioiTinh").equalsIgnoreCase("Nam");
                LocalDate birthDate = rs.getDate("birthDate").toLocalDate();
                NhanVien nv = new NhanVien(
                        rs.getInt("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("SoDienThoai"),
                        gender,
                        birthDate,
                        rs.getString("DiaChi")
                );
                ds.add(nv);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ds;
    }


}
