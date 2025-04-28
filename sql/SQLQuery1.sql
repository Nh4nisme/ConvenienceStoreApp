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
                         LinkAnh NVARCHAR(255),
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

-- ====================== KIỂM TRA ĐỊNH DẠNG ======================

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

-- ====================== CHÈN DỮ LIỆU ======================

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
    ('KH00001', N'Nguyễn Thị Mai', '0912345678', 100),
    ('KH00002', N'Trần Minh Tuấn', '0909876543', 200),
    ('KH00003', N'Lê Hoàng Nam', '0932134567', 150),
    ('KH00004', N'Phạm Ngọc Lan', '0965123456', 50),
    ('KH00005', N'Hồ Minh Châu', '0976345678', 300);

-- 6. Bảng Loại Sản Phẩm
INSERT INTO LoaiSanPham (MaLoai, TenLoai)
VALUES
    ('LS00001', N'Nước suối'),
    ('LS00002', N'Mì gói'),
    ('LS00003', N'Bánh quy'),
    ('LS00004', N'Sữa tươi'),
    ('LS00005', N'Cá hộp');

-- 7. Bảng Sản Phẩm[CuaHangTienLoi]
INSERT INTO SanPham (MaSanPham, TenSanPham, DonViTinh, GiaBan, SoLuongTon, MaLoai, LinkAnh)
VALUES
    ('SP00001', N'Nước suối Aquafina', 'Chai', 10.00, 100, 'LS00001', 'nuoc_suoi.png'),
    ('SP00002', N'Mì gói Hảo Hảo', 'Gói', 5.00, 50, 'LS00002', 'mi_haohao.png'),
    ('SP00003', N'Bánh quy Oreo', 'Hộp', 20.00, 75, 'LS00003', 'banhquy_socola.png'),
    ('SP00004', N'Sữa tươi Vinamilk', 'Chai', 15.00, 200, 'LS00004', 'sua_tuoi.png'),
    ('SP00005', N'Cá hộp Hà Nội', 'Hộp', 30.00, 60, 'LS00005', 'cahop.png');

-- 8. Bảng Hóa Đơn
INSERT INTO HoaDon (MaHoaDon, MaNhanVien, MaKhachHang, TongTien)
VALUES
    ('HD00001', 'NV00001', 'KH00001', 100.00),
    ('HD00002', 'NV00002', 'KH00002', 200.00),
    ('HD00003', 'NV00003', 'KH00003', 150.00),
    ('HD00004', 'NV00004', 'KH00004', 50.00),
    ('HD00005', 'NV00005', 'KH00005', 300.00);

-- 9. Bảng Chi Tiết Hóa Đơn
INSERT INTO ChiTietHoaDon (MaChiTiet, MaHoaDon, MaSanPham, SoLuong, DonGia)
VALUES
    ('CT00001', 'HD00001', 'SP00001', 10, 10.00),
    ('CT00002', 'HD00001', 'SP00002', 5, 5.00),
    ('CT00003', 'HD00002', 'SP00003', 2, 20.00),
    ('CT00004', 'HD00002', 'SP00004', 3, 15.00),
    ('CT00005', 'HD00003', 'SP00005', 3, 30.00);

GO

CREATE PROCEDURE sp_Login
    @TenDangNhap NVARCHAR(50),
    @MatKhau NVARCHAR(100)
AS
BEGIN
    DECLARE @IsValid BIT;

    -- Kiểm tra tài khoản với tên đăng nhập và mật khẩu
SELECT @IsValid = CASE
                      WHEN COUNT(*) > 0 THEN 1
                      ELSE 0
    END
FROM TaiKhoan
WHERE TenDangNhap = @TenDangNhap AND MatKhau = @MatKhau AND TrangThai = 1; -- TrangThai = 1 chỉ tài khoản hoạt động

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
SELECT NULL AS TenDangNhap, NULL AS MaNhanVien, NULL AS VaiTro, NULL AS HoTen;
END
END

GO

CREATE PROCEDURE sp_UpdatePassword
    @username NVARCHAR(50),
    @newPassword NVARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (SELECT 1 FROM TaiKhoan WHERE TenDangNhap = @username)
BEGIN
UPDATE TaiKhoan
SET MatKhau = @newPassword
WHERE TenDangNhap = @username;

RETURN 1; -- Success
END
ELSE
BEGIN
RETURN -1; -- User not found
END
END

GO

-- get ALL Product
CREATE PROCEDURE sp_GetAllSanPham
    AS
BEGIN
SELECT * FROM SanPham;
END

GO

--Create Product
CREATE PROCEDURE sp_ThemSanPham
    @MaSanPham VARCHAR(10),
    @TenSanPham NVARCHAR(255),
    @DonViTinh NVARCHAR(50),
    @GiaBan FLOAT,
    @SoLuongTon INT,
    @MaLoai VARCHAR(10),
    @LinkAnh NVARCHAR(255)
AS
BEGIN
    INSERT INTO SanPham (MaSanPham, TenSanPham, DonViTinh, GiaBan, SoLuongTon, MaLoai, LinkAnh)
    VALUES (@MaSanPham, @TenSanPham, @DonViTinh, @GiaBan, @SoLuongTon, @MaLoai, @LinkAnh);
END

