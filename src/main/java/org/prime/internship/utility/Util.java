package org.prime.internship.utility;

import java.io.File;
import java.util.Arrays;
import java.util.List;
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
}
