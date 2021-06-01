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
        "USD",
        "JPY",
        "EUR"
})
public class Pricemulti {
    @JsonProperty("USD")
    private Double usd;
    @JsonProperty("JPY")
    private Double jpy;
    @JsonProperty("EUR")
    private Double eur;
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

    public Pricemulti withUsd(Double usd) {
        this.usd = usd;
        return this;
    }

    @JsonProperty("JPY")
    public Double getJpy() {
        return jpy;
    }

    @JsonProperty("JPY")
    public void setJpy(Double jpy) {
        this.jpy = jpy;
    }

    public Pricemulti withJpy(Double jpy) {
        this.jpy = jpy;
        return this;
    }

    @JsonProperty("EUR")
    public Double getEur() {
        return eur;
    }

    @JsonProperty("EUR")
    public void setEur(Double eur) {
        this.eur = eur;
    }

    public Pricemulti withEur(Double eur) {
        this.eur = eur;
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

    public Pricemulti withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Pricemulti.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("usd");
        sb.append('=');
        sb.append(((this.usd == null)?"<null>":this.usd));
        sb.append(',');
        sb.append("jpy");
        sb.append('=');
        sb.append(((this.jpy == null)?"<null>":this.jpy));
        sb.append(',');
        sb.append("eur");
        sb.append('=');
        sb.append(((this.eur == null)?"<null>":this.eur));
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
        result = ((result* 31)+((this.jpy == null)? 0 :this.jpy.hashCode()));
        result = ((result* 31)+((this.eur == null)? 0 :this.eur.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.usd == null)? 0 :this.usd.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Pricemulti) == false) {
            return false;
        }
        Pricemulti rhs = ((Pricemulti) other);
        return (((((this.jpy == rhs.jpy)||((this.jpy!= null)&&this.jpy.equals(rhs.jpy)))&&((this.eur == rhs.eur)||((this.eur!= null)&&this.eur.equals(rhs.eur))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.usd == rhs.usd)||((this.usd!= null)&&this.usd.equals(rhs.usd))));
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

    public static void writePriceMultiCurrObj(Pricemulti priceObj, String fileName, String APIUrl, String CryptoSym) throws IOException {
        long timeNow = 0L;
        int i = 0;

        File csvFile = null;
        StringBuilder strbAttr = new StringBuilder();
        StringBuilder strbhdr = new StringBuilder();
        String title = "Single Symbol Multi-Currency Price CSV Data ";
        String DATE_COLLECTED = "Date Collected";
        String DATE_FILE_CREATED = "Date Created";
        String INSTANCE_DATA_HDR = "Generating URL :";
        String FILE_DATA_HEADER = "Time,Date_Time,Crypto Symbol, Currency Symbol, Price,Currency Symbol, Price, Currency Symbol Price";//Assume USD,EUR,JPY
        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        String fileTitle = "PriceMultiCurrencyData.csv";
        String pathPriceSingle = "PRICEMULTICURRENCY";
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
            strbhdr.append(COMMA_DELIMITER);
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
            strbAttr.append("USD");
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(priceObj.getUsd());
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append("EUR");
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(priceObj.getEur());
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append("JPY");
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(priceObj.getJpy());
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
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        ObjectMapper objectMapper = new ObjectMapper();
        Pricemulti pMultiUrl = new Pricemulti();

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
            pMultiUrl = objectMapper.readValue(new URL(inputUrl),Pricemulti.class);
        } catch (JsonProcessingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n********************************* +++++++++++++++++++++++++++ ************\n");
        System.out.println("Bitcoin price USD: "+pMultiUrl.getUsd());
        System.out.println("BTC price per Eur: "+pMultiUrl.getEur());
        System.out.println("BTC price per JPY: "+pMultiUrl.getJpy());
        System.out.println("Dollar value direct from URL should be: "+pMultiUrl.toString());

        System.out.println("Please enter a file pathname to hold CSV file: ");
        String inputLine = scanner.nextLine();
        System.out.println("Path to file entered for CSV output: "+inputLine);
        System.out.println("Calling API at the entered URL.");

        try {
            writePriceMultiCurrObj(pMultiUrl,inputLine,inputUrl,"BTC");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
