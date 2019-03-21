package org.prime.internship.service;

import org.prime.internship.utility.Util;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class FileService {
    private CompanyService companyService;
    private List<String> allFiles;
    private List<String> newFiles;

    FileService() {
        this.companyService = new CompanyService();
        this.allFiles = new ArrayList<>();
        this.newFiles = new ArrayList<>();
    }

    List<String> listNewFilesInDirectory() {
        allFiles = Arrays.asList(new File("reports/").listFiles()).parallelStream().map(file ->
                file.getName()).collect(Collectors.toList());
        if (!allFiles.isEmpty()) {
            for (String fileName : allFiles) {
                String[] attributes = parseFileName(fileName);
                if ((companyService.getOneByName(attributes[1]).getLastDocumentDate() != null)
                        && Util.isDateAfter(LocalDate.parse(attributes[0]), companyService.getOneByName(attributes[1]).getLastDocumentDate()))
                    newFiles.add(fileName);
            }
        }
        return newFiles;
    }

    // String[0] - date,  String[1] - companyName,  String[2] - extension
    String[] parseFileName(String fileName) {
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

