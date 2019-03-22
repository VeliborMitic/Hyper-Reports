package org.prime.internship.parser;

import org.prime.internship.entity.dto.DailyReport;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XMLParser {
    private List<DailyReport> dailyReportList;
    private String cityName = "";
    private String departmentName = "";
    private String employeeName = "";
    private String turnover = "";

    public XMLParser() {
        this.dailyReportList = new ArrayList<>();
    }

    private boolean bEmployee = false;
    private boolean bTurnover = false;

    public List<DailyReport> readReportBeans(String fileName) throws FileNotFoundException, XMLStreamException {

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(fileName));
        XMLEvent event;

        while (eventReader.hasNext()) {
            event = eventReader.nextEvent();

            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    parseStartElement(event);
                    break;

                case XMLStreamConstants.CHARACTERS:
                    parseCharacterElement(event);
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    parseEndElement(event);
                    break;
            }
        }
        return dailyReportList;
    }

    private void parseStartElement(XMLEvent event) {
        StartElement startElement = event.asStartElement();
        String qName = startElement.getName().getLocalPart();

        if (qName.equalsIgnoreCase("city") ||
                qName.equalsIgnoreCase("department")) {
            Iterator<Attribute> attributes = startElement.getAttributes();
            String startElementValue = attributes.next().getValue();
            if (qName.equalsIgnoreCase("City")) {
                cityName = startElementValue;
            } else {
                departmentName = startElementValue;
            }
        } else if (qName.equalsIgnoreCase("employee")) {
            bEmployee = true;
        } else if (qName.equalsIgnoreCase("turnover")) {
            bTurnover = true;
        }
    }

    private void parseCharacterElement(XMLEvent event) {
        Characters characters = event.asCharacters();
        if (bEmployee) {
            employeeName = characters.getData();
            bEmployee = false;
        }
        if (bTurnover) {
            turnover = characters.getData();
            bTurnover = false;
        }
    }

    private void parseEndElement(XMLEvent event) {
        EndElement endElement = event.asEndElement();
        if (endElement.getName().getLocalPart().equalsIgnoreCase("department")) {
            this.dailyReportList.add(new DailyReport(cityName, departmentName, employeeName, Double.parseDouble(turnover)));
        }
    }
}


