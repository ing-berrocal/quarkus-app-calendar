package model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record ScheduleQuery(Integer time,Integer durationInMinutes,Boolean isValid) {
    
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    
    public String getTimeFormatted(){
        return LocalTime.of(0, 0).plusMinutes(this.time).format(formatter);
    }
}
