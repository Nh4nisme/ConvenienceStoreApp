package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NhanVienCaLamViec {
    private String maNhanVien;
    private String maCa;
    private LocalDate ngayLam;
    private LocalDateTime gioVao;
    private LocalDateTime gioRa;

    public NhanVienCaLamViec() {}

    public NhanVienCaLamViec(String maNhanVien, String maCa, LocalDate ngayLam, LocalDateTime gioVao, LocalDateTime gioRa) {
        this.maNhanVien = maNhanVien;
        this.maCa = maCa;
        this.ngayLam = ngayLam;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaCa() {
        return maCa;
    }

    public void setMaCa(String maCa) {
        this.maCa = maCa;
    }

    public LocalDate getNgayLam() {
        return ngayLam;
    }

    public void setNgayLam(LocalDate ngayLam) {
        this.ngayLam = ngayLam;
    }

    public LocalDateTime getGioVao() {
        return gioVao;
    }

    public void setGioVao(LocalDateTime gioVao) {
        this.gioVao = gioVao;
    }

    public LocalDateTime getGioRa() {
        return gioRa;
    }

    public void setGioRa(LocalDateTime gioRa) {
        this.gioRa = gioRa;
    }
}
