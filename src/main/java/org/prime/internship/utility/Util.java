package org.prime.internship.utility;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Util {
    public static final String PATH = "E:\\_PRIME\\HyperReports_Reports/";
    private static final String URL = "http://136ea.k.time4vps.cloud/data/";
    private static final String REGEX = ".*(\\d{4}-\\d{2}-\\d{2})-(.*)\\.(.*)";

    public static void printList(Iterable<?> list) {
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static Timestamp convertLocalDateToTimestamp(LocalDate localDate) {
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return Timestamp.valueOf(localDateTime);
    }

    // String[0] - date,  String[1] - companyName,  String[2] - extension
    public static String[] parseFileName(String fileName) {
        String[] strings = new String[3];
        Pattern pattern = Pattern.compile(REGEX);
        Matcher m = pattern.matcher(fileName);

        if (m.find()) {
            strings[0] = m.group(1);
            strings[1] = m.group(2);
            strings[2] = m.group(3);
        }
        return strings;
    }

    public static List<String> findFilesInLocalDir() {
        return Arrays.asList(new File(PATH).listFiles()).parallelStream().map(file ->
                file.getName()).collect(Collectors.toList());
    }

    public static void downloadNewUrlFiles() throws IOException {
        List<String> remoteDirFileNames = new ArrayList<>();
        Pattern pattern = Pattern.compile(REGEX);

        Document doc = Jsoup.connect(URL).get();
        for (Element file : doc.select("a[href]")) {
            Matcher m = pattern.matcher(file.attr("href"));
            if (m.find()) {
                remoteDirFileNames.add(m.group());
            }
        }
        for (String fileName : remoteDirFileNames) {
            if (!findFilesInLocalDir().contains(fileName)) {
                FileUtils.copyURLToFile(
                        new URL(URL + fileName),
                        new File(PATH + fileName),
                        10000,
                        10000);
                System.out.println(fileName + " downloaded");
            }
        }
    }
}