--Update SP
CREATE PROCEDURE sp_CapNhatSanPham
   @MaSanPham VARCHAR(10), 
    @TenSanPham NVARCHAR(100), 
    @DonViTinh NVARCHAR(20), 
    @GiaBan DECIMAL(10,2), 
    @SoLuongTon INT, 
    @MaLoai VARCHAR(10), 
    @LinkAnh NVARCHAR(255)
AS
BEGIN
   UPDATE SanPham
    SET TenSanPham = @TenSanPham,
        DonViTinh = @DonViTinh,
        GiaBan = @GiaBan,
        SoLuongTon = @SoLuongTon,
        MaLoai = @MaLoai,
        LinkAnh = @LinkAnh
    WHERE MaSanPham = @MaSanPham;
END

GO

--Delete SP
CREATE PROCEDURE sp_XoaSanPham
    @MaSanPham VARCHAR(10)
AS
BEGIN
DELETE FROM SanPham
WHERE MaSanPham = @MaSanPham;
END

GO

--Tim SP
CREATE PROCEDURE sp_TimSanPhamTheoTen
    @TenSanPham NVARCHAR(255)
AS
BEGIN
SELECT * FROM SanPham
WHERE TenSanPham LIKE '%' + @TenSanPham + '%';
END

GO

--Giam SL Ton Kho
CREATE PROCEDURE sp_GiamSoLuongTon
    @MaSanPham VARCHAR(10),
    @SoLuong INT
AS
BEGIN
UPDATE SanPham
SET SoLuongTon = SoLuongTon - @SoLuong
WHERE MaSanPham = @MaSanPham AND SoLuongTon >= @SoLuong;
END

GO

CREATE PROCEDURE sp_InsertShift
    @MaNhanVien VARCHAR(10),
    @MaCa VARCHAR(10),
    @GioVao DATETIME
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM NhanVien_CaLamViec WHERE MaNhanVien = @MaNhanVien AND MaCa = @MaCa AND NgayLam = CAST(@GioVao AS DATE))
BEGIN
        -- Nếu không tồn tại, thực hiện chèn
INSERT INTO NhanVien_CaLamViec (MaNhanVien, MaCa, NgayLam, GioVao)
VALUES (@MaNhanVien, @MaCa, CAST(@GioVao AS DATE), @GioVao);
END
END

GO

CREATE PROCEDURE sp_UpdateGioRaNhanVien
    @MaNhanVien VARCHAR(10),
    @MaCa VARCHAR(10)
AS
BEGIN
    DECLARE @rowsAffected INT;

UPDATE NhanVien_CaLamViec
SET GioRa = GETDATE()
WHERE MaNhanVien = @MaNhanVien
  AND MaCa = @MaCa
  AND CAST(NgayLam AS DATE) = CAST(GETDATE() AS DATE);

SET @rowsAffected = @@ROWCOUNT;

RETURN @rowsAffected;
END


GO

CREATE PROCEDURE sp_GetShiftsByEmployeeID
    @MaNhanVien VARCHAR(10)
AS
BEGIN
SELECT
    MaCa,
    NgayLam,
    CAST(GioVao AS DATETIME) AS GioVao,
    CAST(GioRa AS DATETIME) AS GioRa
FROM NhanVien_CaLamViec
WHERE MaNhanVien = @MaNhanVien
ORDER BY
    NgayLam DESC, GioVao;
END

GO

CREATE PROCEDURE sp_TopSanPhamBanChay
AS
BEGIN
    SELECT TOP 5 
        sp.TenSanPham,
        SUM(ct.SoLuong) AS TongSoLuongBan
    FROM ChiTietHoaDon ct
    JOIN SanPham sp ON ct.MaSanPham = sp.MaSanPham
    GROUP BY sp.MaSanPham, sp.TenSanPham
    ORDER BY TongSoLuongBan DESC
END

GO
CREATE PROCEDURE sp_DoanhThuTuanTrongThang
AS
BEGIN
    SET NOCOUNT ON;

    -- Lấy ngày đầu và cuối tháng hiện tại
    DECLARE @NgayDauThang DATE = DATEFROMPARTS(YEAR(GETDATE()), MONTH(GETDATE()), 1);
    DECLARE @NgayCuoiThang DATE = EOMONTH(@NgayDauThang);

    -- Tính doanh thu theo tuần trong tháng (chú ý tới việc tính tuần)
    SELECT 
        DATEPART(WEEK, NgayLap) - DATEPART(WEEK, @NgayDauThang) + 1 AS Tuan,
        SUM(TongTien) AS DoanhThu
    FROM HoaDon
    WHERE NgayLap >= @NgayDauThang AND NgayLap <= @NgayCuoiThang
    GROUP BY DATEPART(WEEK, NgayLap)
    ORDER BY DATEPART(WEEK, NgayLap);
END;

GO
CREATE PROCEDURE CapNhatDiemTichLuy
    @MaKhachHang VARCHAR(10),
    @TongTien DECIMAL(10,2)
AS
BEGIN
    DECLARE @DiemMoi INT;


    SET @DiemMoi = FLOOR(@TongTien / 10000);


    IF EXISTS (SELECT 1 FROM KhachHang WHERE MaKhachHang = @MaKhachHang)
    BEGIN
        UPDATE KhachHang
        SET DiemTichLuy = DiemTichLuy + @DiemMoi
        WHERE MaKhachHang = @MaKhachHang;
    END
END



