package org.prime.internship.parser;

import com.sun.xml.internal.bind.v2.TODO;
import org.prime.internship.entity.dto.DailyReport;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
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

    public List<DailyReport> readReportBeans(String fileName) throws FileNotFoundException, XMLStreamException {
        boolean bEmployee = false;
        boolean bTurnover = false;

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(fileName));

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();

            switch (event.getEventType()) {

                //TODO: Refactor parser:
                // parseStartElement(XMLEvent event), parseCharacterElement(XMLEvent event) and parseEndElement(XMLEvent event)
                case XMLStreamConstants.START_ELEMENT:
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
                    break;

                case XMLStreamConstants.CHARACTERS:
                    Characters characters = event.asCharacters();
                    if (bEmployee) {
                        employeeName = characters.getData();
                        bEmployee = false;
                    }
                    if (bTurnover) {
                        turnover = characters.getData();
                        bTurnover = false;
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equalsIgnoreCase("department")) {
                        this.dailyReportList.add(new DailyReport(cityName, departmentName, employeeName, Double.parseDouble(turnover)));
                    }
                    break;
            }
        }
        return dailyReportList;
    }
}


