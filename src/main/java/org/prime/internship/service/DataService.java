package org.prime.internship.service;

import org.prime.internship.entity.Company;
import org.prime.internship.entity.dto.DailyReport;
import org.prime.internship.parser.CSVParser;
import org.prime.internship.parser.XMLParser;
import org.prime.internship.utility.Util;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataService {
    private CompanyService companyService;
    private DepartmentService departmentService;
    private CityService cityService;
    private EmployeeService employeeService;
    private TurnoverService turnoverService;
    private List<DailyReport> dailyReportList;
    private List<String> allFiles;
    private List<String> newFiles;

    public DataService() {
        this.companyService = new CompanyService();
        this.departmentService = new DepartmentService();
        this.cityService = new CityService();
        this.employeeService = new EmployeeService();
        this.turnoverService = new TurnoverService();
        this.allFiles = new ArrayList<>();
        this.newFiles = new ArrayList<>();
    }

    public void writeFilesFromResourceToDB() throws IOException, XMLStreamException {

        List<String> newFilesList = listNewFilesInDirectory();
        if (!newFilesList.isEmpty()) {
            for (String fileName : newFilesList) {
                String[] attributes = Util.parseFileName(fileName);

                if (attributes[2].equalsIgnoreCase("csv")) {
                    dailyReportList = new CSVParser().readReportBeans(Util.PATH + fileName);
                    processFile(attributes);
                } else if (attributes[2].equalsIgnoreCase("xml")) {
                    dailyReportList = new XMLParser().readReportBeans(Util.PATH + fileName);
                    processFile(attributes);
                }
                System.out.println("File \"" + fileName + "\" processed!");
            }
        } else {
            System.out.println("All files in source directory already processed!");
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

    private List<String> listNewFilesInDirectory() {
        allFiles = Util.findFilesInLocalDir();

        if (!allFiles.isEmpty()) {
            for (String fileName : allFiles) {
                String[] attributes = Util.parseFileName(fileName);

                Optional<Company> company = Optional.ofNullable(companyService.getOneByName(attributes[1]));
                if (!company.isPresent()) {
                    newFiles.add(fileName);
                } else {
                    if (LocalDate.parse(attributes[0]).isAfter(companyService.getOneByName(attributes[1]).getLastDocumentDate())) {
                        newFiles.add(fileName);
                    }
                }
            }
        } else {
            System.out.println("Resource diretctory is empty!");
            System.exit(99);
        }
        return newFiles;
    }


}
