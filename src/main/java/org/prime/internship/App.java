package org.prime.internship;

import org.prime.internship.service.DataService;
import org.prime.internship.service.ReportService;
import org.prime.internship.utility.Util;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {

        try {
            Util.downloadNewUrlFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataService service = new DataService();
        try {
            service.writeFilesFromResourceToDB();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }

        ReportService reportService = new ReportService();
        try {
            reportService.generateReportForMonth("agivu", 2018, 10);
            reportService.generateReportForMonth("agivu", 2018, 11);
            reportService.generateReportForQuarter("agivu", 2018, 4);
            reportService.generateReportForYear("agivu", 2018);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

