package org.prime.internship.service;

import org.prime.internship.entity.dto.DailyReport;
import org.prime.internship.parser.CSVParser;
import org.prime.internship.parser.XMLParser;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public class ReportService {
    private CompanyService companyService;
    private DepartmentService departmentService;
    private CityService cityService;
    private EmployeeService employeeService;
    private TurnoverService turnoverService;
    private FileService fileService;
    private List<DailyReport> dailyReportList;

    public ReportService() {
        this.companyService = new CompanyService();
        this.departmentService = new DepartmentService();
        this.cityService = new CityService();
        this.employeeService = new EmployeeService();
        this.turnoverService = new TurnoverService();
        this.fileService = new FileService();
    }

    public void writeFilesFromResourceToDB() throws IOException, XMLStreamException {
        List<String> newFilesList = fileService.listNewFilesInDirectory();

        if (!newFilesList.isEmpty()) {
            for (String fileName : newFilesList) {
                String[] attributes = fileService.parseFileName(fileName);

                if (attributes[2].equalsIgnoreCase("csv")) {
                    dailyReportList = new CSVParser().readReportBeans("reports/" + fileName);

                    processFile(attributes);

                } else if (attributes[2].equalsIgnoreCase("xml")) {
                    dailyReportList = new XMLParser().readReportBeans("reports/" + fileName);

                    processFile(attributes);
                }
            }
        } else {
            System.out.println("All files already processed!");
        }
    }

    private void processFile(String[] attributes) {
        dailyReportList.forEach(bean -> turnoverService.processTurnoverToDB(
                employeeService.processEmployeeToDB(
                        bean.getEmployee(),
                        companyService.processCompanyToDB(attributes[1], attributes[0]).getCompanyId(),
                        cityService.processCityToDB(bean.getCity()).getCityId(),
                        departmentService.processDepartmentToDB(bean.getDepartment()).getDepartmentId()).getEmployeeId(),
                attributes[0],
                bean.getTurnover()
        ));
    }
}
