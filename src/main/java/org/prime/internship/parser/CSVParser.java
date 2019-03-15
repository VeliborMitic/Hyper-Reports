package org.prime.internship.parser;


import org.prime.internship.entity.City;
import org.prime.internship.entity.Employee;
import org.prime.internship.entity.Turnover;
import org.prime.internship.entity.dto.DailyReport;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class CSVParser{

    public void readWithCsvBeanReader() throws IOException {

        //TODO: Dynamicly passing file names to method
        try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader("reports/2018-10-01-blogtags.csv"), CsvPreference.STANDARD_PREFERENCE)) {

            // the header elements are used to map the values to the bean (names must match)
            final String[] header = beanReader.getHeader(true);
            final CellProcessor[] processors = getProcessors();

            DailyReport dailyReport;
            while ((dailyReport = beanReader.read(DailyReport.class, header, processors)) != null) {

                City city = new City();
                city.setName(dailyReport.getCity());
                Employee employee = new Employee();
                employee.setName(dailyReport.getEmployee());
                Turnover turnover =  new Turnover();
                turnover.setTurnover(dailyReport.getTurnover());


                //TODO: Take company name and date from file name and set attributes
                dailyReport.setDepartment("");
                dailyReport.setCompanyName("Prime");
                dailyReport.setDate(LocalDate.now());


                //TODO: Writing into the repository
                System.out.println(String.format("lineNo=%s, rowNo=%s, dailyReportDTO=%s", beanReader.getLineNumber(),
                        beanReader.getRowNumber(), dailyReport));
            }
        }

    }

    private static CellProcessor[] getProcessors() {
        return new CellProcessor[]{
                new NotNull(),
                new NotNull(),
                new NotNull(new ParseDouble())
        };
    }


}
