package org.prime.internship.utility;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Util {

    public static void printList (Iterable<?> list){
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static List<String> listAllFilesInDirectory () {
        File[] files = new File("reports/").listFiles();

        return Arrays.asList(files).parallelStream().map(file ->
            file.getName()).collect(Collectors.toList());
    }

    public static String[] parseFileName (String fileName){
        String[] strings = new String[3];
        String regex = ".*(\\d{4}-\\d{2}-\\d{2})-(.*)\\.(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(fileName);

        if (m.find()) {
            strings[0] = m.group(1);
            strings[1] = m.group(2);
            strings[2] = m.group(3);
        }
        return strings;
    }


}
