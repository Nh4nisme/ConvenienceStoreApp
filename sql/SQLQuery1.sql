-- Tạo database
CREATE DATABASE CuaHangTienLoi;
GO

USE CuaHangTienLoi;
GO

-- ====================== BẢNG NHÂN VIÊN ======================
CREATE TABLE NhanVien (
    MaNhanVien VARCHAR(10) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    SoDienThoai VARCHAR(20),
    GioiTinh NVARCHAR(10),
    NgaySinh DATE,
    DiaChi NVARCHAR(200)
);

-- ====================== BẢNG TÀI KHOẢN ======================
CREATE TABLE TaiKhoan (
    TenDangNhap NVARCHAR(50) PRIMARY KEY,
    MatKhau NVARCHAR(100) NOT NULL,
    MaNhanVien VARCHAR(10) UNIQUE NOT NULL,
    VaiTro NVARCHAR(50) NOT NULL,
    TrangThai BIT DEFAULT 1,
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien)
);

GO

CREATE PROCEDURE sp_Login
    @TenDangNhap NVARCHAR(50),
    @MatKhau NVARCHAR(100)
AS
BEGIN
    DECLARE @IsValid BIT;

    -- Kiểm tra tài khoản với tên đăng nhập và mật khẩu, phân biệt chữ hoa và chữ thường
    IF EXISTS (
        SELECT 1
        FROM TaiKhoan
        WHERE TenDangNhap COLLATE Latin1_General_BIN = @TenDangNhap COLLATE Latin1_General_BIN
        AND MatKhau COLLATE Latin1_General_BIN = @MatKhau COLLATE Latin1_General_BIN
        AND TrangThai = 1 -- Tài khoản hoạt động
    )
BEGIN
        SET @IsValid = 1;
END
ELSE
BEGIN
        SET @IsValid = 0;
END

    -- Nếu tài khoản hợp lệ, trả về thông tin tài khoản
    IF @IsValid = 1
BEGIN
SELECT TaiKhoan.TenDangNhap, TaiKhoan.MaNhanVien, TaiKhoan.VaiTro, NhanVien.HoTen
FROM TaiKhoan
         JOIN NhanVien ON TaiKhoan.MaNhanVien = NhanVien.MaNhanVien
WHERE TaiKhoan.TenDangNhap = @TenDangNhap;
END
ELSE
BEGIN
        -- Trả về thông báo thất bại nếu đăng nhập không thành công
SELECT 'Lỗi đăng nhập! Tài khoản hoặc mật khẩu không đúng.' AS ErrorMessage;
END
END





-- ====================== BẢNG CA LÀM VIỆC ======================
CREATE TABLE CaLamViec (
    MaCa VARCHAR(10) PRIMARY KEY,
    TenCa NVARCHAR(50),
    GioBatDau TIME NOT NULL,
    GioKetThuc TIME NOT NULL,
    GhiChu NVARCHAR(255)
);

-- ====================== BẢNG TRUNG GIAN NHÂN VIÊN - CA ======================
CREATE TABLE NhanVien_CaLamViec (
    MaNhanVien VARCHAR(10),
    MaCa VARCHAR(10),
    NgayLam DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
    GioVao DATETIME NOT NULL,
    GioRa DATETIME NULL,
    PRIMARY KEY (MaNhanVien, MaCa, NgayLam),
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (MaCa) REFERENCES CaLamViec(MaCa)
);

-- ====================== BẢNG KHÁCH HÀNG ======================
CREATE TABLE KhachHang (
    MaKhachHang VARCHAR(10) PRIMARY KEY,
    TenKhachHang NVARCHAR(100) NOT NULL,
    SoDienThoai VARCHAR(20) UNIQUE NOT NULL,
    DiemTichLuy INT DEFAULT 0
);

-- ====================== BẢNG LOẠI SẢN PHẨM ======================
CREATE TABLE LoaiSanPham (
    MaLoai VARCHAR(10) PRIMARY KEY,
    TenLoai NVARCHAR(100) NOT NULL UNIQUE
);

