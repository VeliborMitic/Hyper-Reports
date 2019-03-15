package org.prime.internship.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class URLReader {

    URL oracle = new URL("http://136ea.k.time4vps.cloud/data/");
    BufferedReader in = new BufferedReader(
            new InputStreamReader(oracle.openStream()));

    String inputLine;

    public URLReader() throws IOException {

        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
}
