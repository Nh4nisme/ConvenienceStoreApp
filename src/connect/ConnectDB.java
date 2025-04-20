package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static Connection con = null;
    private static ConnectDB instance = new ConnectDB();

    private ConnectDB() {
    }

    public static ConnectDB getInstance() {
        return instance;
    }

    public void connect() {
        if (con == null) {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=CuaHangTienLoi;encrypt=false";
            String user = "sa";
            String pass = "caotrungnguli";

            try {
                con = DriverManager.getConnection(url, user, pass);
                System.out.println("✅ Kết nối thành công!");
            } catch (SQLException e) {
                System.err.println("❌ Kết nối thất bại!");
                e.printStackTrace();
            }
        }
    }

    // Đóng kết nối
    public static void disconnect() {
        if (con != null) {
            try {
                con.close();
                con = null;
                System.out.println("🔌 Đã đóng kết nối.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Trả về đối tượng Connection
    public static Connection getConnection() {
        return con;
    }
}
