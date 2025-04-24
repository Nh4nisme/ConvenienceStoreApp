package entity;

public class TaiKhoan {
    private String tenDangNhap;
    private String matKhau;
    private NhanVien nv;
    private String vaiTro;
    private boolean trangThai;

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public NhanVien getNhanVien() {  // Getter for NhanVien
        return nv;
    }

    public void setNhanVien(NhanVien nv) {  // Setter for NhanVien
        this.nv = nv;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
