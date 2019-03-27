package org.prime.internship.parser;

import org.prime.internship.entity.dto.ParsedDataDTO;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CSVParser {
    private Set<ParsedDataDTO> parsedDataDTOBeansList;

    public CSVParser() {
        this.parsedDataDTOBeansList = new HashSet<>();
    }

    public Set<ParsedDataDTO> readReportBeans(String fileName) throws IOException {
        ParsedDataDTO parsedDataDTO;
        try (ICsvBeanReader beanReader = new CsvBeanReader(
                new FileReader(fileName), CsvPreference.STANDARD_PREFERENCE)) {

            final String[] header = beanReader.getHeader(true);
            final CellProcessor[] processors = getProcessors();

            while ((parsedDataDTO = beanReader.read(ParsedDataDTO.class, header, processors)) != null) {
                parsedDataDTO.setDepartment("no department");
                this.parsedDataDTOBeansList.add(parsedDataDTO);
            }
        }
        return this.parsedDataDTOBeansList;
    }

    private static CellProcessor[] getProcessors() {
        return new CellProcessor[]{
                new NotNull(),
                new NotNull(),
                new NotNull(new ParseDouble())
        };
    }
}
