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
import com.maxcogito.collectapp.coinbasepro.Ticker;
import com.maxcogito.collectapp.TimeManager;

import javax.net.ssl.HttpsURLConnection;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Response",
        "Message",
        "HasWarning",
        "Type",
        "RateLimit",
        "Data"
})

public class Cryptocompareohlcv2 {
    @JsonProperty("Response")
    private String response;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("HasWarning")
    private Boolean hasWarning;
    @JsonProperty("Type")
    private Integer type;
    @JsonProperty("RateLimit")
    private RateLimit rateLimit;
    @JsonProperty("Data")
    private Data data;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Response")
    public String getResponse() {
        return response;
    }

    @JsonProperty("Response")
    public void setResponse(String response) {
        this.response = response;
    }

    public Cryptocompareohlcv2 withResponse(String response) {
        this.response = response;
        return this;
    }

    @JsonProperty("Message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("Message")
    public void setMessage(String message) {
        this.message = message;
    }

    public Cryptocompareohlcv2 withMessage(String message) {
        this.message = message;
        return this;
    }

    @JsonProperty("HasWarning")
    public Boolean getHasWarning() {
        return hasWarning;
    }

    @JsonProperty("HasWarning")
    public void setHasWarning(Boolean hasWarning) {
        this.hasWarning = hasWarning;
    }

    public Cryptocompareohlcv2 withHasWarning(Boolean hasWarning) {
        this.hasWarning = hasWarning;
        return this;
    }

    @JsonProperty("Type")
    public Integer getType() {
        return type;
    }

    @JsonProperty("Type")
    public void setType(Integer type) {
        this.type = type;
    }

    public Cryptocompareohlcv2 withType(Integer type) {
        this.type = type;
        return this;
    }

    @JsonProperty("RateLimit")
    public RateLimit getRateLimit() {
        return rateLimit;
    }

    @JsonProperty("RateLimit")
    public void setRateLimit(RateLimit rateLimit) {
        this.rateLimit = rateLimit;
    }

    public Cryptocompareohlcv2 withRateLimit(RateLimit rateLimit) {
        this.rateLimit = rateLimit;
        return this;
    }

    @JsonProperty("Data")
    public Data getData() {
        return data;
    }

    @JsonProperty("Data")
    public void setData(Data data) {
        this.data = data;
    }

    public Cryptocompareohlcv2 withData(Data data) {
        this.data = data;
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

    public Cryptocompareohlcv2 withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Cryptocompareohlcv2 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("response");
        sb.append('=');
        sb.append(((this.response == null)?"<null>":this.response));
        sb.append(',');
        sb.append("message");
        sb.append('=');
        sb.append(((this.message == null)?"<null>":this.message));
        sb.append(',');
        sb.append("hasWarning");
        sb.append('=');
        sb.append(((this.hasWarning == null)?"<null>":this.hasWarning));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("rateLimit");
        sb.append('=');
        sb.append(((this.rateLimit == null)?"<null>":this.rateLimit));
        sb.append(',');
        sb.append("data");
        sb.append('=');
        sb.append(((this.data == null)?"<null>":this.data));
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
        result = ((result* 31)+((this.rateLimit == null)? 0 :this.rateLimit.hashCode()));
        result = ((result* 31)+((this.data == null)? 0 :this.data.hashCode()));
        result = ((result* 31)+((this.response == null)? 0 :this.response.hashCode()));
        result = ((result* 31)+((this.hasWarning == null)? 0 :this.hasWarning.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.message == null)? 0 :this.message.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Cryptocompareohlcv2) == false) {
            return false;
        }
        Cryptocompareohlcv2 rhs = ((Cryptocompareohlcv2) other);
        return ((((((((this.rateLimit == rhs.rateLimit)||((this.rateLimit!= null)&&this.rateLimit.equals(rhs.rateLimit)))&&((this.data == rhs.data)||((this.data!= null)&&this.data.equals(rhs.data))))&&((this.response == rhs.response)||((this.response!= null)&&this.response.equals(rhs.response))))&&((this.hasWarning == rhs.hasWarning)||((this.hasWarning!= null)&&this.hasWarning.equals(rhs.hasWarning))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.message == rhs.message)||((this.message!= null)&&this.message.equals(rhs.message))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))));
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
    public static void writeOHLCv2Obj(Cryptocompareohlcv2 ccOHLCv2Obj, String fileName, String APIUrl) throws IOException {

        long timeNow = 0L;
        int i = 0;

        File csvFile = null;
        StringBuilder strbAttr = new StringBuilder();
        StringBuilder strbhdr = new StringBuilder();
        String title = "OHLCv2 CSV Data ";
        String DATE_COLLECTED = "Date Collected";
        String DATE_FILE_CREATED = "Date Created";
        String INSTANCE_DATA_HDR = "Aggregated,TimeFrom,Date_Time_From,TimeTo,Date_Time_To";
        String GENERATING_URL = "Generating URL:";
        String FILE_DATA_HEADER = "Time, Date_Time, High, Low, Open, VolumeFrom, VolumeTo, Close, ConversionType, conversionSymbol";
        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        String fileTitle = "OHLCv2Data.csv";
        String pathTicker = "OHLCv2";
        long timeval = 0;
        Long timeStamp = 0L;
        Date dateTrans = new Date();
        TimeManager tm = new TimeManager();

        timeNow = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(timeNow);
        String pathNameDate = date.toString();
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeNow), ZoneOffset.UTC);