-- ====================== BẢNG SẢN PHẨM ======================
CREATE TABLE SanPham (
    MaSanPham VARCHAR(10) PRIMARY KEY,
    TenSanPham NVARCHAR(100) NOT NULL,
    DonViTinh NVARCHAR(20),
    GiaBan DECIMAL(10,2) NOT NULL,
    SoLuongTon INT NOT NULL,
    MaLoai VARCHAR(10),
    FOREIGN KEY (MaLoai) REFERENCES LoaiSanPham(MaLoai)
);

-- ====================== BẢNG HÓA ĐƠN ======================
CREATE TABLE HoaDon (
    MaHoaDon VARCHAR(10) PRIMARY KEY,
    MaNhanVien VARCHAR(10) NOT NULL,
    MaKhachHang VARCHAR(10) NULL,
    NgayLap DATETIME NOT NULL DEFAULT GETDATE(),
    TongTien DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKhachHang)
);

-- ====================== BẢNG CHI TIẾT HÓA ĐƠN ======================
CREATE TABLE ChiTietHoaDon (
    MaChiTiet VARCHAR(10) PRIMARY KEY,
    MaHoaDon VARCHAR(10) NOT NULL,
    MaSanPham VARCHAR(10) NOT NULL,
    SoLuong INT NOT NULL,
    DonGia DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (MaHoaDon) REFERENCES HoaDon(MaHoaDon),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham)
);


-- NHÂN VIÊN: NVxxxxx
ALTER TABLE NhanVien
ADD CONSTRAINT chk_MaNhanVien_format
CHECK (MaNhanVien LIKE 'NV[0-9][0-9][0-9][0-9][0-9]');

-- TÀI KHOẢN: TKxxxxx
ALTER TABLE TaiKhoan
ADD CONSTRAINT chk_TenDangNhap_format
CHECK (TenDangNhap LIKE 'TK[0-9][0-9][0-9][0-9][0-9]');

-- CA LÀM VIỆC: CAxxxxx
ALTER TABLE CaLamViec
ADD CONSTRAINT chk_MaCa_format
CHECK (MaCa LIKE 'CA[0-9][0-9][0-9][0-9][0-9]');

-- KHÁCH HÀNG: KHxxxxx
ALTER TABLE KhachHang
ADD CONSTRAINT chk_MaKhachHang_format
CHECK (MaKhachHang LIKE 'KH[0-9][0-9][0-9][0-9][0-9]');

-- LOẠI SẢN PHẨM: LSxxxxx
ALTER TABLE LoaiSanPham
ADD CONSTRAINT chk_MaLoai_format
CHECK (MaLoai LIKE 'LS[0-9][0-9][0-9][0-9][0-9]');

-- SẢN PHẨM: SPxxxxx
ALTER TABLE SanPham
ADD CONSTRAINT chk_MaSanPham_format
CHECK (MaSanPham LIKE 'SP[0-9][0-9][0-9][0-9][0-9]');

-- HÓA ĐƠN: HDxxxxx
ALTER TABLE HoaDon
ADD CONSTRAINT chk_MaHoaDon_format
CHECK (MaHoaDon LIKE 'HD[0-9][0-9][0-9][0-9][0-9]');

-- CHI TIẾT HÓA ĐƠN: CTxxxxx
ALTER TABLE ChiTietHoaDon
ADD CONSTRAINT chk_MaChiTiet_format
CHECK (MaChiTiet LIKE 'CT[0-9][0-9][0-9][0-9][0-9]');



-- 1. Bảng Nhân Viên
INSERT INTO NhanVien (MaNhanVien, HoTen, SoDienThoai, GioiTinh, NgaySinh, DiaChi)
VALUES
('NV00001', 'Nguyễn Văn A', '0909123456', 'Nam', '1990-05-15', 'Hà Nội'),
('NV00002', 'Trần Thị B', '0938123456', 'Nữ', '1985-08-22', 'TP.HCM'),
('NV00003', 'Lê Văn C', '0912123456', 'Nam', '1992-11-10', 'Đà Nẵng'),
('NV00004', 'Phạm Thị D', '0969123456', 'Nữ', '1993-02-20', 'Hải Phòng'),
('NV00005', 'Hồ Văn E', '0972123456', 'Nam', '1988-04-17', 'Cần Thơ');

