package org.prime.internship;

// http://136ea.k.time4vps.cloud/data/

import org.prime.internship.parser.CSVParser;
import org.prime.internship.parser.XMLParser;
import org.prime.internship.utility.Util;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.time.LocalDate;


public class App
{
    public static void main( String[] args ) throws XMLStreamException, IOException {


        Util.printList(new XMLParser("reports/2018-10-01-agivu.xml", "Sekica", LocalDate.now()).readReportBeans());
        Util.printList(new CSVParser("reports/2018-10-01-blogtags.csv", "Yekica", LocalDate.now()).readReportBeans( ));


    }





}
