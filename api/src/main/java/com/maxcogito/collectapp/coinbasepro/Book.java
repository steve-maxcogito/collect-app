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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxcogito.collectapp.TimeManager;

import javax.net.ssl.HttpsURLConnection;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "bids",
        "asks",
        "sequence"
})
public class Book {
    @JsonProperty("bids")
    private List<List<String>> bids = new ArrayList<List<String>>();
    @JsonProperty("asks")
    private List<List<String>> asks = new ArrayList<List<String>>();
    @JsonProperty("sequence")
    private Long sequence;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("bids")
    public List<List<String>> getBids() {
        return bids;
    }

    @JsonProperty("bids")
    public void setBids(List<List<String>> bids) {
        this.bids = bids;
    }

    public Book withBids(List<List<String>> bids) {
        this.bids = bids;
        return this;
    }

    @JsonProperty("asks")
    public List<List<String>> getAsks() {
        return asks;
    }

    @JsonProperty("asks")
    public void setAsks(List<List<String>> asks) {
        this.asks = asks;
    }

    public Book withAsks(List<List<String>> asks) {
        this.asks = asks;
        return this;
    }

    @JsonProperty("sequence")
    public Long getSequence() {
        return sequence;
    }

    @JsonProperty("sequence")
    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Book withSequence(Long sequence) {
        this.sequence = sequence;
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

    public Book withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Book.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("bids");
        sb.append('=');
        sb.append(((this.bids == null)?"<null>":this.bids));
        sb.append(',');
        sb.append("asks");
        sb.append('=');
        sb.append(((this.asks == null)?"<null>":this.asks));
        sb.append(',');
        sb.append("sequence");
        sb.append('=');
        sb.append(((this.sequence == null)?"<null>":this.sequence));
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
        result = ((result* 31)+((this.bids == null)? 0 :this.bids.hashCode()));
        result = ((result* 31)+((this.sequence == null)? 0 :this.sequence.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.asks == null)? 0 :this.asks.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Book) == false) {
            return false;
        }
        Book rhs = ((Book) other);
        return (((((this.bids == rhs.bids)||((this.bids!= null)&&this.bids.equals(rhs.bids)))&&((this.sequence == rhs.sequence)||((this.sequence!= null)&&this.sequence.equals(rhs.sequence))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.asks == rhs.asks)||((this.asks!= null)&&this.asks.equals(rhs.asks))));
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

    public static String getString(List<String> strList){
        StringBuilder strb = new StringBuilder();
        int numItem = strList.size();
        int i = 0;
        String COMMA_DELIMITER = ",";

        for(i=0;i<numItem;i++){
            strb.append(strList.get(i));
            if(i<numItem-1) {
                strb.append(COMMA_DELIMITER);
            }
        }

        System.out.println("Returning String from getString: "+strb.toString());
        return(strb.toString());
    }

    public static void writeBookObj(Book bookObj, String fileName, String Url) throws IOException {

        long timeNow = 0L;
        int i = 0;
        int num_bids = 0;
        int num_asks = 0;
        int bid_ask_cntr = 0;

        File csvFile = null;

        StringBuilder strbhdr = new StringBuilder();
        String title = "book Order CSV Data ";
        String FILE_HEADER = "timestamp,date_time,Bid,price,size,num_orders,Ask,price,size,num_orders";
        String ASK_EMPTY = "Ask,0.0,0.0,0.0,0";
        String BID_EMPTY = "Bid,0.0,0.0,0,";
        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        String fileTitle = "BookData.csv";
        String pathBook = "BOOK";
        String orderType = "null";
        long timeval = 0;
        Long timeStamp = 0L;
        Date dateTrans = new Date();
        TimeManager tm = new TimeManager();

        timeNow = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(timeNow);
        String pathNameDate = date.toString();

        System.out.println("Date for Pathname: "+pathNameDate);

        Path filePath = Paths.get(fileName);
        Path filePathDate = filePath.resolve(pathNameDate).resolve(pathBook);
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
            System.out.println("File : "+pathToFile.toString()+" still exists, write Book data to it.");
            java.sql.Timestamp ts = new Timestamp(timeNow);
            Instant instDate = ts.toInstant();
            LocalDateTime ldt = LocalDateTime.ofInstant(instDate, ZoneOffset.UTC);
            System.out.println("LocalDateTme: "+ldt.toString());

            num_bids = bookObj.bids.size();
            num_asks = bookObj.asks.size();
            System.out.println("NUM BIDS: "+num_bids);
            System.out.println("NUM ASKS: "+num_asks);
            List<String> bids = new ArrayList<String>();
            List<String> asks = new ArrayList<String>();
            List<String> finalList = new ArrayList<String>();

            //"timestamp,date_time,Bid,price,size,num_orders,Ask,price,size,num_orders";
            //Get bid data and build string for CSV file
            //bid_ask_cntr = 3;//price, size, num_orders
            int j = 0;
                for (i = 0;i<num_bids;i++){
                    //Write timestamp, date_time to string builder
                    StringBuilder strb = new StringBuilder();
                    strb.append(ts.getTime());
                    strb.append(COMMA_DELIMITER);
                    strb.append(ldt.toString());
                    strb.append(COMMA_DELIMITER);
                    strb.append("Bid");
                    strb.append(COMMA_DELIMITER);
                    String bidString = new String();
                    bidString = getString(bookObj.bids.get(i));
                    strb.append(bidString);
                    strb.append(COMMA_DELIMITER);
                    System.out.println("Adding a single BID entry as a string to LIST of bids: "+strb.toString());
                    bids.add(strb.toString());
                }

            for(i=0;i<num_bids;i++){
                System.out.println("Bid# :"+i+" "+bids.get(i));
            }

            for (i = 0;i<num_asks;i++){
                //Write timestamp, date_time to string builder
                StringBuilder stra = new StringBuilder();
                stra.append("Ask");
                stra.append(COMMA_DELIMITER);
                String askString = new String();
                askString = getString(bookObj.asks.get(i));
                stra.append(askString);
                System.out.println("Adding a single BID entry as a string to LIST of bids: "+stra.toString());
                asks.add(stra.toString());
            }

            for(i=0;i<num_asks;i++){
                System.out.println("Ask# :"+i+" "+asks.get(i));
            }
            //bw.write(strb.toString());  // append data to file
            if(num_bids == num_asks){//append asks to bids; and we are done
                String strA = new String();
                i = 0;
                for(String strB: bids){
                    StringBuilder strFinal = new StringBuilder();
                    strFinal.append(strB);
                    strA = asks.get(i);
                    strFinal.append(strA);
                    strFinal.append(COMMA_DELIMITER);
                    strFinal.append("Generating URL:");
                    strFinal.append(COMMA_DELIMITER);
                    strFinal.append(Url);
                    strFinal.append(NEW_LINE_SEPARATOR);
                    finalList.add(strFinal.toString());
                    i++;
                }
                i = 0;
                for(String finalMember: finalList){
                    System.out.println("Member#: "+i+" "+finalMember);
                    bw.write(finalMember);  // append data to file
                    i++;
                }
            }

            if(num_bids > num_asks){//append asks to bids; and we are done
                String strA = new String();
                i = 0;
                for(String strB: bids){
                    StringBuilder strFinal = new StringBuilder();
                    strFinal.append(strB);

                    if(i == asks.size()){
                        strA = ASK_EMPTY;
                    }
                    else{
                        strA = asks.get(i);
                    }
                    strFinal.append(strA);
                    strFinal.append(COMMA_DELIMITER);
                    strFinal.append("Generating URL:");
                    strFinal.append(COMMA_DELIMITER);
                    strFinal.append(Url);
                    strFinal.append(NEW_LINE_SEPARATOR);
                    finalList.add(strFinal.toString());
                    i++;
                }
                i = 0;
                for(String finalMember: finalList){
                    System.out.println("Member#: "+i+" "+finalMember);
                    bw.write(finalMember);  // append data to file
                    i++;
                }
            }

            if(num_bids < num_asks){//append asks to bids; and we are done
                String strA = new String();
                i = 0;
                int k = 0;
                for(String strB: bids){
                    StringBuilder strFinal = new StringBuilder();
                    strFinal.append(strB);
                    if(i == asks.size()){//should not happen
                        strA = ASK_EMPTY;
                    }
                    else{
                        strA = asks.get(i);
                        i++;
                    }
                    strFinal.append(strA);
                    strFinal.append(COMMA_DELIMITER);
                    strFinal.append("Generating URL:");
                    strFinal.append(COMMA_DELIMITER);
                    strFinal.append(Url);
                    strFinal.append(NEW_LINE_SEPARATOR);
                    finalList.add(strFinal.toString());
                }
                for(;i<asks.size();i++){
                    StringBuilder strFinalNew = new StringBuilder();
                    strFinalNew.append(BID_EMPTY);
                    strFinalNew.append(strA);//append ASK data string
                    strFinalNew.append(NEW_LINE_SEPARATOR);
                    finalList.add(strFinalNew.toString());
                }
                i = 0;
                for(String finalMember: finalList){
                    System.out.println("Member#: "+i+" "+finalMember);
                    bw.write(finalMember);  // append data to file
                    i++;
                }
            }

            bw.close();
        }

    }

