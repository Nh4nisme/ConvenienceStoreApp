package Session;

import entity.TaiKhoan;

public class Session {
    private static Session instance;
    private TaiKhoan taiKhoan;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void login(TaiKhoan tk) {
        this.taiKhoan = tk;
    }

    public void logout() {
        taiKhoan = null;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public boolean isLoggedIn() {
        return taiKhoan != null;
    }
}
