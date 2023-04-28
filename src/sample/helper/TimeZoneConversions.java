package sample.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeZoneConversions {
    private static String pattern = "yyyy-MM-dd HH:mm:ss";


    public static String localDateTimeToString(LocalDateTime localDateTime){
        String ldt = localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        return ldt;
    }

    public static String zonedDateTimeToString(ZonedDateTime zonedDateTime){
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        String ldt = localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        return ldt;
    }

    public static LocalDateTime stringToLocalDateTime(String localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime ldt = LocalDateTime.parse(localDateTime, formatter);
        return ldt;
    }

    public static ZonedDateTime stringToUTC(String localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime ldt = LocalDateTime.parse(localDateTime, formatter);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        return zdt;
    }

    public static ZonedDateTime systemDateTimeToUTC(LocalDateTime localDateTime){
        ZonedDateTime ldtZoned = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZoned;
    }

    public static ZonedDateTime systemDateTimeToEST(LocalDateTime localDateTime){
        ZonedDateTime ldtZoned = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("US/Eastern"));
        return utcZoned;
    }



    public static ZonedDateTime UTCToSystemDateTime(ZonedDateTime zonedDateTime){
        ZonedDateTime zdtSystem = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        return zdtSystem;
    }

    public static ZonedDateTime UTCtoEST(ZonedDateTime zonedDateTime){
        if(zonedDateTime.getZone()==ZoneId.of("UTC")){
            return zonedDateTime.withZoneSameInstant(ZoneId.of("US/Eastern"));
        }
        else{
            return null;
        }
    }

    public static LocalDateTime UTCToSystem(LocalDateTime UTCLocalDateTime){
        ZonedDateTime zdtUTC = UTCLocalDateTime.atZone(ZoneId.of("UTC"));
        System.out.println("UTC : " + zdtUTC);
        ZonedDateTime zdtSystem = zdtUTC.withZoneSameInstant(ZoneId.systemDefault());
        System.out.println("System : " + zdtSystem);
        System.out.println("Local : " + zdtSystem.toLocalDateTime());
        return zdtSystem.toLocalDateTime();
    }


}
