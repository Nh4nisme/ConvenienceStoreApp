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
    ('LS00005', N'Cá hộp'),
    ('LS00006', N'Đồ uống có ga'),
    ('LS00007', N'Bánh kẹo'),
    ('LS00008', N'Gia vị và thực phẩm chế biến sẵn'),
    ('LS00009', N'Chăm sóc sức khỏe'),
    ('LS00010', N'Đồ gia dụng');

-- 7. Bảng Sản Phẩm[CuaHangTienLoi]
INSERT INTO SanPham (MaSanPham, TenSanPham, DonViTinh, GiaBan, SoLuongTon, MaLoai, LinkAnh)
VALUES
    ('SP00001', N'Nước suối Aquafina', 'Chai', 10.00, 100, 'LS00001', 'nuoc_suoi.png'),
    ('SP00002', N'Mì gói Hảo Hảo', 'Gói', 5.00, 50, 'LS00002', 'mi_haohao.png'),
    ('SP00003', N'Bánh quy Oreo', 'Hộp', 20.00, 75, 'LS00003', 'banhquy_socola.png'),
    ('SP00004', N'Sữa tươi Vinamilk', 'Chai', 15.00, 200, 'LS00004', 'sua_tuoi.png'),
    ('SP00005', N'Cá hộp Hà Nội', 'Hộp', 30.00, 60, 'LS00005', 'cahop.png'),
    ('SP00006', N'Nước suối LaVie 1L', 'Chai', 12.00, 150, 'LS00001', 'lavie.png'),
    ('SP00007', N'Nước suối Evian 500ml', 'Chai', 25.00, 80, 'LS00001', 'evian.png'),
    ('SP00008', N'Nước suối Dasani 600ml', 'Chai', 18.00, 90, 'LS00001', 'dasani.png'),
    ('SP00009', N'Nước suối Perrier 330ml', 'Chai', 30.00, 70, 'LS00001', 'perrier.png'),
    ('SP00010', N'Nước suối Nestlé Pure Life 1L', 'Chai', 15.00, 110, 'LS00001', 'Nestle_Pure_life.png'),
    ('SP00011', N'Nước khoáng Fiji 500ml', 'Chai', 50.00, 60, 'LS00001', 'fiji.png'),
    ('SP00012', N'Mì Gấu Đỏ (thịt bò)', 'Gói', 5.00, 50, 'LS00002', 'GauDo.png'),
    ('SP00013', N'Mì Vifon (gà)', 'Gói', 6.00, 60, 'LS00002', 'Vifon.png'),
    ('SP00014', N'Mì Omachi (tôm hùm)', 'Gói', 10.00, 80, 'LS00002', 'omachi.png'),
    ('SP00015', N'Bánh quy Digestive', 'Hộp', 25.00, 60, 'LS00003', 'Digestive.png'),
    ('SP00016', N'Bánh quy Costo (Vani)', 'Hộp', 15.00, 80, 'LS00003', 'Costo.png'),
    ('SP00017', N'Bánh quy Marie (Nestlé)', 'Hộp', 18.00, 90, 'LS00003', 'marie.png'),
    ('SP00018', N'Bánh quy Choco Pie', 'Hộp', 30.00, 70, 'LS00003', 'chocopie.png'),
    ('SP00019', N'Bánh quy Ritz', 'Hộp', 22.00, 65, 'LS00003', 'ritz.png'),
    ('SP00020', N'Bánh quy Cream (Lotte)', 'Hộp', 28.00, 50, 'LS00003', 'cream.png'),
    ('SP00021', N'Sữa tươi TH True Milk', 'Chai', 20.00, 150, 'LS00004', 'THTrueMilk.png'),
    ('SP00022', N'Sữa tươi Mộc Châu', 'Chai', 18.00, 180, 'LS00004', 'MocChau.png'),
    ('SP00023', N'Sữa tươi FrieslandCampina Dutch Lady', 'Chai', 25.00, 200, 'LS00004', 'dutchlady.png'),
    ('SP00024', N'Sữa tươi NutiFood', 'Chai', 22.00, 170, 'LS00004', 'nutifood.png'),
    ('SP00025', N'Sữa tươi Đà Lạt Milk', 'Chai', 19.00, 160, 'LS00004', 'dalat.png'),
    ('SP00026', N'Sữa tươi Ovaltine', 'Chai', 24.00, 140, 'LS00004', 'ovaltine.png'),
    ('SP00027', N'Cá ngừ hộp (John West)', 'Hộp', 40.00, 80, 'LS00005', 'johnwest.png'),
    ('SP00028', N'Cá thu hộp (Hạ Long)', 'Hộp', 35.00, 90, 'LS00005', 'halong.png'),
    ('SP00029', N'Cá mackerel hộp (Biển Đông)', 'Hộp', 45.00, 75, 'LS00005', 'mackerel.png'),
    ('SP00030', N'Cá nục hộp (Diana)', 'Hộp', 38.00, 70, 'LS00005', 'canuc.png'),
    ('SP00031', N'Coca Cola 330ml', 'Chai', 12.00, 100, 'LS00006', 'coca.png'),
    ('SP00032', N'Pepsi 330ml', 'Chai', 11.00, 110, 'LS00006', 'Pepsi.png'),
    ('SP00033', N'Sprite 500ml', 'Chai', 15.00, 90, 'LS00006', 'Sprite.png'),
    ('SP00034', N'Fanta (cam, nho, dâu)', 'Chai', 18.00, 80, 'LS00006', 'Fanta.png'),
    ('SP00035', N'7Up 330ml', 'Chai', 14.00, 120, 'LS00006', '7Up.png'),
    ('SP00036', N'Sunkist (cam, nho)', 'Chai', 16.00, 85, 'LS00006', 'Sunkist.png'),
    ('SP00037', N'Schweppes 330ml (chanh, quất)', 'Chai', 20.00, 95, 'LS00006', 'Schweppes.png'),
    ('SP00038', N'Kẹo dẻo dâu (Haribo)', 'Gói', 25.00, 100, 'LS00007', 'harobi.png'),
    ('SP00039', N'Kẹo Socola (Cadbury)', 'Gói', 30.00, 90, 'LS00007', 'cadbury.png'),
    ('SP00040', N'Kẹo cao su (Hubba Bubba)', 'Gói', 15.00, 120, 'LS00007', 'hubabuba.png'),
    ('SP00041', N'Kẹo M&M’s (Socola đậu phộng)', 'Gói', 35.00, 80, 'LS00007', 'mm.png'),
    ('SP00043', N'Nước mắm Phú Quốc', 'Chai', 45.00, 100, 'LS00008', 'mam.png'),
    ('SP00044', N'Dầu ăn Meizan', 'Chai', 50.00, 90, 'LS00008', 'meizan.png'),
    ('SP00045', N'Bột ngọt Ajinomoto', 'Gói', 25.00, 120, 'LS00008', 'ajinomoto.png'),
    ('SP00046', N'Xốt mayonnaise (Hellmann’s)', 'Chai', 60.00, 80, 'LS00008', 'mayone.png'),
    ('SP00047', N'Xốt cà chua Heinz', 'Chai', 55.00, 85, 'LS00008', 'heinz.png'),
    ('SP00048', N'Hạt nêm Knorr', 'Gói', 35.00, 95, 'LS00008', 'knorr.png'),
    ('SP00049', N'Thuốc giảm đau Panadol', 'Hộp', 35.00, 100, 'LS00009', 'panadol.png'),
    ('SP00050', N'Vitamin C (Citrus)', 'Hộp', 40.00, 90, 'LS00009', 'vitaminC.png'),
    ('SP00051', N'Thuốc ho Prospan', 'Chai', 50.00, 80, 'LS00009', 'prospan.png'),
    ('SP00052', N'Thuốc cảm cúm (Theraflu)', 'Hộp', 45.00, 85, 'LS00009', 'flu.png'),
    ('SP00053', N'Vitamin B Complex', 'Hộp', 55.00, 70, 'LS00009', 'vitaminB.png'),
    ('SP00054', N'Nước rửa tay Lifebuoy', 'Chai', 30.00, 120, 'LS00009', 'lifebouy.png'),
    ('SP00055', N'Thuốc bổ sung canxi (Caltrate)', 'Hộp', 60.00, 75, 'LS00009', 'canxi.png'),
    ('SP00056', N'Giấy vệ sinh Scott', 'Cuộn', 20.00, 100, 'LS00010', 'scott.png'),
    ('SP00057', N'Dầu gội Head & Shoulders', 'Chai', 65.00, 90, 'LS00010', 'shampoo.png'),
    ('SP00058', N'Bột giặt OMO', 'Túi', 70.00, 85, 'LS00010', 'omo.png'),
    ('SP00059', N'Nước lau sàn Cif', 'Chai', 50.00, 80, 'LS00010', 'cif.png'),
    ('SP00060', N'Màng bọc thực phẩm Glad', 'Hộp', 35.00, 95, 'LS00010', 'glad.png'),
    ('SP00061', N'Nước rửa chén Sunlight', 'Chai', 40.00, 100, 'LS00010', 'sunlight.png');

