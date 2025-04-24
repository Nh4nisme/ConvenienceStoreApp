package entity;

public class SanPham {
    private String maSanPham;
    private String tenSanPham;
    private String donViTinh;
    private double giaBan;
    private int soLuongTon;
    private String maLoai;
    private String linkAnh;

    public SanPham() {
    }

    public SanPham(String maSanPham, String tenSanPham, String donViTinh, double giaBan, int soLuongTon, String maLoai, String linkAnh) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.donViTinh = donViTinh;
        this.giaBan = giaBan;
        this.soLuongTon = soLuongTon;
        this.maLoai = maLoai;
        this.linkAnh = linkAnh;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }

    @Override
    public String toString() {
        return "SanPham{" +
                "maSanPham='" + maSanPham + '\'' +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", donViTinh='" + donViTinh + '\'' +
                ", giaBan=" + giaBan +
                ", soLuongTon=" + soLuongTon +
                ", maLoai='" + maLoai + '\'' +
                ", linkAnh='" + linkAnh + '\'' +
                '}';
    }
}
