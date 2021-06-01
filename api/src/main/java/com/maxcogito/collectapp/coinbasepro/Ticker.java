package com.maxcogito.collectapp.coinbasepro;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        "trade_id",
        "price",
        "size",
        "time",
        "bid",
        "ask",
        "volume"
})
public class Ticker {

    @JsonProperty("trade_id")
    private Integer tradeId;
    @JsonProperty("price")
    private String price;
    @JsonProperty("size")
    private String size;
    @JsonProperty("time")
    private String time;
    @JsonProperty("bid")
    private String bid;
    @JsonProperty("ask")
    private String ask;
    @JsonProperty("volume")
    private String volume;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("trade_id")
    public Integer getTradeId() {
        return tradeId;
    }

    @JsonProperty("trade_id")
    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public Ticker withTradeId(Integer tradeId) {
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

    public Ticker withPrice(String price) {
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

    public Ticker withSize(String size) {
        this.size = size;
        return this;
    }

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    public Ticker withTime(String time) {
        this.time = time;
        return this;
    }

    @JsonProperty("bid")
    public String getBid() {
        return bid;
    }

    @JsonProperty("bid")
    public void setBid(String bid) {
        this.bid = bid;
    }

    public Ticker withBid(String bid) {
        this.bid = bid;
        return this;
    }

    @JsonProperty("ask")
    public String getAsk() {
        return ask;
    }

    @JsonProperty("ask")
    public void setAsk(String ask) {
        this.ask = ask;
    }

    public Ticker withAsk(String ask) {
        this.ask = ask;
        return this;
    }

    @JsonProperty("volume")
    public String getVolume() {
        return volume;
    }

    @JsonProperty("volume")
    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Ticker withVolume(String volume) {
        this.volume = volume;
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

    public Ticker withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Ticker.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("time");
        sb.append('=');
        sb.append(((this.time == null)?"<null>":this.time));
        sb.append(',');
        sb.append("bid");
        sb.append('=');
        sb.append(((this.bid == null)?"<null>":this.bid));
        sb.append(',');
        sb.append("ask");
        sb.append('=');
        sb.append(((this.ask == null)?"<null>":this.ask));
        sb.append(',');
        sb.append("volume");
        sb.append('=');
        sb.append(((this.volume == null)?"<null>":this.volume));
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
        result = ((result* 31)+((this.volume == null)? 0 :this.volume.hashCode()));
        result = ((result* 31)+((this.size == null)? 0 :this.size.hashCode()));
        result = ((result* 31)+((this.price == null)? 0 :this.price.hashCode()));
        result = ((result* 31)+((this.ask == null)? 0 :this.ask.hashCode()));
        result = ((result* 31)+((this.time == null)? 0 :this.time.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.bid == null)? 0 :this.bid.hashCode()));
        result = ((result* 31)+((this.tradeId == null)? 0 :this.tradeId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Ticker) == false) {
            return false;
        }
        Ticker rhs = ((Ticker) other);
        return (((((((((this.volume == rhs.volume)||((this.volume!= null)&&this.volume.equals(rhs.volume)))&&((this.size == rhs.size)||((this.size!= null)&&this.size.equals(rhs.size))))&&((this.price == rhs.price)||((this.price!= null)&&this.price.equals(rhs.price))))&&((this.ask == rhs.ask)||((this.ask!= null)&&this.ask.equals(rhs.ask))))&&((this.time == rhs.time)||((this.time!= null)&&this.time.equals(rhs.time))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.bid == rhs.bid)||((this.bid!= null)&&this.bid.equals(rhs.bid))))&&((this.tradeId == rhs.tradeId)||((this.tradeId!= null)&&this.tradeId.equals(rhs.tradeId))));
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

    public static void writeTickerObj(Ticker tickerObj, String fileName) throws IOException {

        long timeNow = 0L;
        int i = 0;

        File csvFile = null;
        StringBuilder strb = new StringBuilder();
        StringBuilder strbhdr = new StringBuilder();
        String title = "Ticker CSV Data ";
        String FILE_HEADER = "timestamp,date_time,trade_id,price,size,time,bid,ask,volume";
        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        String fileTitle = "TickerData.csv";
        String pathTicker = "TICKER";
        long timeval = 0;
        Long timeStamp = 0L;
        Date dateTrans = new Date();
        TimeManager tm = new TimeManager();

        timeNow = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(timeNow);
        String pathNameDate = date.toString();

        System.out.println("Date for Pathname: "+pathNameDate);

        Path filePath = Paths.get(fileName);
        Path filePathDate = filePath.resolve(pathNameDate).resolve(pathTicker);
        Path pathToFile = filePathDate.resolve(fileTitle);

        System.out.println("filePath after Paths.get: "+filePathDate.toString());
        System.out.println("pathToFile after Paths.get: "+pathToFile.toString());
        //Make sure directories exist to hold the CandlesDataFile should we need to create it
        Files.createDirectories(filePathDate);

        if(!Files.exists(pathToFile)){
            //Create new CandlesFile in the directory
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
            java.sql.Timestamp ts = tm.parseDateStrtoTsVal(tickerObj.getTime());

            strb.append(ts.getTime());
            strb.append(COMMA_DELIMITER);
            strb.append(tickerObj.getTime());
            strb.append(COMMA_DELIMITER);
            strb.append(tickerObj.getTradeId());
            strb.append(COMMA_DELIMITER);
            strb.append(tickerObj.getPrice());
            strb.append(COMMA_DELIMITER);
            strb.append(tickerObj.getSize());
            strb.append(COMMA_DELIMITER);
            strb.append(tickerObj.getTime());
            strb.append(COMMA_DELIMITER);
            strb.append(tickerObj.getBid());
            strb.append(COMMA_DELIMITER);
            strb.append(tickerObj.getAsk());
            strb.append(COMMA_DELIMITER);
            strb.append(tickerObj.getVolume());
            strb.append(NEW_LINE_SEPARATOR);
            System.out.println("String from TickerObject: "+strb.toString());
            bw.write(strb.toString());  // append data to file
            bw.close();
        }

    }

    public static void main(String[] args) throws IOException {

        Ticker tkData = new Ticker();
        ObjectMapper mapper = new ObjectMapper();
        String inputUrl = null;
        String jsonStr = null;
        String filePath = null;
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        ObjectMapper objectMapper = new ObjectMapper();

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
            tkData = objectMapper.readValue(new URL(inputUrl), Ticker.class);
        } catch (JsonProcessingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Json data from the object is: "+tkData.toString());
        System.out.println("\n********************************* +++++++++++++++++++++++++++ ************\n");
        System.out.println("PRICE   "+tkData.getPrice());
        System.out.println("ASK:  "+tkData.getAsk());
        System.out.println("BID:  "+tkData.getBid());
        System.out.println("Volume:  "+tkData.getVolume());
        System.out.println("Time:  "+tkData.getTime());
        System.out.println("Size:  "+tkData.getSize());
        System.out.println("Size:  "+tkData.getSize());
        System.out.println("Please enter a pathname to the file that will hold the CSV data: ");
        filePath = scanner.nextLine();
        System.out.println("File path to Ticker CSV file: "+filePath);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm[:ss][X][.SX][.SSX][.SSSX][.SSSSX][.SSSSSX][.SSSSSSX]");
        LocalDate parsedDate = LocalDate.parse(tkData.getTime(),formatter);
        LocalDateTime parsedDateTime = LocalDateTime.parse(tkData.getTime(),formatter);
        System.out.println("Parsed Date object: "+parsedDate.toString());
        System.out.println("Parsed DateTime object: "+parsedDateTime.toString());
        System.out.println("Parsed DateTime value: "+parsedDateTime.getSecond());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX");
        Date parsedDateobj = null;
        try {
            parsedDateobj = dateFormat.parse(tkData.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long timeValue = 1617057725072L;

        Instant instVal = Instant.ofEpochMilli(timeValue);
        System.out.println("*************\n");
        System.out.println("Instant Value from LONG TIMEVAL: "+instVal);
        System.out.println("Instant STRING Value from LONG TIMEVAL: "+instVal.toString());
        System.out.println("*************\n");

        System.out.println("Parsed Date Object: "+parsedDateobj.toString());
        System.out.println("Parsed Date Object to Instant: "+parsedDateobj.toInstant());
        System.out.println("ParsedDateTime Instant value: "+parsedDateTime.toInstant(ZoneOffset.UTC));

        Instant inst = parsedDateobj.toInstant();
        System.out.println("As Nanos :"+inst.getNano());
        System.out.println("As EPOCH seconds: "+inst.getEpochSecond());
        System.out.println("Instant string value: "+inst.toString());

        Instant instant = Instant.parse(tkData.getTime());
        System.out.println("From Instant API.parse: "+instant.toString());
        Date instDate = Date.from(instant);
        System.out.println("Printing Date object time value:"+instDate.getTime());
        Timestamp instantTimestamp = new Timestamp(instDate.getTime());
        System.out.println("Timestamp Value from Date derived from instant: "+instantTimestamp.getTime());

        Timestamp ts = new java.sql.Timestamp(parsedDateobj.getTime());
        System.out.println("Timestamp (DATE/TIME) is: "+ts.toString());
        System.out.println("Timestamp (value/getTime() Date Object) is: "+ts.getTime());


        System.out.println("****** Timestamp2 ******** gathered from lockDateTime object: ");
        Timestamp ts2 = Timestamp.valueOf(parsedDateTime);

        System.out.println("Timestamp2 is: "+ts2.getTime());
        System.out.println("Timestamp2 from toString: "+ts2.toString());

        Date dateTest = new Date(ts2.getTime());
        System.out.println("Date object values: "+dateTest.toString());

        System.out.println("****** Instant Value Date/Time ******** gathered from localDateTime object: ");

        Instant intValDateTime = parsedDateTime.toInstant(ZoneOffset.UTC);
        Timestamp tsFromInstant = Timestamp.from(intValDateTime);
        System.out.println("Instant Value from LocalDateTime object: "+intValDateTime);
        System.out.println("Instant Value from LocalDateTime object String: "+intValDateTime.toString());
        System.out.println("Instant Value from LocalDateTime object(EpochMillis): "+intValDateTime.toEpochMilli());
        System.out.println("As EPOCH seconds: "+intValDateTime.getEpochSecond());
        System.out.println("Timestamp Value from Instant Value Date Time: "+tsFromInstant.getTime());
        Instant i = parsedDateTime.atZone(ZoneOffset.ofHours(0)).toInstant();
        Timestamp tsLDT = Timestamp.from(i);
        System.out.println("*** Instant value from LDT object parseDateTime: "+tsLDT.getTime());

        Date dateTest2 = new Date(tsLDT.getTime());
        System.out.println("Date 2 Date object values from LDT (INSTANT): "+dateTest2.toString());


        System.out.println("***********************\n");
        System.out.println("parseDateTimeStr ***********************\n");
        TimeManager tm = new TimeManager();
        LocalDateTime ldt2 = tm.parseDateTimeStr(tkData.getTime());
        System.out.println("DateTime String data: "+ldt2.toString()+":  ******************\n");
        System.out.println("***********************\n");

        System.out.println("***********************\n");
        System.out.println("instantParseDate ***********************\n");
        Date dateObj = tm.instantParseDate(tkData.getTime());
        System.out.println("***********************\n");

        System.out.println("***********************\n");
        System.out.println("instantParseDateTime ***********************\n");
        Date dateObj2 = tm.instantParseDateTime(tkData.getTime());
        System.out.println("***********************\n");

        System.out.println("***********************\n");
        System.out.println("Date string from timeval: ***************\n");
        java.sql.Date dateObj3 = tm.instantParseDateTime(tkData.getTime());
        System.out.println("DataObj: "+dateObj3.toString()+":  ***********************\n");
        System.out.println("**************"+dateObj3.getTime()+"  ******\n");
        java.sql.Timestamp tsVal = tm.parseDateStrtoTsVal(tkData.getTime());
        Instant instantFromDate = tsVal.toInstant();
        System.out.println("**Timestamp timeVal: ******"+tsVal.getTime()+"  ******\n");
        System.out.println("**Timestamp time/date toString: ******"+tsVal.toString()+"  ******\n");
        System.out.println("**Timestamp time/date UTC string: ******"+tsVal+"  ******\n");
        System.out.println("** Instant time/date UTC string: ******"+instantFromDate+"  ******\n");
        System.out.println("** Instant time/date UTC VALUE : ******"+instantFromDate.toEpochMilli()+"  ******\n");
        System.out.println("***********************\n");

        writeTickerObj(tkData,filePath);


    }

}