-- 8. Bảng Hóa Đơn
INSERT INTO HoaDon (MaHoaDon, MaNhanVien, MaKhachHang, NgayLap, TongTien)
VALUES
    ('HD00001', 'NV00001', 'KH00001', '2025-04-28 00:00:00', 100.00),
    ('HD00002', 'NV00002', 'KH00002', '2025-04-28 00:00:00', 200.00),
    ('HD00003', 'NV00003', 'KH00003', '2025-04-28 00:00:00', 150.00),
    ('HD00004', 'NV00004', 'KH00004', '2025-04-28 00:00:00', 50.00),
    ('HD00005', 'NV00005', 'KH00005', '2025-04-28 00:00:00', 300.00);

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

GO

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

CREATE PROCEDURE [dbo].[sp_LayTatCaTenLoaiSanPham]
AS
BEGIN
SELECT DISTINCT l.TenLoai
FROM SanPham s
         JOIN LoaiSanPham l ON s.MaLoai = l.MaLoai;
END;

GO

CREATE PROCEDURE [dbo].[sp_TimSanPhamTheoMa]
    @MaSanPham NVARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;

SELECT *
FROM SanPham
WHERE UPPER(MaSanPham) = UPPER(@MaSanPham);
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

SELECT
    -- Tính số tuần trong tháng bằng cách lấy số ngày từ ngày đầu tháng đến ngày lập hóa đơn chia cho 7
    (DATEDIFF(DAY, @NgayDauThang, NgayLap) / 7) + 1 AS Tuan,
    SUM(TongTien) AS DoanhThu
FROM HoaDon
WHERE NgayLap >= @NgayDauThang AND NgayLap <= @NgayCuoiThang
GROUP BY (DATEDIFF(DAY, @NgayDauThang, NgayLap) / 7)
ORDER BY Tuan;
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
