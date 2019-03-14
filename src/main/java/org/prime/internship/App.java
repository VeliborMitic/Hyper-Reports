package org.prime.internship;

// http://136ea.k.time4vps.cloud/data/

import org.prime.internship.parser.CSVParser;


public class App
{
    public static void main( String[] args ){

        CSVParser parser = new CSVParser();
        try {
            parser.readWithCsvBeanReader();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
