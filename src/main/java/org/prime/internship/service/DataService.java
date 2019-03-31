package org.prime.internship.service;

import org.prime.internship.entity.Company;
import org.prime.internship.entity.dto.ParsedDataDTO;
import org.prime.internship.parser.CSVParser;
import org.prime.internship.parser.XMLParser;
import org.prime.internship.utility.ReportFileUtils;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class DataService {
    private final CompanyService companyService;
    private final DepartmentService departmentService;
    private final CityService cityService;
    private final EmployeeService employeeService;
    private final TurnoverService turnoverService;
    private Set<ParsedDataDTO> parsedDataDTOList;
    private Set<String> allFiles;
    private final Set<String> newFiles;

    public DataService() {
        this.companyService = new CompanyService();
        this.departmentService = new DepartmentService();
        this.cityService = new CityService();
        this.employeeService = new EmployeeService();
        this.turnoverService = new TurnoverService();
        this.allFiles = new TreeSet<>();
        this.newFiles = new TreeSet<>();
    }

    public void writeFilesFromResourceToDB() throws IOException, XMLStreamException {
        Set<String> newFilesSet = listNewFilesInDirectory();

        if (!newFilesSet.isEmpty()) {
            for (String fileName : newFilesSet) {
                String[] attributes = ReportFileUtils.parseFileName(fileName);

                if (attributes[2].equalsIgnoreCase("csv")) {
                    parsedDataDTOList = new CSVParser().readReportBeans(ReportFileUtils.DATA_INPUT_PATH + fileName);
                    processFile(attributes);
                } else if (attributes[2].equalsIgnoreCase("xml")) {
                    parsedDataDTOList = new XMLParser().readReportBeans(ReportFileUtils.DATA_INPUT_PATH + fileName);
                    processFile(attributes);
                }
                System.out.println("File \"" + fileName + "\" processed!");
            }
        } else {
            System.out.println("All files in source directory already processed!");
        }
    }

    private void processFile(String[] attributes) {
        parsedDataDTOList.forEach(bean -> turnoverService.processTurnoverToDB(
                employeeService.processEmployeeToDB(
                        bean.getEmployee(),
                        companyService.processCompanyToDB(attributes[1], attributes[0]).getCompanyId(),
                        cityService.processCityToDB(bean.getCity()).getCityId(),
                        departmentService.processDepartmentToDB(bean.getDepartment()).getDepartmentId()).getEmployeeId(),
                attributes[0],
                bean.getTurnover()
        ));
    }

    private Set<String> listNewFilesInDirectory() {
        allFiles = ReportFileUtils.findFilesInLocalDir();

        if (!allFiles.isEmpty()) {
            for (String fileName : allFiles) {
                String[] attributes = ReportFileUtils.parseFileName(fileName);

                Optional<Company> company = Optional.ofNullable(companyService.getOneByName(attributes[1]));
                if (!company.isPresent()) {
                    newFiles.add(fileName);
                } else {
                    if (LocalDate.parse(attributes[0]).isAfter(companyService.getOneByName(attributes[1]).getLastDocumentDate()) &&
                            !LocalDate.parse(attributes[0]).isEqual(companyService.getOneByName(attributes[1]).getLastDocumentDate())) {
                        newFiles.add(fileName);
                    }
                }
            }
        } else {
            System.out.println("Resource directory is empty!");
            System.exit(99);
        }
        return newFiles;
    }


}
