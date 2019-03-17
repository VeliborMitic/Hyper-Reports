package org.prime.internship.parser;

import org.prime.internship.entity.dto.DailyReportBean;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CSVParser extends BaseParser {
    private DailyReportBean dailyReportBean;
    private List<DailyReportBean> dailyReportBeansList;

    public CSVParser(String fileName, String companyName, LocalDate reportDate) {
        super(fileName, companyName, reportDate);
        this.dailyReportBean = new DailyReportBean();
        this.dailyReportBeansList = new ArrayList<>();
    }

    public List<DailyReportBean> readReportBeans() throws IOException {
        try (ICsvBeanReader beanReader = new CsvBeanReader(
                new FileReader(this.getFileName()), CsvPreference.STANDARD_PREFERENCE)) {

            final String[] header = beanReader.getHeader(true);
            final CellProcessor[] processors = getProcessors();

            while ((dailyReportBean = beanReader.read(DailyReportBean.class, header, processors)) != null) {
                dailyReportBean.setCompanyName(this.getCompanyName());
                dailyReportBean.setDate(this.getReportDate());
                dailyReportBean.setDepartment(null);

                this.dailyReportBeansList.add(dailyReportBean);
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
