package org.prime.internship.cli;

import com.beust.jcommander.Parameters;
import org.prime.internship.service.DataService;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

@Parameters(commandNames = "write" ,commandDescription = "Writes newly added reports to database")
public class CommandWrite {
    private DataService dataService;

    public CommandWrite(){
        this.dataService = new DataService();
    }

    public  void run() {
        try {
            dataService.writeFilesFromResourceToDB();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

}