        System.out.println("Date for Pathname: "+pathNameDate);
        System.out.println("LocalDateTime for Timestamp string in CSV: "+ldt.toString());

        Path filePath = Paths.get(fileName);
        Path filePathDate = filePath.resolve(pathNameDate).resolve(pathTicker);
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
            bw.write(strbhdr.toString());  // add header to file
            bw.flush();
            bw.close();
        }

        if(Files.exists(pathToFile)){
            BufferedWriter bw = new BufferedWriter(new FileWriter(String.valueOf(pathToFile),true));
            System.out.println("File : "+pathToFile.toString()+" still exists, write OHLCv2 data to it.");
            strbAttr.append(NEW_LINE_SEPARATOR);
            strbAttr.append("Response :");
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(ccOHLCv2Obj.getResponse());
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append("Message :");
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(ccOHLCv2Obj.getMessage());
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append("HasWarning :");
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(ccOHLCv2Obj.getHasWarning());
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append("Type :");
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(ccOHLCv2Obj.getType());
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append("RateLimit :");
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(ccOHLCv2Obj.getRateLimit().getAdditionalProperties().toString());
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(NEW_LINE_SEPARATOR);
            strbAttr.append(NEW_LINE_SEPARATOR);
            strbAttr.append("Data Attributes: ");
            strbAttr.append(NEW_LINE_SEPARATOR);
            strbAttr.append(INSTANCE_DATA_HDR);
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(GENERATING_URL);
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(APIUrl);
            strbAttr.append(NEW_LINE_SEPARATOR);
            strbAttr.append(ccOHLCv2Obj.getData().getAggregated());
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(ccOHLCv2Obj.getData().getTimeFrom());
            strbAttr.append(COMMA_DELIMITER);
            long timeFrom = ccOHLCv2Obj.getData().getTimeFrom();
            timeFrom *= 1000;
            System.out.println("TimeFrom Milliseconds: "+timeFrom);
            LocalDateTime ldtFrom = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeFrom), ZoneOffset.UTC);
            System.out.println("Local Data/TimeFrom Milliseconds: "+ldtFrom.toString());
            strbAttr.append(ldtFrom.toString());
            strbAttr.append(COMMA_DELIMITER);
            strbAttr.append(ccOHLCv2Obj.getData().getTimeTo());
            strbAttr.append(COMMA_DELIMITER);
            //strbAttr.append(NEW_LINE_SEPARATOR);
            System.out.println("String so far: "+strbAttr.toString());
            long timeTo = ccOHLCv2Obj.getData().getTimeTo();
            timeTo *= 1000;
            System.out.println("TimeFrom Milliseconds: "+timeTo);
            LocalDateTime ldtTo = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeTo),ZoneOffset.UTC);
            strbAttr.append(ldtTo.toString());
            System.out.println("Local Data/TimeFrom Milliseconds: "+ldtTo.toString());
            strbAttr.append(NEW_LINE_SEPARATOR);
            strbAttr.append(NEW_LINE_SEPARATOR);
            strbAttr.append("Data Values:");
            strbAttr.append(NEW_LINE_SEPARATOR);
            strbAttr.append(FILE_DATA_HEADER);
            strbAttr.append(NEW_LINE_SEPARATOR);
            i = ccOHLCv2Obj.getData().getData().size();
            System.out.println("Size of Datum list: "+i);


            System.out.println("String so far: "+strbAttr.toString());
            bw.write(strbAttr.toString());  // append data to file
            i = 0;
            for(Datum dataItem: ccOHLCv2Obj.getData().getData()){
                StringBuilder strDatum = new StringBuilder();
                long timeDatum = dataItem.getTime();
                timeDatum *= 1000;
                System.out.println("TimeFrom Milliseconds: "+timeDatum);
                LocalDateTime ldtDatum = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeDatum),ZoneOffset.UTC);
                strDatum.append(dataItem.getTime());
                strDatum.append(COMMA_DELIMITER);
                strDatum.append(ldtDatum.toString());
                strDatum.append(COMMA_DELIMITER);
                strDatum.append(dataItem.getHigh());
                strDatum.append(COMMA_DELIMITER);
                strDatum.append(dataItem.getLow());
                strDatum.append(COMMA_DELIMITER);
                strDatum.append(dataItem.getOpen());
                strDatum.append(COMMA_DELIMITER);
                strDatum.append(dataItem.getVolumefrom());
                strDatum.append(COMMA_DELIMITER);
                strDatum.append(dataItem.getVolumeto());
                strDatum.append(COMMA_DELIMITER);
                strDatum.append(dataItem.getClose());
                strDatum.append(COMMA_DELIMITER);
                strDatum.append(dataItem.getConversionType());
                strDatum.append(COMMA_DELIMITER);
                strDatum.append(dataItem.getConversionSymbol());
                strDatum.append(NEW_LINE_SEPARATOR);
                System.out.println("Results of Datum #"+i+":"+strDatum.toString());
                bw.write(strDatum.toString());
                i++;
            }
            bw.close();

        }
        }


    public static void main(String [] args)
    {
        System.out.println("Hello from the MAIN of Cryptocompareohlcv2.");
        Scanner scanner = new Scanner(System.in);
        String inputLine = new String();
        String jsonStr = null;

        ObjectMapper objectMapper = new ObjectMapper();
        Cryptocompareohlcv2 cmpOhlcv2 = new Cryptocompareohlcv2();

        System.out.println("Please enter an API/URL to view : ");
        inputLine = scanner.nextLine();
        System.out.println("Collecting API/URL: "+inputLine);

        try {
            jsonStr=callAPI(inputLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("API results (string form): "+jsonStr);
        System.out.println("\n********************************* +++++++++++++++++++++++++++ ************\n");
        String APIUrl = new String(inputLine);
        System.out.println("String for API URL: "+APIUrl);

        try {
            cmpOhlcv2 = objectMapper.readValue(new URL(inputLine),Cryptocompareohlcv2.class);
        } catch (JsonProcessingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n********************************* +++++++++++++++++++++++++++ ************\n");
        System.out.println("Dollar value direct from URL should be: "+cmpOhlcv2.toString());

        System.out.println("Please enter a path to the output directory for CSV file: ");
        inputLine = scanner.nextLine();
        System.out.println("Collecting CSV data into: "+inputLine);

        System.out.println("\n********************************* +++++++++++++++++++++++++++ ************\n");

        try {
            writeOHLCv2Obj(cmpOhlcv2,inputLine,APIUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
