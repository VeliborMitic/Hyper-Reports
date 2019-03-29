package org.prime.internship;

import org.prime.internship.service.DataService;
import org.prime.internship.service.ReportService;
import org.prime.internship.utility.Util;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {

//        try {
//            Util.downloadNewUrlFiles();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        DataService service = new DataService();
//        try {
//            service.writeFilesFromResourceToDB();
//        } catch (IOException | XMLStreamException e) {
//            e.printStackTrace();
//        }

        ReportService reportService = new ReportService();
        try {
            reportService.generateReportForMonth("agivu", 2018, 10);
            reportService.generateReportForQuarter("agivu", 2018, 4);
            reportService.generateReportForYear("agivu", 2018);

            reportService.monthlyTopNEmployees("agivu", 2018, 10, 15);
            reportService.quarterlyTopNEmployees("agivu", 2018, 4, 15);
            reportService.yearlyTopNEmployees("agivu", 2018, 15);

            reportService.monthlyTopNCities("agivu", 2018, 10, 10);
            reportService.quarterlyTopNCities("agivu", 2018, 4, 10);
            reportService.yearlyTopNCities("agivu", 2018, 10);

            reportService.monthlyTopNDepartments("agivu", 2018, 10, 5);
            reportService.quarterlyTopNDepartments("agivu", 2018, 4, 5);
            reportService.yearlyTopNDepartments("agivu", 2018, 5);

            reportService.monthlyBottomNEmployees("agivu", 2018, 10, 15);
            reportService.quarterlyBottomNEmployees("agivu", 2018, 4, 15);
            reportService.yearlyBottomNEmployees("agivu", 2018, 15);

            reportService.monthlyBottomNCities("agivu", 2018, 10, 10);
            reportService.quarterlyBottomNCities("agivu", 2018, 4, 10);
            reportService.yearlyBottomNCities("agivu", 2018, 10);

            reportService.monthlyBottomNDepartments("agivu", 2018, 10, 5);
            reportService.quarterlyBottomNDepartments("agivu", 2018, 4, 5);
            reportService.yearlyBottomNDepartments("agivu", 2018, 5);


        }
        catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

