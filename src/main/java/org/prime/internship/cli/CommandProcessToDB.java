package org.prime.internship.cli;

import com.beust.jcommander.Parameters;
import org.prime.internship.service.DataService;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

@Parameters(commandNames = "write" ,commandDescription = "Process newly added report files from local directory to database")
public class CommandProcessToDB {
    private DataService dataService;

    public CommandProcessToDB(){
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
