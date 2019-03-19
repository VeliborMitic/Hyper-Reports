package org.prime.internship.parser;

import org.prime.internship.entity.dto.DailyReportBean;

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
    private List<DailyReportBean> dailyReportBeanList;
    private String cityName = "";
    private String departmentName = "";
    private String employeeName = "";
    private String turnover = "";

    public XMLParser() {
        this.dailyReportBeanList = new ArrayList<>();
    }

    public List<DailyReportBean> readReportBeans(String fileName) throws FileNotFoundException, XMLStreamException {
        boolean bEmployee = false;
        boolean bTurnover = false;

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(fileName));

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();

            switch (event.getEventType()) {

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
                        this.dailyReportBeanList.add(new DailyReportBean(cityName, departmentName, employeeName, Double.parseDouble(turnover)));
                    }
                    break;

                default: break;
            }
        }
        return dailyReportBeanList;
    }
}