-- 2. Bảng Tài Khoản
INSERT INTO TaiKhoan (TenDangNhap, MatKhau, MaNhanVien, VaiTro, TrangThai)
VALUES
('TK00001', 'password123', 'NV00001', 'Thu ngân', 1),
('TK00002', 'password456', 'NV00002', 'Quản lý', 1),
('TK00003', 'password789', 'NV00003', 'Nhân viên', 1),
('TK00004', 'password101', 'NV00004', 'Bán hàng', 1),
('TK00005', 'password102', 'NV00005', 'Thu ngân', 1);

-- 3. Bảng Ca Làm Việc
INSERT INTO CaLamViec (MaCa, TenCa, GioBatDau, GioKetThuc, GhiChu)
VALUES
('CA00001', 'Ca sáng', '06:00', '14:00', 'Ca làm việc từ sáng đến chiều'),
('CA00002', 'Ca chiều', '14:00', '22:00', 'Ca làm việc từ chiều đến tối'),
('CA00003', 'Ca đêm', '22:00', '06:00', 'Ca làm việc đêm khuya');

-- 4. Bảng Trung Gian Nhân Viên - Ca
INSERT INTO NhanVien_CaLamViec (MaNhanVien, MaCa, NgayLam, GioVao, GioRa)
VALUES
('NV00001', 'CA00001', '2025-04-22', '06:00', '14:00'),
('NV00002', 'CA00002', '2025-04-22', '14:00', '22:00'),
('NV00003', 'CA00003', '2025-04-22', '22:00', '06:00'),
('NV00004', 'CA00001', '2025-04-22', '06:00', '14:00'),
('NV00005', 'CA00002', '2025-04-22', '14:00', '22:00');

-- 5. Bảng Khách Hàng
INSERT INTO KhachHang (MaKhachHang, TenKhachHang, SoDienThoai, DiemTichLuy)
VALUES
('KH00001', 'Nguyễn Thị Mai', '0912345678', 100),
('KH00002', 'Trần Minh Tuấn', '0909876543', 200),
('KH00003', 'Lê Hoàng Nam', '0932134567', 150),
('KH00004', 'Phạm Ngọc Lan', '0965123456', 50),
('KH00005', 'Hồ Minh Châu', '0976345678', 300);

-- 6. Bảng Loại Sản Phẩm
INSERT INTO LoaiSanPham (MaLoai, TenLoai)
VALUES
('LS00001', 'Chuột'),
('LS00002', 'Bàn phím'),
('LS00003', 'Màn hình'),
('LS00004', 'Tai nghe'),
('LS00005', 'Loa');

-- 7. Bảng Sản Phẩm
INSERT INTO SanPham (MaSanPham, TenSanPham, DonViTinh, GiaBan, SoLuongTon, MaLoai)
VALUES
('SP00001', 'Chuột không dây A', 'Cái', 250000, 100, 'LS00001'),
('SP00002', 'Bàn phím cơ B', 'Cái', 500000, 50, 'LS00002'),
('SP00003', 'Màn hình 24 inch', 'Cái', 3000000, 30, 'LS00003'),
('SP00004', 'Tai nghe Bluetooth', 'Cái', 350000, 75, 'LS00004'),
('SP00005', 'Loa bluetooth', 'Cái', 500000, 60, 'LS00005');

-- 8. Bảng Hóa Đơn
INSERT INTO HoaDon (MaHoaDon, MaNhanVien, MaKhachHang, NgayLap, TongTien)
VALUES
('HD00001', 'NV00001', 'KH00001', '2025-04-22 08:30', 700000),
('HD00002', 'NV00002', 'KH00002', '2025-04-22 09:00', 1200000),
('HD00003', 'NV00003', 'KH00003', '2025-04-22 10:30', 1000000),
('HD00004', 'NV00004', 'KH00004', '2025-04-22 14:00', 800000),
('HD00005', 'NV00005', 'KH00005', '2025-04-22 15:30', 1500000);

-- 9. Bảng Chi Tiết Hóa Đơn
INSERT INTO ChiTietHoaDon (MaChiTiet, MaHoaDon, MaSanPham, SoLuong, DonGia)
VALUES
('CT00001', 'HD00001', 'SP00001', 2, 250000),
('CT00002', 'HD00001', 'SP00002', 1, 500000),
('CT00003', 'HD00002', 'SP00003', 1, 3000000),
('CT00004', 'HD00003', 'SP00004', 2, 350000),
('CT00005', 'HD00004', 'SP00005', 1, 500000);
