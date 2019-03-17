package org.prime.internship.parser;

import org.prime.internship.entity.dto.DailyReportBean;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public abstract class BaseParser {
    private String fileName;
    private String companyName;
    private LocalDate reportDate;

    protected BaseParser(String fileName, String companyName, LocalDate reportDate) {
        this.fileName = fileName;
        this.companyName = companyName;
        this.reportDate = reportDate;
    }

    abstract List<DailyReportBean> readReportBeans() throws IOException, XMLStreamException;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }
}
