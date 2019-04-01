package org.prime.internship.cli;

import com.beust.jcommander.Parameters;
import org.prime.internship.utility.ReportFileUtils;

import java.io.IOException;

@Parameters(commandNames = {"load"}, commandDescription = "Load new report files from remote directory")
public class CommandLoad {

    public  void run() {
        try {
            ReportFileUtils.downloadNewUrlFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}