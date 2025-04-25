package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import connect.ConnectDB;

public class NhanVienCaLamViec_DAO {
    public List<String> getShiftsByEmployeeID(String maNV) {
        List<String> list = new ArrayList<>();

        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getConnection();
            try (CallableStatement stmt = conn.prepareCall("{call sp_GetShiftsByEmployeeID(?)}")) {
                stmt.setString(1, maNV);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String maCa = rs.getString("MaCa");
                    Date ngayLam = rs.getDate("NgayLam");
                    Timestamp gioVao = rs.getTimestamp("GioVao");
                    Timestamp gioRa = rs.getTimestamp("GioRa");

                    list.add("Shift: " + maCa + ", Date: " + ngayLam +
                            ", Start: " + gioVao + ", End: " + (gioRa != null ? gioRa.toString() : "N/A"));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi truy xuất ca làm việc bằng SP: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }



    public int insertShift(String maNV, String maCa, LocalDateTime gioVao) {
        int result = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getConnection();

            try (CallableStatement stmt = conn.prepareCall("{call sp_InsertShift(?, ?, ?)}")) {
                stmt.setString(1, maNV);
                stmt.setString(2, maCa);
                stmt.setTimestamp(3, Timestamp.valueOf(gioVao));

                result = stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public int updateShiftOutTime(String maNhanVien, String maCa) {
        int result = -1;
        ConnectDB.getInstance().connect();
        Connection conn = ConnectDB.getConnection();

        if (conn == null) {
            System.err.println("❌ Không thể kết nối tới cơ sở dữ liệu.");
            return -1;
        }

        try (CallableStatement stmt = conn.prepareCall("{? = call sp_UpdateGioRaNhanVien(?, ?)}")) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, maNhanVien);
            stmt.setString(3, maCa);
            stmt.execute();
            result = stmt.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


}
