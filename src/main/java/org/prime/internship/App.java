package org.prime.internship;

// http://136ea.k.time4vps.cloud/data/

import org.prime.internship.parser.XMLParser;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class App
{
    public static void main( String[] args ) throws XMLStreamException, FileNotFoundException {

//        TestStAXParser testStAXParser = new TestStAXParser();
//        testStAXParser.testParse();

        XMLParser xmlParser = new XMLParser();
        xmlParser.readXML();

    }


    public static List<String> listAllFilesInDirectory () {
        File[] files = new File("reports/").listFiles();

        return Arrays.asList(files).parallelStream().map(file ->
                file.getName()).collect(Collectors.toList());
    }

    public static void printList (Iterable<?> list){
        for (Object obj : list)
            System.out.println(obj);
    }
}
