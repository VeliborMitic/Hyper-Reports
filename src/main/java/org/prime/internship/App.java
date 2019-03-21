package org.prime.internship;

import org.prime.internship.service.ReportService;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class App {
    public static void main(String[] args) {

        ReportService service = new ReportService();
        try {
            service.writeFilesFromResourceToDB();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
