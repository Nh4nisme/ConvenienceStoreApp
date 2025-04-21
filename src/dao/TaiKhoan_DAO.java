package dao;

import connect.ConnectDB;
import entity.TaiKhoan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoan_DAO {
    public TaiKhoan login(String username, String password) {

        try {
            Connection con = ConnectDB.getConnection();
            CallableStatement cstmt = con.prepareCall("{CALL sp_Login(?,?)}");
            cstmt.setString(1, username);
            cstmt.setString(2, password);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
}
