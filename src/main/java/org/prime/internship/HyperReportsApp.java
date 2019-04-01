package org.prime.internship;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.prime.internship.cli.CommandHelp;
import org.prime.internship.cli.CommandLoad;
import org.prime.internship.cli.CommandReport;
import org.prime.internship.cli.CommandProcessToDB;

import java.io.IOException;
import java.sql.SQLException;

public class HyperReportsApp {
    public static void main(String[] args) {

        JCommander jc = new JCommander();
        jc.setProgramName(HyperReportsApp.class.getSimpleName());

        CommandReport report = new CommandReport();
        CommandLoad load = new CommandLoad();
        CommandProcessToDB write = new CommandProcessToDB();
        CommandHelp help = new CommandHelp();

        jc.addCommand("report", report);
        jc.addCommand(write);
        jc.addCommand(load);
        jc.addCommand(help);

        try {
            jc.parse(args);

            if (jc.getParsedCommand() == null) {
                jc.usage();
            } else {
                switch (jc.getParsedCommand()) {
                    case "report":
                        report.run();
                        break;
                    case "write":
                        write.run();
                        break;
                    case "load":
                        load.run();
                        break;
                    case "help":
                        jc.usage();
                        break;
                    default:
                        jc.usage();
                        break;
                }
            }
        } catch (ParameterException | SQLException | IOException | ClassNotFoundException e) {
            jc.usage();
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}


