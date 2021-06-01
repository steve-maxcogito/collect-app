package com.maxcogito.collectapp.KrakenApi;

import com.maxcogito.collectapp.KrakenApi.input.Interval;
import com.maxcogito.collectapp.KrakenApi.result.ServerTimeResult;
import com.maxcogito.collectapp.KrakenApi.result.TickerInformationResult;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class CollectTickerInfo {

    public static String TITLE = "TickerInfo CSV Data";
    public static String COMMA_DELIMITER = ",";
    public static String NEWLINE = "\n";
    public static String API_URL = "https://api.kraken.com/0/public/Ticker";

    CollectTickerInfo(String title,String comma_delimiter,String newline,String Url){
        TITLE = title;
        COMMA_DELIMITER = comma_delimiter;
        NEWLINE = newline;
        API_URL = Url;
    }

    public static void CollectorRecentTradesData(KrakenAPIClient client, String filePath, String URL, String Currency, long since) throws IOException, KrakenApiException {

        String tickerInfo = "Ticker Information";
        String tickerStr = "Ticker";
        String startTimeDate = "Server Start time: ";
        String headerTitle = "Currency/Pair,date/time, timestamp, ask price,ask wholeLotVolume,ask lotVolume,bid price,bid wholeLotVolume,bid lotVolume,lastTradeclosed price,lastTradeclosed lotVolume,volume today,volume last24hours,volumeWeightedAverage today,volumeWeightedAverage last24hours,numberOfTrades today,numberOfTrades last24hours,low today,low last24hours,high today, high last24hours,todayOpenPrice";
        StringBuilder strbServerTime = new StringBuilder();
        StringBuilder strbHdr = new StringBuilder();
        StringBuilder strbData = new StringBuilder();
        Path dirPath = Paths.get(filePath);
        Path finalPath = null;

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

        strbServerTime.append(startTimeDate);
        strbServerTime.append(String.format("timestamp: %d => %s",
                serverTimeresult.getResult().unixtime,
                serverTimeresult.getResult().rfc1123));

        System.out.println("Start Time of file from server perspective: "+strbServerTime.toString());

        long timeNow = System.currentTimeMillis();
        Instant instant = Instant.ofEpochMilli(timeNow);
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        System.out.println("Initial path: "+dirPath.toString());
        System.out.println("Current UTC time must be: "+ldt.toLocalDate().toString().toString());
        Path interimPath = dirPath.resolve(ldt.toLocalDate().toString()).resolve(tickerStr);
        System.out.println("Path with local Date Time: "+interimPath.toString());

        StringBuilder strbPath = new StringBuilder();

        strbPath.append("TickerInfo");
        strbPath.append("_");
        strbPath.append("rev1");
        strbPath.append(".csv");
        finalPath = interimPath.resolve(strbPath.toString());
        System.out.println("Interim path without filename: "+interimPath.toString());
        System.out.println("Final path with filename: "+finalPath.toString());

        try {
            Files.createDirectories(interimPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File csvFile = null;
        if(!Files.exists(finalPath)) {
            //Create new OHLC/Candles file in the directory
            //Add header with date created
            System.out.println("File : " + finalPath.toString() + " does not exist");
            System.out.println("Creating file: " + finalPath.toString());
            csvFile = new File(finalPath.toString());
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, true));
            strbHdr.append(TITLE);
            strbHdr.append(COMMA_DELIMITER);
            strbHdr.append(serverTimeStr);
            strbHdr.append(COMMA_DELIMITER);
            strbHdr.append("Current UTC TIME :");
            strbHdr.append(COMMA_DELIMITER);
            strbHdr.append(ldt.toString());
            strbHdr.append(COMMA_DELIMITER);
            strbHdr.append(URL);
            strbHdr.append(COMMA_DELIMITER);
            strbHdr.append("Currency = "+Currency);
            strbHdr.append(NEWLINE);
            strbHdr.append(headerTitle);
            strbHdr.append(NEWLINE);
            bw.write(strbHdr.toString());  // add header to file
            bw.flush();
            bw.close();
        }

        if(Files.exists(finalPath)) {
            TickerInformationResult tickResult = client.getTickerInformation(Arrays.asList(Currency));

            Map<String, TickerInformationResult.TickerInformation> mrecentTickres = tickResult.getResult();

            BufferedWriter bw = new BufferedWriter(new FileWriter(String.valueOf(finalPath),true));
            int k =0;

            for(Map.Entry<String, TickerInformationResult.TickerInformation> mapentry:mrecentTickres.entrySet()){
                strbData.append(Currency);
                strbData.append(COMMA_DELIMITER);
                strbData.append(instant);
                strbData.append(COMMA_DELIMITER);
                strbData.append(instant.toEpochMilli());
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().ask.price);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().ask.wholeLotVolume);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().ask.lotVolume);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().bid.price);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().bid.wholeLotVolume);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().bid.lotVolume);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().lastTradeClosed.price);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().lastTradeClosed.lotVolume);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().volume.today);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().volume.last24hours);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().volumeWeightAverage.today);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().volumeWeightAverage.last24hours);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().numberOfTrades.today);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().numberOfTrades.last24hours);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().low.today);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().low.last24hours);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().high.today);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().high.last24hours);
                strbData.append(COMMA_DELIMITER);
                strbData.append(mapentry.getValue().todayOpenPrice);
                strbData.append(NEWLINE);
                bw.write(strbData.toString());
                bw.flush();
                k++;
            }

            bw.flush();
            bw.close();

            // TickerInformationResult tickresult = client.getTickerInformation(Arrays.asList("BTCEUR","ETHEUR","XBTUSD"));
            System.out.println("Ticker result size: "+tickResult.getResult().size());


        }

    }

    public static void main(String[] args){
        String inputLine = null;
        String API_URL = CollectTickerInfo.API_URL;
        String homeDir = System.getProperty("user.home");
        Scanner scanner = new Scanner(new InputStreamReader(System.in));

        System.out.println("Welcome to the Ticker collector.");

        KrakenAPIClient client = new KrakenAPIClient();

        System.out.println("Your home directory is: "+homeDir+" and your file will be saved in the directory you enter there.\n");

        System.out.println("Please enter the directory where you would like to add the ticker specific information.");
        System.out.println("This will be the file pathname that you would like to use for the saving of the results in CSV file format:");
        System.out.println("This utility will enter build a path such as /what_you_enter/2021-mm-dd/Ticker/");
        inputLine = scanner.nextLine();
        System.out.println("User entered: "+inputLine+"  for a directory location.");

        StringBuilder strbPath = new StringBuilder();

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

        String defaultcurrency = "XBTUSD";
        System.out.println("Current cryptocurrency/FIAT pair: "+defaultcurrency);
        System.out.println("Please enter another if you prefer another crypto or FIAT currency pair.");
        System.out.println("Or hit return if the default is acceptable.");
        String currency = null;
        currency = scanner.nextLine();
        if(currency.length()<defaultcurrency.length()){
            currency = defaultcurrency;
            System.out.println("Using default currency/FIAT pair of:"+currency);
        }
        else{
            System.out.println("Using value entered by user of: "+currency);
        }

        StringBuilder strbAPI = new StringBuilder();
        strbAPI.append(API_URL);
        strbAPI.append("?");
        strbAPI.append("pair=");
        strbAPI.append(currency);
        System.out.println("API Path to be used is: "+strbAPI.toString());

        try {
            CollectorRecentTradesData(client,strbPath.toString(),strbAPI.toString(),currency,0L);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KrakenApiException e) {
            e.printStackTrace();
        }
    }

}
