package com.maxcogito.collectapp.KrakenApi;

import com.maxcogito.collectapp.KrakenApi.input.Interval;
import com.maxcogito.collectapp.KrakenApi.result.OrderBookResult;
import com.maxcogito.collectapp.KrakenApi.result.RecentTradeResult;
import com.maxcogito.collectapp.KrakenApi.result.ServerTimeResult;
import com.maxcogito.collectapp.DecimalUtils;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CollectOrderBook {
    public static String TITLE = "Order Book CSV Data";
    public static String COMMA_DELIMITER = ",";
    public static String NEWLINE = "\n";
    public static String API_URL = "https://api.kraken.com/0/public/Depth";
    public static String intervalStr = "ONE_WEEK";

    CollectOrderBook(String title,String comma_delimiter,String newline,String Url,String interval){
        TITLE = title;
        COMMA_DELIMITER = comma_delimiter;
        NEWLINE = newline;
        API_URL = Url;
        intervalStr = interval;
    }

    public static void CollectorOrderBookData(KrakenAPIClient client, Interval val, String filePath, String URL, String Currency, long since) throws IOException, KrakenApiException {

        String OrderBook = "OrderBook";
        String title_header = "Bid/Ask, date_time,time_value,crypto/FIAT,price, volume,Bid/Ask,date_time,time_value,crypto/FIAT,price,volume,API_URL,Data_Collect_Interval";
        StringBuilder strbHdr = new StringBuilder();
        StringBuilder strTitlebHdr = new StringBuilder();
        StringBuilder strbPath = new StringBuilder();
        Path initialPath = null;
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

        System.out.println("Server time must be: "+serverTime);

        Path dirPath = Paths.get(filePath);
        long timeNow = System.currentTimeMillis();
        Instant instant = Instant.ofEpochMilli(timeNow);
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        System.out.println("Initial path: "+dirPath.toString());
        System.out.println("Current UTC time must be: "+ldt.toLocalDate().toString().toString());
        Path interimPath = dirPath.resolve(ldt.toLocalDate().toString()).resolve(OrderBook).resolve(val.toString());
        System.out.println("Path with local Date Time: "+interimPath.toString());
        strbPath.append("OrderBook");
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
            strbHdr.append(NEWLINE);
            strbHdr.append(title_header);
            strbHdr.append(NEWLINE);
            bw.write(strbHdr.toString());  // add header to file
            bw.flush();
            bw.close();
        }

        if(Files.exists(finalPath)) {


            OrderBookResult orderBookResult = client.getOrderBook(Currency);
            int q = 0;

            BufferedWriter bw = new BufferedWriter(new FileWriter(String.valueOf(finalPath),true));

            orderBookResult.getResult().forEach((currency,orders) -> {
                int p = 0;
                System.out.println("Size of ASKS values: " +orders.asks.size());
                System.out.println("Size of BIDS values: " +orders.bids.size());
                System.out.println(currency +" asks:" + orders.asks);
                System.out.println(currency + " bids: " + orders.bids);

                int askSize = orders.asks.size();
                int bidSize = orders.bids.size();
                int maxSize = 0;

                if(askSize>=bidSize){
                    maxSize = askSize;
                    for(int i = 0; i<maxSize;i++){
                        StringBuilder strbData = new StringBuilder();
                        if(i<bidSize){
                            strbData.append("Bid");
                            strbData.append(COMMA_DELIMITER);
                            BigDecimal timeVal = BigDecimal.valueOf(orders.bids.get(i).timestamp);
                            Instant instantVal = DecimalUtils.toInstant(timeVal);
                            strbData.append(instantVal);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(timeVal);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(currency);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(orders.bids.get(i).price);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(orders.bids.get(i).volume);
                            strbData.append(COMMA_DELIMITER);
                            //Add ASK data at this point
                            strbData.append("Ask");
                            strbData.append(COMMA_DELIMITER);
                            BigDecimal timeValAsk = BigDecimal.valueOf(orders.asks.get(i).timestamp);
                            Instant instantValAsk = DecimalUtils.toInstant(timeVal);
                            strbData.append(instantValAsk);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(timeValAsk);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(currency);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(orders.asks.get(i).price);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(orders.asks.get(i).volume);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(URL);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(val.toString());
                            strbData.append(NEWLINE);
                            try {
                                bw.write(strbData.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                bw.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            //Don't write bids after this point; write NULL bid information
                            strbData.append("Bid");
                            strbData.append(COMMA_DELIMITER);
                            strbData.append("No Data");
                            strbData.append(COMMA_DELIMITER);
                            strbData.append("No Data");
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(currency);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append("No Data");
                            strbData.append(COMMA_DELIMITER);
                            strbData.append("No Data");
                            strbData.append(COMMA_DELIMITER);
                            //Add ASK data at this point
                            strbData.append("Ask");
                            strbData.append(COMMA_DELIMITER);
                            BigDecimal timeValAsk = BigDecimal.valueOf(orders.asks.get(i).timestamp);
                            Instant instantValAsk = DecimalUtils.toInstant(timeValAsk);
                            strbData.append(instantValAsk);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(timeValAsk);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(currency);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(orders.asks.get(i).price);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(orders.asks.get(i).volume);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(URL);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(val.toString());
                            strbData.append(NEWLINE);
                            try {
                                bw.write(strbData.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                bw.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("Result of stringBuilder: "+strbData.toString());
                    }
                }
                else{
                    maxSize = bidSize;

                    for(int i = 0; i<maxSize;i++){
                        StringBuilder strbData = new StringBuilder();
                        if(i<askSize){
                            strbData.append("Bid");
                            strbData.append(COMMA_DELIMITER);
                            BigDecimal timeVal = BigDecimal.valueOf(orders.bids.get(i).timestamp);
                            Instant instantVal = DecimalUtils.toInstant(timeVal);
                            strbData.append(instantVal);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(timeVal);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(currency);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(orders.bids.get(i).price);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(orders.bids.get(i).volume);
                            strbData.append(COMMA_DELIMITER);
                            //Add ASK data at this point
                            strbData.append("Ask");
                            strbData.append(COMMA_DELIMITER);
                            BigDecimal timeValAsk = BigDecimal.valueOf(orders.asks.get(i).timestamp);
                            Instant instantValAsk = DecimalUtils.toInstant(timeVal);
                            strbData.append(instantValAsk);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(timeValAsk);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(currency);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(orders.asks.get(i).price);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(orders.asks.get(i).volume);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(URL);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(val.toString());
                            strbData.append(NEWLINE);
                            try {
                                bw.write(strbData.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                bw.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            //Don't write asks after this point; write NULL Ask information
                            strbData.append("Bid");
                            strbData.append(COMMA_DELIMITER);
                            BigDecimal timeValBid = BigDecimal.valueOf(orders.bids.get(i).timestamp);
                            Instant instantValBid = DecimalUtils.toInstant(timeValBid);
                            strbData.append(instantValBid);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(timeValBid);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(currency);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(orders.bids.get(i).price);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(orders.bids.get(i).volume);
                            strbData.append(COMMA_DELIMITER);

                            //Add ASK data at this point
                            strbData.append("Ask");
                            strbData.append(COMMA_DELIMITER);
                            strbData.append("No Data");
                            strbData.append(COMMA_DELIMITER);
                            strbData.append("No Data");
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(currency);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append("No Data");
                            strbData.append(COMMA_DELIMITER);
                            strbData.append("No Data");
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(URL);
                            strbData.append(COMMA_DELIMITER);
                            strbData.append(val.toString());
                            strbData.append(NEWLINE);
                            try {
                                bw.write(strbData.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                bw.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("Result of stringBuilder: "+strbData.toString());
                    }

                }
            });
            bw.flush();
            bw.close();

        }

    }

    public static void main(String[] args){

        System.out.println( "Hello World!, let's get some Recent trade prices from Kraken API." );
        String inputLine = null;
        String intervalVal = null;
        String sinceStr = null;
        String homeDir = System.getProperty("user.home");
        String APIURL = "https://api.kraken.com/0/public/OrderBook";
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
        strbAPI.append(APIURL);
        strbAPI.append("?");
        strbAPI.append("pair=");
        strbAPI.append(currency);

        try {
            CollectorOrderBookData(client,interval,strbPath.toString(),strbAPI.toString(),currency,sinceVal);
        } catch (IOException | KrakenApiException e) {
            e.printStackTrace();
        }

    }

}
