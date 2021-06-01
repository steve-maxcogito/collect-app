package com.maxcogito.collectapp.coinbasepro;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxcogito.collectapp.TimeManager;

import javax.net.ssl.HttpsURLConnection;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "time",
        "trade_id",
        "price",
        "size",
        "side"
})

public class Trade {

    @JsonProperty("time")
    private String time;
    @JsonProperty("trade_id")
    private Integer tradeId;
    @JsonProperty("price")
    private String price;
    @JsonProperty("size")
    private String size;
    @JsonProperty("side")
    private String side;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    public Trade withTime(String time) {
        this.time = time;
        return this;
    }

    @JsonProperty("trade_id")
    public Integer getTradeId() {
        return tradeId;
    }

    @JsonProperty("trade_id")
    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public Trade withTradeId(Integer tradeId) {
        this.tradeId = tradeId;
        return this;
    }

    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
    }

    public Trade withPrice(String price) {
        this.price = price;
        return this;
    }

    @JsonProperty("size")
    public String getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(String size) {
        this.size = size;
    }

    public Trade withSize(String size) {
        this.size = size;
        return this;
    }

    @JsonProperty("side")
    public String getSide() {
        return side;
    }

    @JsonProperty("side")
    public void setSide(String side) {
        this.side = side;
    }

    public Trade withSide(String side) {
        this.side = side;
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

    public Trade withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Trade.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("time");
        sb.append('=');
        sb.append(((this.time == null)?"<null>":this.time));
        sb.append(',');
        sb.append("tradeId");
        sb.append('=');
        sb.append(((this.tradeId == null)?"<null>":this.tradeId));
        sb.append(',');
        sb.append("price");
        sb.append('=');
        sb.append(((this.price == null)?"<null>":this.price));
        sb.append(',');
        sb.append("size");
        sb.append('=');
        sb.append(((this.size == null)?"<null>":this.size));
        sb.append(',');
        sb.append("side");
        sb.append('=');
        sb.append(((this.side == null)?"<null>":this.side));
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
        result = ((result* 31)+((this.side == null)? 0 :this.side.hashCode()));
        result = ((result* 31)+((this.size == null)? 0 :this.size.hashCode()));
        result = ((result* 31)+((this.price == null)? 0 :this.price.hashCode()));
        result = ((result* 31)+((this.time == null)? 0 :this.time.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.tradeId == null)? 0 :this.tradeId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Trade) == false) {
            return false;
        }
        Trade rhs = ((Trade) other);
        return (((((((this.side == rhs.side)||((this.side!= null)&&this.side.equals(rhs.side)))&&((this.size == rhs.size)||((this.size!= null)&&this.size.equals(rhs.size))))&&((this.price == rhs.price)||((this.price!= null)&&this.price.equals(rhs.price))))&&((this.time == rhs.time)||((this.time!= null)&&this.time.equals(rhs.time))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.tradeId == rhs.tradeId)||((this.tradeId!= null)&&this.tradeId.equals(rhs.tradeId))));
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

    public static void writeTradeObj(Trade tradeObj, String fileName) throws IOException {
        long timeNow = 0L;
        int i = 0;

        File csvFile = null;
        StringBuilder strb = new StringBuilder();
        StringBuilder strbhdr = new StringBuilder();
        String title = "Trade CSV Data ";
        String FILE_HEADER = "timestamp,date_time,trade_id,price,size,side";
        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        String fileTitle = "TradeData.csv";
        String pathTrades = "TRADES";
        long timeval = 0;
        Long timeStamp = 0L;
        Date dateTrans = new Date();
        TimeManager tm = new TimeManager();

        timeNow = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(timeNow);
        String pathNameDate = date.toString();

        System.out.println("Date for Pathname: "+pathNameDate);

        Path filePath = Paths.get(fileName);
        Path filePathDate = filePath.resolve(pathNameDate).resolve(pathTrades);
        Path pathToFile = filePathDate.resolve(fileTitle);

        System.out.println("filePath after Paths.get: "+filePathDate.toString());
        System.out.println("pathToFile after Paths.get: "+pathToFile.toString());
        //Make sure directories exist to hold the CandlesDataFile should we need to create it
        Files.createDirectories(filePathDate);

        if(!Files.exists(pathToFile)){
            //Create new TradesFile in the directory
            System.out.println("File : "+pathToFile.toString()+" does not exist");
            System.out.println("Creating file: "+pathToFile.toString());
            csvFile = new File(pathToFile.toString());
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile,true));
            strbhdr.append(title);
            strbhdr.append(NEW_LINE_SEPARATOR);
            strbhdr.append(FILE_HEADER);
            strbhdr.append(NEW_LINE_SEPARATOR);
            bw.write(strbhdr.toString());  // append data to file
            bw.flush();
            bw.close();
        }

        if(Files.exists(pathToFile)){ //Paranoid check to make sure file still exists
            //Go ahead and iterate through the candle objects in the list
            //Append them to the file
            //Buffered Writer is ready for us if the file still exists
            BufferedWriter bw = new BufferedWriter(new FileWriter(String.valueOf(pathToFile),true));
            System.out.println("File : "+pathToFile.toString()+" still exists, write Ticker data to it.");
            java.sql.Timestamp ts = tm.parseDateStrtoTsVal(tradeObj.getTime());

            strb.append(ts.getTime());
            strb.append(COMMA_DELIMITER);
            strb.append(tradeObj.getTime());
            strb.append(COMMA_DELIMITER);
            strb.append(tradeObj.getTradeId());
            strb.append(COMMA_DELIMITER);
            strb.append(tradeObj.getPrice());
            strb.append(COMMA_DELIMITER);
            strb.append(tradeObj.getSize());
            strb.append(COMMA_DELIMITER);
            strb.append(tradeObj.getSide());
            strb.append(NEW_LINE_SEPARATOR);
            System.out.println("String from TradeObject: "+strb.toString());
            bw.write(strb.toString());  // append data to file
            bw.close();
        }

    }

    public static void  main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Trade> listTrades = new ArrayList<Trade>();

        String inputUrl = null;
        String jsonStr = null;
        String dirPath = null;
        Scanner scanner = new Scanner(new InputStreamReader(System.in));

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


        try {
            listTrades = objectMapper.readValue(new URL(inputUrl), new TypeReference<List<Trade>>(){});
        } catch (JsonProcessingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TimeManager tm = new TimeManager();

        //System.out.println("DID IT WORK?  "+listTrades.get(0).toString());

        System.out.println("\n********************************* +++++++++++++++++++++++++++ ************\n");
        System.out.println("Please enter a pathname for the directory to hold the trade data: ");
        dirPath = scanner.nextLine();
        System.out.println("Directory to build for TRADE data: "+dirPath);

        int i = 0;

        for (i=0;i< listTrades.size();i++){
            System.out.println("*********  Trade #: "+i+" *************");
            java.sql.Timestamp timestamp = tm.parseDateStrtoTsVal(listTrades.get(i).getTime());
            System.out.println("Timestamp from DateStr: "+timestamp.getTime());
            System.out.println("Time: "+listTrades.get(i).getTime());
            System.out.println("Trade_ID: "+listTrades.get(i).getTradeId());
            System.out.println("Price: "+listTrades.get(i).getPrice());
            System.out.println("Size: "+listTrades.get(i).getSize());
            System.out.println("Side: "+listTrades.get(i).getSide());
            System.out.println("Timestamp as String from DateStr: "+timestamp.toString());
            System.out.println("Value of timestamp (with timestamp.getTime(): "+timestamp.getTime());
            writeTradeObj(listTrades.get(i),dirPath);
        }
        System.out.println("How many?  "+listTrades.size());
        long testTS = 1617132648763L;
        Instant testInstant = Instant.ofEpochMilli(testTS);
        java.sql.Timestamp ts = Timestamp.from(testInstant);
        LocalDateTime lDT = LocalDateTime.ofInstant(testInstant, ZoneOffset.UTC);
        System.out.println("Instant value: "+testInstant.toEpochMilli()+" instant time:  "+testInstant+" TS: "+ts+" LocalDateTime: "+lDT.toString());
    }

}
