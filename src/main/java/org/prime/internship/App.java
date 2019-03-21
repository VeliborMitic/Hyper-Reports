package org.prime.internship;

// http://136ea.k.time4vps.cloud/data/


import org.prime.internship.service.ReportService;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class App{
    public static void main(String[] args) {

        ReportService service = new ReportService();
        try {
            service.writeAllFilesFromResourceToDB();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
