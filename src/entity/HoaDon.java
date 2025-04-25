package entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class HoaDon {
    private String maHoaDon;
    private String maNhanVien;
    private String maKhachHang;
    private Date ngayLap;
    private double tongTien;
    private List<ChiTietHoaDon> danhSachChiTiet;

    public HoaDon() {
        danhSachChiTiet = new ArrayList<>();
    }

    public HoaDon(String maHoaDon, String maNhanVien, String maKhachHang, Date ngayLap, double tongTien) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.danhSachChiTiet = new ArrayList<>();
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public double getTongTien() {
        return tongTien;
    }

    public List<ChiTietHoaDon> getDanhSachChiTiet() {
        return danhSachChiTiet;
    }

    public void themChiTietHoaDon(ChiTietHoaDon chiTiet) {
        danhSachChiTiet.add(chiTiet);
    }

    // Setters (dùng khi tạo mới hóa đơn)
    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", maNhanVien='" + maNhanVien + '\'' +
                ", maKhachHang='" + maKhachHang + '\'' +
                ", ngayLap=" + ngayLap +
                ", tongTien=" + tongTien +
                ", chiTiet=" + danhSachChiTiet +
                '}';
    }
}
