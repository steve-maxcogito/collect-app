package com.maxcogito.collectapp.cryptocompare;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxcogito.collectapp.TimeManager;

import javax.net.ssl.HttpsURLConnection;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "USD"
})
public class Pricesingle {

    @JsonProperty("USD")
    private Double usd;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("USD")
    public Double getUsd() {
        return usd;
    }

    @JsonProperty("USD")
    public void setUsd(Double usd) {
        this.usd = usd;
    }

    public Pricesingle withUsd(Double usd) {
        this.usd = usd;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Pricesingle withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Pricesingle.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("usd");
        sb.append('=');
        sb.append(((this.usd == null)?"<null>":this.usd));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.usd == null)? 0 :this.usd.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Pricesingle) == false) {
            return false;
        }
        Pricesingle rhs = ((Pricesingle) other);
        return (((this.usd == rhs.usd)||((this.usd!= null)&&this.usd.equals(rhs.usd)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }
    public static String callAPI(String API) throws IOException {
        String str = new String();
        String httpsURL = API;
        String inputLine;
        URL myUrl = new URL(httpsURL);
        HttpsURLConnection conn = (HttpsURLConnection)myUrl.openConnection();
        InputStream is = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        try {

            while ((inputLine = br.readLine()) != null) {
                System.out.println(inputLine);
                str+= inputLine;
            }

            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Returning API JSON: "+str);
        return(str);
    }

    public static void writePriceSingleObj(Pricesingle priceObj, String fileName, String APIUrl,String CryptoSym,String CurrSym) throws IOException {
        long timeNow = 0L;
        int i = 0;

        File csvFile = null;
        StringBuilder strbAttr = new StringBuilder();
        StringBuilder strbhdr = new StringBuilder();
        String title = "Single Symbol Price CSV Data ";
        String DATE_COLLECTED = "Date Collected";
        String DATE_FILE_CREATED = "Date Created";
        String INSTANCE_DATA_HDR = "Generating URL :";
        String FILE_DATA_HEADER = "Time,Date_Time,Crypto Symbol, Currency Symbol, Price";
        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        String fileTitle = "PriceSingleCurrencyData.csv";
        String pathPriceSingle = "PRICESINGLECURRENCY";
        long timeval = 0;
        Long timeStamp = 0L;
        Date dateTrans = new Date();
        TimeManager tm = new TimeManager();

        timeNow = System.currentTimeMillis();
        Instant instant = Instant.ofEpochMilli(timeNow);
        java.sql.Timestamp sqlTs = Timestamp.from(instant);
        java.sql.Date date = new java.sql.Date(timeNow);
        String pathNameDate = date.toString();
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeNow), ZoneOffset.UTC);

        System.out.println("Date for Pathname: "+pathNameDate);
        System.out.println("LocalDateTime for Timestamp string in CSV: "+ldt.toString());

        Path filePath = Paths.get(fileName);
        Path filePathDate = filePath.resolve(pathNameDate).resolve(pathPriceSingle);
        Path pathToFile = filePathDate.resolve(fileTitle);

        System.out.println("filePath after Paths.get: "+filePathDate.toString());
        System.out.println("pathToFile after Paths.get: "+pathToFile.toString());
        //Make sure directories exist to hold the CandlesDataFile should we need to create it
        Files.createDirectories(filePathDate);

        if(!Files.exists(pathToFile)){
            //Create new OHLC/Candles file in the directory
            //Add header with date created
            System.out.println("File : "+pathToFile.toString()+" does not exist");
            System.out.println("Creating file: "+pathToFile.toString());
            csvFile = new File(pathToFile.toString());
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile,true));
            strbhdr.append(title);
            strbhdr.append(COMMA_DELIMITER);
            strbhdr.append(DATE_FILE_CREATED);
            strbhdr.append(":");
            strbhdr.append(COMMA_DELIMITER);
            strbhdr.append(ldt.toString());
            strbhdr.append(NEW_LINE_SEPARATOR);
            strbhdr.append(NEW_LINE_SEPARATOR);
            strbhdr.append(FILE_DATA_HEADER);
            strbhdr.append(NEW_LINE_SEPARATOR);
            bw.write(strbhdr.toString());  // add header to file
            bw.flush();
            bw.close();
        }

        if(Files.exists(pathToFile)) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(String.valueOf(pathToFile), true));
            System.out.println("File : " + pathToFile.toString() + " still exists, write Price Data to it.\n");
            System.out.println("Instant time string : " + instant + " value.\n");
            System.out.println("Instant epoch seconds value: "+instant.toEpochMilli()+"  as Milli-seconds.");
            System.out.println("SqlTs : " + sqlTs.getTime() + " Timestamp derived fron Instant value.\n");
            System.out.println("SqlTs : " + sqlTs + " Timestamp derived fron Instant value.\n");
            System.out.println("LocalDateTime for Timestamp string in CSV: "+ldt.toString());
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
            System.out.println("Converting Timestamp to Local Date Time object:"+localDateTime.atZone(ZoneId.of("UTC")));
            strbAttr.append(sqlTs.getTime());
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(localDateTime.atZone(ZoneId.of("UTC")));
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(CryptoSym);
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(CurrSym);
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(priceObj.getUsd());
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(INSTANCE_DATA_HDR);
            strbAttr.append(COMMA_DELIMITER);
            String strChk = new String();
            System.out.println("StrChk: "+strChk);
            strChk = APIUrl.replace(',','-');
            strbAttr.append(strChk);
            System.out.println("Fixed APIUrl: "+strChk);
            System.out.println("Fixed up String in StringBuilder: "+strbAttr.toString());
            strbAttr.append(NEW_LINE_SEPARATOR);
            bw.write(strbAttr.toString());  // append data to file
            bw.close();
        }


    }

    public static void main(String [] args){

        String inputUrl = null;
        String jsonStr = null;
        String inputLine = null;
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        ObjectMapper objectMapper = new ObjectMapper();
        Pricesingle pSingleUrl = new Pricesingle();

        System.out.println("Please enter an API url to test: ");
        inputUrl = scanner.nextLine();
        System.out.println("URL to test: "+inputUrl);
        System.out.println("Calling API at the entered URL.");

        try {
            jsonStr=callAPI(inputUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Json string to map to an object is: "+jsonStr);

        System.out.println("\n********************************* +++++++++++++++++++++++++++ ************\n");

        try {
            pSingleUrl = objectMapper.readValue(new URL(inputUrl),Pricesingle.class);
        } catch (JsonProcessingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n********************************* +++++++++++++++++++++++++++ ************\n");
        System.out.println("Bitcoin price USD: "+pSingleUrl.getUsd());

        System.out.println("Dollar value direct from URL should be: "+pSingleUrl.toString());

        System.out.println("Please enter a path to the output directory for CSV file: ");
        inputLine = scanner.nextLine();
        System.out.println("Collecting CSV data into: "+inputLine);

        System.out.println("\n********************************* +++++++++++++++++++++++++++ ************\n");

        try {
            writePriceSingleObj(pSingleUrl,inputLine,inputUrl,"BTC","USD");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
