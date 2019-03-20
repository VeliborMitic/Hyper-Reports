package org.prime.internship.service;

import org.prime.internship.entity.dto.DailyReportBean;
import org.prime.internship.parser.CSVParser;
import org.prime.internship.parser.XMLParser;
import org.prime.internship.utility.Util;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public class ReportService {
    private CompanyService companyService;
    private DepartmentService departmentService;
    private CityService cityService;
    private EmployeeService employeeService;
    private TurnoverService turnoverService;
    private List<DailyReportBean> list;

    public ReportService(){
        this.companyService = new CompanyService();
        this.departmentService = new DepartmentService();
        this.cityService = new CityService();
        this.employeeService = new EmployeeService();
        this.turnoverService = new TurnoverService();
    }

    public void writeAllFilesFromResourceToDB() throws IOException, XMLStreamException {
        List<String> allFilesList = Util.listAllFilesInDirectory();

        for (String fileName : allFilesList) {
            String[] attributes = Util.parseFileName(fileName);

            if (attributes[2].equalsIgnoreCase("csv")) {
                list = new CSVParser().readReportBeans("reports/" + fileName);
                processFile(attributes);

            } else if (attributes[2].equalsIgnoreCase("xml")) {
                list = new XMLParser().readReportBeans("reports/" + fileName);
                processFile(attributes);
            }
        }
    }

    private void processFile(String[] attributes) {
        list.forEach(bean -> turnoverService.processTurnoverToDB(
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
