package org.prime.internship.utility;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Util {

    public static void printList (Iterable<?> list){
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static Timestamp convertLocalDateToTimestamp(LocalDate localDate) {
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return Timestamp.valueOf(localDateTime);
    }

    public static boolean isDateAfter(LocalDate date1, LocalDate date2){

        return date1.isAfter(date2);
    }


}
