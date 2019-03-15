package org.prime.internship.parser;

import org.prime.internship.entity.dto.DailyReport;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

public class XMLParser {

    private String filename = "reports/2018-10-01-agivu.xml";


    public void readXML() throws FileNotFoundException, XMLStreamException{

        boolean bCityName = false;
        boolean bDepartment = false;
        boolean bEmployee = false;
        boolean bTurnover = false;


        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader =
                factory.createXMLEventReader(new FileReader(filename));

        while(eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();

            switch(event.getEventType()) {

                case XMLStreamConstants.START_ELEMENT:
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();

                    if (qName.equalsIgnoreCase("city") || qName.equalsIgnoreCase("department")) {
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        String match = attributes.next().getValue();
                        if (qName.equalsIgnoreCase("City")){
                            System.out.println("City : " + match);}
                        else {
                            System.out.println("Department : " + match);
                        }
                    } else if (qName.equalsIgnoreCase("city name")) {
                        bCityName = true;
                    } else if (qName.equalsIgnoreCase("department name")) {
                        bDepartment = true;
                    } else if (qName.equalsIgnoreCase("employee")) {
                        bEmployee = true;
                    }
                    else if (qName.equalsIgnoreCase("turnover")) {
                        bTurnover = true;
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    Characters characters = event.asCharacters();
                    if(bCityName) {
                        System.out.println("City Name: " + characters.getData());
                        bCityName = false;
                    }
                    if(bDepartment) {
                        System.out.println("Department: " + characters.getData());
                        bDepartment = false;
                    }
                    if(bEmployee) {
                        System.out.println("Employee: " + characters.getData());
                        bEmployee = false;
                    }
                    if(bTurnover) {
                        System.out.println("Turnover: " + characters.getData());
                        bTurnover = false;
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    EndElement endElement = event.asEndElement();

                    if(endElement.getName().getLocalPart().equalsIgnoreCase("city")
                            || endElement.getName().getLocalPart().equalsIgnoreCase("department")) {
                        System.out.println();
                    }
                    break;
            }
        }


    }

}
