package org.prime.internship.utility;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Util {

    public static void printList (Iterable<?> list){
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static List<String> listAllFilesInDirectory () {
        File[] files = new File("reports/").listFiles();

        return Arrays.asList(files).parallelStream().map(file ->
            file.getName()).collect(Collectors.toList());
    }

    public static String[] parseFileName (String fileName){
        String[] strings = new String[3];
        String regex = ".*(\\d{4}-\\d{2}-\\d{2})-(.*)\\.(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(fileName);

        if (m.find()) {
            strings[0] = m.group(1);
            strings[1] = m.group(2);
            strings[2] = m.group(3);
        }
        return strings;
    }

    private static void downloadFolder(
        FTPClient ftpClient, String remotePath, String localPath) throws IOException {
        System.out.println("Downloading folder " + remotePath + " to " + localPath);

        FTPFile[] remoteFiles = ftpClient.listFiles(remotePath);

        for (FTPFile remoteFile : remoteFiles)
        {
            if (!remoteFile.getName().equals(".") && !remoteFile.getName().equals(".."))
            {
                String remoteFilePath = remotePath + "/" + remoteFile.getName();
                String localFilePath = localPath + "/" + remoteFile.getName();

                if (remoteFile.isDirectory())
                {
                    new File(localFilePath).mkdirs();

                    downloadFolder(ftpClient, remoteFilePath, localFilePath);
                }
                else
                {
                    System.out.println("Downloading file " + remoteFilePath + " to " +
                            localFilePath);

                    OutputStream outputStream =
                            new BufferedOutputStream(new FileOutputStream(localFilePath));
                    if (!ftpClient.retrieveFile(remoteFilePath, outputStream)){
                        System.out.println("Failed to download file " + remoteFilePath);
                    }
                    outputStream.close();
                }
            }
        }
    }


}
