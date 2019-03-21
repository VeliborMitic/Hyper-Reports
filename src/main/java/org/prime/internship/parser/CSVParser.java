package org.prime.internship.parser;

import org.prime.internship.entity.dto.DailyReport;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    private List<DailyReport> dailyReportBeansList;

    public CSVParser() {
        this.dailyReportBeansList = new ArrayList<>();
    }

    public List<DailyReport> readReportBeans(String fileName) throws IOException {
        DailyReport dailyReport;
        try (ICsvBeanReader beanReader = new CsvBeanReader(
                new FileReader(fileName), CsvPreference.STANDARD_PREFERENCE)) {

            final String[] header = beanReader.getHeader(true);
            final CellProcessor[] processors = getProcessors();

            while ((dailyReport = beanReader.read(DailyReport.class, header, processors)) != null) {
                dailyReport.setDepartment("");
                this.dailyReportBeansList.add(dailyReport);
            }
        }
        return this.dailyReportBeansList;
    }

    private static CellProcessor[] getProcessors() {
        return new CellProcessor[]{
                new NotNull(),
                new NotNull(),
                new NotNull(new ParseDouble())
        };
    }
}
