package org.prime.internship;

// http://136ea.k.time4vps.cloud/data/


import org.prime.internship.parser.CSVParser;
import org.prime.internship.parser.XMLParser;
import org.prime.internship.service.ReportService;
import org.prime.internship.utility.Util;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException, XMLStreamException {

        ReportService.harvestDailyReportsFromResource();

        //System.out.println(ReportService.listAllFilesInDirectory());

        /*try {
            Util.printList(new XMLParser().readReportBeans("reports/2018-10-01-agivu.xml"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (XMLStreamException ex) {
            System.out.println("Bac XML file!");
        }

        try {
            Util.printList(new CSVParser().readReportBeans("reports/2018-10-01-blogtags.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //FileHandler.downloadFolder()








    }
//        public static List<DailyReportBean> processCSVFile(String fileName) throws IOException {
//            return new CSVParser().readReportBeans(fileName);
//        }
//
//        public static List<DailyReportBean> processXMLFile(String fileName) throws IOException, XMLStreamException {
//            return new XMLParser().readReportBeans(fileName);
//        }



}
