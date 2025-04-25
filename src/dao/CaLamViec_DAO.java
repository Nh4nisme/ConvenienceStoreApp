package dao;

import java.time.LocalTime;

public class CaLamViec_DAO {

    public String getMaCaFromCurrentTime() {
        LocalTime currentTime = LocalTime.now();

        if (currentTime.isAfter(LocalTime.of(6, 0)) && currentTime.isBefore(LocalTime.of(14, 0))) {
            return "CA00001"; // Ca sáng
        }
        else if (currentTime.isAfter(LocalTime.of(14, 0)) && currentTime.isBefore(LocalTime.of(22, 0))) {
            return "CA00002"; // Ca chiều
        }
        else if (currentTime.isAfter(LocalTime.of(22, 0)) || currentTime.isBefore(LocalTime.of(6, 0))) {
            return "CA00003"; // Ca tối
        }
        return null;
    }
}
