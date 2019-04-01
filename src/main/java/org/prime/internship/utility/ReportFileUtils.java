package org.prime.internship.utility;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.prime.internship.PropertiesReader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReportFileUtils {

    private ReportFileUtils() {
    }
    private static Properties prop;

    static {
        try {
            prop = PropertiesReader.properties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String DATA_INPUT_PATH = prop.getProperty("local.reports.folder");
    public static final String REPORT_OUTPUT_PATH = prop.getProperty("local.output.folder");
    private static final String URL = prop.getProperty("remote.source.directory");
    private static final String REGEX = ".*(\\d{4}-\\d{2}-\\d{2})-(.*)\\.(.*)";

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

    public static Set<String> findFilesInLocalDir() {
        return Arrays.asList(Objects.requireNonNull(new File(DATA_INPUT_PATH).listFiles())).parallelStream()
                .map(File::getName).collect(Collectors.toCollection(TreeSet::new));
    }

    public static void downloadNewUrlFiles() throws IOException {
        Set<String> remoteDirFileNames = new TreeSet<>();
        Set<String> localDirFileNames = findFilesInLocalDir();
        int countDownloadedFiles = 0;

        Pattern pattern = Pattern.compile(REGEX);

        Document doc = Jsoup.connect(URL).get();
        for (Element file : doc.select("a[href]")) {
            Matcher m = pattern.matcher(file.attr("href"));
            if (m.find()) {
                remoteDirFileNames.add(m.group());
            }
        }
        for (String fileName : remoteDirFileNames) {
            if (!localDirFileNames.contains(fileName)) {
                FileUtils.copyURLToFile(
                        new URL(URL + fileName),
                        new File(DATA_INPUT_PATH + fileName),
                        10000,
                        10000);
                System.out.println(fileName + " downloaded");
                countDownloadedFiles++;
            }
        }
        if (countDownloadedFiles == 0) {
            System.out.println("No new reports in remote directory!");
        }
        else {
            System.out.println("Downloaded " + countDownloadedFiles + " files.");
        }
    }
}
