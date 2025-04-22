package Session;

import entity.TaiKhoan;

public class Session {
    private static TaiKhoan currentUser;

    public static void login(TaiKhoan user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static TaiKhoan getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}

