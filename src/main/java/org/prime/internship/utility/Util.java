package org.prime.internship.utility;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
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

    // String[0] - date,  String[1] - companyName,  String[2] - extension
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
