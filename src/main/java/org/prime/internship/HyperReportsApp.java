package org.prime.internship;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.prime.internship.cli.CommandHelp;
import org.prime.internship.cli.CommandLoad;
import org.prime.internship.cli.CommandReport;
import org.prime.internship.cli.CommandWrite;

import java.io.IOException;
import java.sql.SQLException;

public class HyperReportsApp {
    public static void main(String[] args) {

        JCommander jc = new JCommander();
        jc.setProgramName(HyperReportsApp.class.getSimpleName());

        CommandReport report = new CommandReport();
        CommandLoad load = new CommandLoad();
        CommandWrite write = new CommandWrite();
        CommandHelp help = new CommandHelp();

        jc.addCommand("report", report);
        jc.addCommand(write);
        jc.addCommand(load);
        jc.addCommand(help);

        //jc.parse(args);
        String[] simArgs = {"report", "-c", "agivu", "-y", "2018", "-m", "10", "-b", "10"};

        try {
            jc.parse(simArgs);

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
            System.out.println(e.getMessage());
            jc.usage();
            System.exit(1);
        }
    }
}


