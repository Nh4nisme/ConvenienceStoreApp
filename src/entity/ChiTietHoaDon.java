package entity;

public class ChiTietHoaDon {
    private String maChiTiet;
    private String maHoaDon;
    private String maSanPham;
    private int soLuong;
    private double donGia;
    
    public ChiTietHoaDon() {
    }
    
    public ChiTietHoaDon(String maChiTiet, String maHoaDon, String maSanPham, int soLuong, double donGia) {
        this.maChiTiet = maChiTiet;
        this.maHoaDon = maHoaDon;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }
    
    public String getMaChiTiet() {
        return maChiTiet;
    }
    
    public String getMaHoaDon() {
        return maHoaDon;
    }
    
    public String getMaSanPham() {
        return maSanPham;
    }
    
    public int getSoLuong() {
        return soLuong;
    }
    
    public double getDonGia() {
        return donGia;
    }
    
    public double getThanhTien() {
        return soLuong * donGia;
    }
    
    void setMaChiTiet(String maChiTiet) {
        this.maChiTiet = maChiTiet;
    }
    
    void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }
    
    void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }
    
    void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    
    void setDonGia(double donGia) {
        this.donGia = donGia;
    }
    
    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
            "maChiTiet='" + maChiTiet + "'" +
            ", maHoaDon='" + maHoaDon + "'" +
            ", maSanPham='" + maSanPham + "'" +
            ", soLuong=" + soLuong +
            ", donGia=" + donGia +
            ", thanhTien=" + getThanhTien() +
            "}";
    }
}