    public static void  main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Book bookData = new Book();

        String inputUrl = null;
        String jsonStr = null;
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

        System.out.println("\n********************************* +++++++++++++++++++++++++++ ************\n");

        try {
            bookData = objectMapper.readValue(new URL(inputUrl),Book.class);
        } catch (JsonProcessingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List <String>> listBidArray = bookData.getBids();
        List<List <String>> listAskArray = bookData.getAsks();

        List<String> stringBidList = new ArrayList<String>();
        List<String> stringAskList = new ArrayList<String>();

        int i = 0;
        int j =0;
        int numBids = 0;
        int numBidData = 0;
        int numAsks = 0;
        int numAskData = 0;

        numBids = listBidArray.size();
        numBidData = stringBidList.size();

        numAsks = listAskArray.size();
        numAskData = listAskArray.size();

        System.out.println("Size or number of Bid Lists in the recent book call: "+listBidArray.size());
        System.out.println("Size or number of Ask Lists in the recent book call: "+listAskArray.size());

        for(i=0; i< numBids; i++){
            stringBidList = listBidArray.get(i);
            numBidData = stringBidList.size();
            System.out.println("Number of bid strings: "+numBidData+" showing item # "+i);
            for(j=0;j<numBidData;j++){
                System.out.println(" Item: "+j+" "+stringBidList.get(j));
            }

        }
        writeBookObj(bookData,"/home/stevenakers/Documents/scratchpadObj",inputUrl);

    }

}
