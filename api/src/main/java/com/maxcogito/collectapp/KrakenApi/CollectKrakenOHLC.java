package com.maxcogito.collectapp.KrakenApi;

import com.maxcogito.collectapp.KrakenApi.input.Interval;
import com.maxcogito.collectapp.KrakenApi.result.OHLCResult;
import com.maxcogito.collectapp.KrakenApi.result.ServerTimeResult;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CollectKrakenOHLC {

    public static String TITLE = "Candles CSV Data";
    public static String COMMA_DELIMITER = ",";
    public static String NEWLINE = "\n";

    public static void CollectorOHLCData(KrakenAPIClient client,Interval val, String filePath, String URL, long since) throws IOException {
        String candleString = "CANDLES";
        String title_header = "date_time,time,low,high,open,close,volume,vwap,count,API_URL,Data_Collect_Interval";
        StringBuilder strbHdr = new StringBuilder();
        StringBuilder strbPath = new StringBuilder();

        ServerTimeResult serverTimeresult = null;
        try {
            serverTimeresult = client.getServerTime();
        } catch (KrakenApiException e) {
            e.printStackTrace();
        }
        ServerTimeResult.ServerTime serverTime = serverTimeresult.getResult();
        String serverTimeStr = String.format("Remote Server timestamp: %d => %s",
                serverTimeresult.getResult().unixtime,
                serverTimeresult.getResult().rfc1123);

        System.out.println(String.format("timestamp: %d => %s",
                serverTimeresult.getResult().unixtime,
                serverTimeresult.getResult().rfc1123));

        System.out.println("Server time must be: "+serverTime);
        System.out.println("Collection Interval: "+val.toString());

       // try {
          //  OHLCResult OHLCresult = client.getOHLC("XBTUSD", val,(int)since);
       // } catch (KrakenApiException e) {
         //   e.printStackTrace();
        //}

        Path dirPath = Paths.get(filePath);
        long timeNow = System.currentTimeMillis();
        Instant instant = Instant.ofEpochMilli(timeNow);
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        System.out.println("Initial path: "+dirPath.toString());
        System.out.println("Current UTC time must be: "+ldt.toLocalDate().toString().toString());
        Path interimPath = dirPath.resolve(ldt.toLocalDate().toString()).resolve(candleString).resolve(val.toString());
        System.out.println("Path with local Date Time: "+interimPath.toString());
        strbPath.append("CandlesData");
        strbPath.append("_");
        strbPath.append("rev1");
        strbPath.append(".csv");
        System.out.println("Filename for end of path: "+strbPath.toString());
        Path finalDirPath = interimPath.resolve(strbPath.toString());
        System.out.println("Final path with filename: "+finalDirPath.toString());
        System.out.println("Interval parameter value: "+val);

        try {
            Files.createDirectories(interimPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File csvFile = null;
        if(!Files.exists(finalDirPath)) {
            //Create new OHLC/Candles file in the directory
            //Add header with date created
            System.out.println("File : " + finalDirPath.toString() + " does not exist");
            System.out.println("Creating file: " + finalDirPath.toString());
            csvFile = new File(finalDirPath.toString());
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, true));
            strbHdr.append(TITLE);
            strbHdr.append(COMMA_DELIMITER);
            strbHdr.append(serverTimeStr);
            strbHdr.append(COMMA_DELIMITER);
            strbHdr.append("Current UTC TIME :");
            strbHdr.append(COMMA_DELIMITER);
            strbHdr.append(ldt.toString());
            strbHdr.append(NEWLINE);
            strbHdr.append(title_header);
            strbHdr.append(NEWLINE);
            bw.write(strbHdr.toString());  // add header to file
            bw.flush();
            bw.close();
        }

        //System.out.println(OHLCresult.getResult()); // OHLC data

        if(Files.exists(finalDirPath)) {
            try {
                OHLCResult OHLCresult = client.getOHLC("XBTUSD", val,(int)since);
                Map<String, List<OHLCResult.OHLC>> mres = OHLCresult.getResult();
                int i =0;

                BufferedWriter bw = new BufferedWriter(new FileWriter(String.valueOf(finalDirPath),true));

                for(Map.Entry<String,List<OHLCResult.OHLC>> entry : mres.entrySet()) {
                    System.out.println("OHLC List size: "+ entry.getValue().size());
                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().toString());
                    System.out.println("Key = " + entry.getKey() + ", Index = " + i + ", Value = " +entry.getValue().get(i));
//Get the list
                    List<OHLCResult.OHLC> OHLClist = entry.getValue();
                    System.out.println("OHLCresult.OHLC list size: "+OHLClist.size());
                    for (int j=0;j<OHLClist.size();j++){
                        StringBuilder strbData = new StringBuilder();
                        System.out.println("Data item:"+j+" time value: "+OHLClist.get(j).time);
                        long currentTime = OHLClist.get(j).time;
                        currentTime *=1000;
                        Instant instantVal = Instant.ofEpochMilli(currentTime);
                        LocalDateTime ldtObj = LocalDateTime.ofInstant(instantVal, ZoneOffset.UTC);
                        System.out.println("LocalDateTimeObj value: "+ldtObj);
                        strbData.append(ldtObj.toString());
                        strbData.append(COMMA_DELIMITER);
                        System.out.println("TIMESTAMP Value: "+currentTime);
                        System.out.println("Instant Value: "+instantVal.toString());
                        System.out.println("OHLC TIME Value: "+OHLClist.get(j).time);
                        strbData.append(OHLClist.get(j).time);
                        strbData.append(COMMA_DELIMITER);
                        System.out.println("Item: "+j+" for OHLClist low value: "+OHLClist.get(j).low);
                        strbData.append(OHLClist.get(j).low);
                        strbData.append(COMMA_DELIMITER);
                        System.out.println("Item: "+j+" for OHLClist high value: "+OHLClist.get(j).high);
                        strbData.append(OHLClist.get(j).high);
                        strbData.append(COMMA_DELIMITER);
                        System.out.println("Item: "+j+" for OHLClist open value: "+OHLClist.get(j).open);
                        strbData.append(OHLClist.get(j).open);
                        strbData.append(COMMA_DELIMITER);
                        System.out.println("Item: "+j+" for OHLClist close value: "+OHLClist.get(j).close);
                        strbData.append(OHLClist.get(j).close);
                        strbData.append(COMMA_DELIMITER);
                        System.out.println("Item: "+j+" for OHLClist olume value: "+OHLClist.get(j).volume);
                        strbData.append(OHLClist.get(j).volume);
                        strbData.append(COMMA_DELIMITER);
                        System.out.println("Item: "+j+" for OHLClist vwap value: "+OHLClist.get(j).vwap);
                        strbData.append(OHLClist.get(j).vwap);
                        strbData.append(COMMA_DELIMITER);
                        System.out.println("Item: "+j+" for OHLClist count value: "+OHLClist.get(j).count);
                        strbData.append(OHLClist.get(j).count);
                        strbData.append(COMMA_DELIMITER);
                        strbData.append(URL);
                        strbData.append(COMMA_DELIMITER);
                        strbData.append(val.toString());
                        strbData.append(NEWLINE);
                        System.out.println("Results of StringBuilder: "+strbData.toString());
                        bw.write(strbData.toString());
                    }
                    i++;
                }
                bw.close();
            } catch (KrakenApiException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String [] args){

        System.out.println( "Hello World!, let's get some OHLC prices from Kraken API." );
        String inputLine = null;
        String intervalVal = null;
        String sinceStr = null;
        String homeDir = System.getProperty("user.home");
        String APIURL = "https://api.kraken.com/0/public/OHLC";
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        KrakenAPIClient client = new KrakenAPIClient();

        System.out.println("Please enter the file pathname that you would like to use for the saving of the results in CSV file format:");
        System.out.println("Your home directory is: "+homeDir+" and your file will be saved in the directory you enter there.\n");
        inputLine = scanner.nextLine();
        StringBuilder strbPath = new StringBuilder();

        System.out.println("Please enter a time value (i.e.: 1580042780) for the start value of the collection, or hit Enter to accept default value of ZERO.");
        sinceStr = scanner.nextLine();
        long sinceVal = 0;
        Interval interval = Interval.ONE_WEEK;

        if(sinceStr.length()==0){
            System.out.println("User entered an empty string. Will start collection at since=0.");
            System.out.println("Will use an interval of ONE_WEEK");
        }
        else{
            System.out.println("User entered a string of length: "+sinceStr.length());
            sinceVal = Long.parseLong(sinceStr);
            System.out.println("The user entered a Long of value: "+sinceVal);
            if (sinceStr.equals("0")) {
                System.out.println("A value of this magnitude will reach as far back in the past as the API allows (API has an upper limit on values returned)");
                System.out.println("The date time value shown may seem odd as in Jan. 1, 1970");
            }
            interval = Interval.ONE_DAY;
        }


        //sinceVal = 1580042780L;
        long currentVal = sinceVal;//1580042780L;

        System.out.println("Starting API retrieve TIMESTAMP value: "+currentVal);

        currentVal *=1000;
        Instant instantVal = Instant.ofEpochMilli(currentVal);

        LocalDateTime localDateStart = LocalDateTime.ofInstant(instantVal, ZoneOffset.UTC);

        System.out.println("Starting LocalDateTime value: "+localDateStart.toString());
        System.out.println("\nUser has entered a starting time value of: "+sinceVal);
        System.out.println("This will have a starting LocalDateTime value of: "+localDateStart.toString());
        System.out.println("The current interval for collection will be: "+interval);

        System.out.println("Please enter a new interval value of 'DAY' or 'WEEK' or 'FIFTEEN' (FIFTEEN DAYS)");
        intervalVal = scanner.nextLine();
        intervalVal.toUpperCase();
        switch (intervalVal){
            case ("DAY"):
                interval = Interval.ONE_DAY;
                break;
            case("WEEK"):
                interval = Interval.ONE_WEEK;
                break;
            case("FIFTEEN"):
                interval = Interval.FIFTEEN_DAYS;
                break;
            case("MINUTE"):
                interval=Interval.ONE_MINUTE;
                break;
            case("FIVEMINUTE"):
                interval=Interval.FIVE_MINUTES;
                break;
            case("FIFTEENMINUTE"):
                interval=Interval.FIFTEEN_MINUTES;
                break;
            case("30MINUTE"):
                interval=Interval.THIRTY_MINUTES;
                break;
            case("HOUR"):
                interval=Interval.ONE_HOUR;
                break;
            case("FOURHOUR"):
                interval=Interval.FOUR_HOURS;
                break;
            default:
                System.out.println("Value not found, using default values.");
                break;
        }

        System.out.println("The current interval for collection will now be: "+interval);

        if(inputLine.contains(homeDir)){
            strbPath.append(inputLine);
            System.out.println("Will save output CSV file in: "+strbPath.toString());
        }
        else{
            System.out.println("Will add your directory:"+inputLine+" to the home path: "+homeDir);
            strbPath.append(homeDir);
            strbPath.append("/");
            strbPath.append(inputLine);
            System.out.println("Using this path string: "+strbPath.toString());
        }

        try {
            CollectorOHLCData(client,interval,strbPath.toString(),APIURL,sinceVal);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
