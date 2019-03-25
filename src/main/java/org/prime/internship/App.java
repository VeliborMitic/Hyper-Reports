package org.prime.internship;

import org.prime.internship.service.DataService;
import org.prime.internship.utility.Util;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class App {
    public static void main(String[] args) {

        DataService service = new DataService();
        try {
            Util.downloadNewUrlFiles();
            service.writeFilesFromResourceToDB();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }


    }


}

