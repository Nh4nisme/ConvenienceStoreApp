package entity;

public class TaiKhoan {
    private String tenDangNhap;
    private String matKhau;
    private NhanVien nhanVien;
    private String vaiTro;
    private boolean trangThai;

    public TaiKhoan(String tenDangNhap, String matKhau, NhanVien nhanVien, String vaiTro, boolean trangThai) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.nhanVien = nhanVien;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
    }

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

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
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

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "username=" + tenDangNhap +
                ", password='" + matKhau + '\'' +
                ", NhanVien='" + (nhanVien != null ? nhanVien.getMaNhanVien() : "null") + '\'' +
                ", vaitro=" + vaiTro + '\'' +
                ", TrangThai=" + (trangThai ? "Hoat dong" : "Tam nghi") +
                '}';
    }
}