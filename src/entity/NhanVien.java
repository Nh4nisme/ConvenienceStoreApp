package entity;

import java.time.LocalDate;

public class NhanVien {
    private int maNhanVien;
    private String hoTen;
    private String sdt;
    private boolean isGender;
    private LocalDate ngaySinh;
    private String diaChi;

    public NhanVien(int maNhanVien, String hoTen, String sdt, boolean isGender, LocalDate ngaySinh, String diaChi) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.isGender = isGender;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public boolean isGender() {
        return isGender;
    }

    public void setGender(boolean gender) {
        isGender = gender;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNhanVien=" + maNhanVien +
                ", hoTen='" + hoTen + '\'' +
                ", sdt='" + sdt + '\'' +
                ", gender=" + (isGender ? "Nam" : "Nữ") +
                ", ngaySinh=" + ngaySinh +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }
}