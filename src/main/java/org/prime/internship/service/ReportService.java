package org.prime.internship.service;

import org.prime.internship.entity.dto.DailyReportBean;
import org.prime.internship.parser.CSVParser;
import org.prime.internship.parser.XMLParser;
import org.prime.internship.repository.CityRepository;
import org.prime.internship.repository.CompanyRepository;
import org.prime.internship.repository.DepartmentRepository;
import org.prime.internship.repository.EmployeeRepository;
import org.prime.internship.repository.TurnoverRepository;
import org.prime.internship.utility.Util;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService {

    private final String PATH = "";

    private CityRepository cityRepository;
    private CompanyRepository companyRepository;
    private DepartmentRepository departmentRepository;
    private EmployeeRepository employeeRepository;
    private TurnoverRepository turnoverRepository;

    public ReportService(){
        cityRepository = new CityRepository();
        companyRepository = new CompanyRepository();
        departmentRepository = new DepartmentRepository();
        employeeRepository = new EmployeeRepository();
        turnoverRepository = new TurnoverRepository();
    }

    public static void harvestDailyReportsFromResource() throws IOException, XMLStreamException {
        List<String> fileNames = Util.listAllFilesInDirectory();

        for (String file : fileNames) {
            String[] attributes = Util.parseFileName(file);

            if (attributes[2].equalsIgnoreCase("csv")) {
                List<DailyReportBean> list = new CSVParser().readReportBeans("reports/" + file);
                for(DailyReportBean bean : list){

                }

            } else if (attributes[2].equalsIgnoreCase("xml")) {
                List<DailyReportBean> list = new XMLParser().readReportBeans("reports/" + file);
                for(DailyReportBean bean : list){

                }
            }
        }

    }





}
