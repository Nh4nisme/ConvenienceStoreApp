package entity;

public class KhachHang {
    private int maKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private double diemTichLuy;
    
    public KhachHang() {
    }
    
    public KhachHang(int maKhachHang, String tenKhachHang, String soDienThoai, double diemTichLuy) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.diemTichLuy = diemTichLuy;
    }
    
    public int getMaKhachHang() {
        return maKhachHang;
    }
    
    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
    
    public String getTenKhachHang() {
        return tenKhachHang;
    }
    
    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }
    
    public String getSoDienThoai() {
        return soDienThoai;
    }
    
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    
    public double getDiemTichLuy() {
        return diemTichLuy;
    }
    
    public void setDiemTichLuy(double diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }
    
    @Override
    public String toString() {
        return "KhachHang{" +
            "maKhachHang=" + maKhachHang +
            ", tenKhachHang='" + tenKhachHang + "'" +
            ", soDienThoai='" + soDienThoai + "'" +
            ", diemTichLuy=" + diemTichLuy +
            "}";
    }
}