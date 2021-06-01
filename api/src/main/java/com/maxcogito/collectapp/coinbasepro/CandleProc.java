package com.maxcogito.collectapp.coinbasepro;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CandleProc {

    private String baseUrl = null;
    private String product = null;
    private String dataFormat = null;
    private List<String> list = null;

    CandleProc(String baseUrl,String product,String dataFormat){
        this.baseUrl = baseUrl;
        this.product = product;
        this.dataFormat = dataFormat;
        this.list = new ArrayList<String>();
    }

    public JsonStrArrayDesc validateJson(String jsonCandidate, char begin, char end){

        JsonStrArrayDesc jstr = new JsonStrArrayDesc();
        int begin_inner_index = 0;
        int begin_outerIndex = 0;
        int end_outerArrayIndex = 0;
        int jsonLength = 0;
        int posIndex = 0;
        boolean valid = false;

        jsonLength = jsonCandidate.length();
        jstr.length = jsonLength;

        //Begin by validating The JsonObject, first two indices should equal begin char, last two indices should be end char

        if(jsonCandidate.charAt(0)==begin){
            // System.out.println("First character of candidate is equal to begin char as expected: "+begin+ " Value found: "+jsonCandidate.charAt(0));
            jstr.valid = true;
            jstr.beg_outIndex = 0;
        }
        else{
            System.out.println("jsonCandidate did not begin with the character as expected. Character found was: "+jsonCandidate.charAt(0));
            jstr.valid = false;
        }

        if(jsonCandidate.charAt(1)==begin){
            //System.out.println("jsonCandidate has innerArray as expected: "+jsonCandidate.charAt(1));
            jstr.beg_innerIndex = 1;
        }
        else{
            System.out.println("jsonCandidate inner array not found, it did not begin with the character as expected."+jsonCandidate.charAt(1)+" was found instead");
            jstr.valid = false;
        }

        //String candidate should stop at length of string

        jstr.stop_index = jsonLength;
        jstr.end_outerArrayIndex = jsonCandidate.lastIndexOf(end);


        if(jstr.stop_index-jstr.end_outerArrayIndex == 1){
            if(jsonCandidate.charAt(jstr.end_outerArrayIndex) == end){
                // System.out.println("Valid JSON string ending with a proper ending character: "+end);
            }
            else{
                System.out.println("jsonCandidate is not valid.");
                jstr.valid = false;
            }
        }

        int itemCntr = 0;
        int innerItemIndex = 0;
        posIndex =jstr.beg_innerIndex;

        // Add loop for counting the inner_array items
        while(posIndex <jstr.stop_index){
            if(jsonCandidate.charAt(posIndex)==begin){
                innerItemIndex=jsonCandidate.indexOf(end,posIndex);
                itemCntr++;
                posIndex = innerItemIndex;

                if(posIndex == jstr.end_outerArrayIndex - 1){
                    // System.out.println("We are done; we found the end of the rainbow Dorothy."+posIndex);
                    // System.out.println("Found last item : "+jsonCandidate.charAt(posIndex)+"  of the last item in the array at position: "+posIndex);
                    jstr.valid = true;
                    jstr.innerArrayitems = itemCntr;
                    //System.out.println(" Final number of array items: "+jstr.innerArrayitems);
                    break;
                }
                else {
                    posIndex = jsonCandidate.indexOf(begin, posIndex);
                    //System.out.println("Starting index of inner item: " + posIndex + " with a character value of: " + jsonCandidate.charAt(posIndex));
                }
                //Set posIndex too
            }
        }

        return jstr;
    }

    public List<String> getStringArray(String sampleData){
        List<String> str = new ArrayList<String>();
        int i = 0;
        int j = 0;
        int first_indexOf = 0;
        int beg_index = 0; //ARRAY element
        int end_index = 0; //ARRAY element
        int last_indexOf = 0;
        int nextInner = 0;
        int nextOuter = 0;

        int size = 0;
        boolean collect = false;

        String localStr = new String();

        first_indexOf = sampleData.indexOf('[');
        beg_index = sampleData.indexOf('[',first_indexOf);
        end_index = sampleData.indexOf(']');
        size = sampleData.length();
        last_indexOf = sampleData.lastIndexOf(']');

        JsonStrArrayDesc jstr = new JsonStrArrayDesc();
        jstr = validateJson(sampleData,'[',']');

        if(jstr.valid == false){
            System.out.println("Did not find a valid inner array Candle object within the sample data.");
            return(null);
        }

        //First get the parameters of the JSON string data

        int ending_bracket = sampleData.indexOf(']',beg_index+1);


        nextInner = beg_index+1;
        nextOuter = sampleData.indexOf(']',beg_index+1);

        while(nextOuter!=last_indexOf) {
            localStr = getArrayElement(sampleData, nextInner, nextOuter, size);
            //System.out.println("Value extracted: " + localStr+"  add it to List of strings to be passed out of getArrayString");
            str.add(localStr);
            //Move pointers in the string to next array element
            nextInner = findStrPos(sampleData, nextOuter, '[', size);

            if(nextInner == -1){
                System.out.println("Invalid parameters in findStringPos: returning NULL");
                return (null);
            }
            if(nextInner == nextOuter){
                // System.out.println("We are done; we found the end of the last inner array element.");
                break;
            }
            nextOuter = sampleData.indexOf(']', nextInner);
            // System.out.println("Next inner array begins at position: " + nextInner);
            //System.out.println("Next inner array ends at position: " + nextOuter);
            //System.out.println("Next inner array ends with a character of this value: " + sampleData.charAt(nextOuter) + "  at position: " + nextOuter);
            // localStr = getArrayElement(sampleData, nextInner, nextOuter, size);
            // System.out.println("Value extracted: " + localStr);
            //System.out.println("Value of nextOuter : "+nextOuter+" value of last-indexOf: "+last_indexOf);
        }
        //System.out.println("Returning a string list of: "+str.size()+" elements/strings");
        return(str);
    }

    public int findStrPos(String str, int begin, char markChar, int stopIndex){
        int pos = 0;

        if(begin < (stopIndex-2) ){
            pos = begin;
            //System.out.println("Inside findStrPos with begin index of: "+begin);
            while((str.charAt(pos)!= markChar)&&(pos<stopIndex)){
                pos++;
                //System.out.println("Evaluating string and character: "+str.charAt(pos)+"  at : "+pos);
                if(pos == (stopIndex-2))
                {
                    break;
                }
            }
        } else{
            if(begin == (stopIndex-2)) {
                // System.out.println("Time to stop parsing, we are done.");
                pos = begin;
            }
            else {
                System.out.println("Invalid string parameters - break execution without parsing.");
                pos = -1;
            }
        }
        //System.out.println("Returning pos value in findStrPos: "+pos);
        return (pos);
    }

    public String getArrayElement(String data, int beg_index, int end_index, int stop_index){
        String str = new String();
        int i =0;
        int j = 0;
        //System.out.println("Value of beginning index inside getArrayElement: "+beg_index);
        for(i=beg_index;i<end_index+1;i++){
            if(i<stop_index) {
                str += data.charAt(i);
                //System.out.println("CHARS in String: "+data.charAt(i));
            }
            else{
                // System.out.println("END of MAIN data Source (index of end)"+i);
                return(str);
            }
        }

        return(str);
    }
    //CandleDataStrings breaks a long JSON string comprising many Candle Object instances into a list
// of individual strings representing one JSON instance of Candle Data each
    public List<String> CandleDataStrings (String CandleStrData, char begin, char end, char delimiter){
        List<String> strVals = new ArrayList<String>();
        String localStr = new String();
        int i = 0;
        int j = 0;
        int startPos = 0;
        int lengthData = 0;

        lengthData = CandleStrData.length();

        if(CandleStrData==null){
            return null;
        }

        for(i=CandleStrData.indexOf(begin)+1;i<lengthData;i++){
            if((CandleStrData.charAt(i)!= end)){
                if(CandleStrData.charAt(i)!=delimiter){
                    localStr += CandleStrData.charAt(i);
                    // System.out.println("Adding: "+CandleStrData.charAt(i)+" to localStr" );
                }
                else{
                    //System.out.println("Found a sub-string: "+localStr+" adding it to strVals position : "+j);
                    //Move pointer (j) to next  position
                    strVals.add(localStr);
                    j++;
                    localStr = new String();
                }
            }
            else{
                // System.out.println("Found the end of the character array with char: "+CandleStrData.charAt(i));
                strVals.add(localStr);
                //System.out.println("Adding last string to the strVals array "+localStr);
            }
        }

        // System.out.println("Returning an array of : "+strVals.size()+"  items.");

        return(strVals);

    }

    public CandleObj CandleObjData(List<String> ListData)
    {
        int i = 0;
        List<String> arrayElements = new ArrayList<String>();
        arrayElements = ListData;

        CandleObj candleItem = new CandleObj();

        for(String element: arrayElements){

            //System.out.println("Element number:"+i+" element: "+element);
            switch (i){
                case 0:
                    candleItem.setTime(Long.parseLong(element));
                    break;
                case 1:
                    candleItem.setLow(Double.parseDouble(element));
                    break;
                case 2:
                    candleItem.setHigh(Double.parseDouble(element));
                    break;
                case 3:
                    candleItem.setOpen(Double.parseDouble(element));
                    break;
                case 4:
                    candleItem.setClose(Double.parseDouble(element));
                    break;
                case 5:
                    candleItem.setVolume(Double.parseDouble(element));
                    break;
                default:
                    break;
            }
            i++;
        }//For loop

        //System.out.println("Value of candleObj: "+candleItem.toString()+"  inside of CandleObjData method.");
        return (candleItem);
    }

    public String returnAPIData(String APIUrl) throws IOException {

        StringBuilder strb = new StringBuilder();

        String httpsURL = APIUrl;
        String inputLine;
        URL myUrl = new URL(httpsURL);
        HttpsURLConnection conn = (HttpsURLConnection)myUrl.openConnection();
        InputStream is = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        try {

            while ((inputLine = br.readLine()) != null) {
                // System.out.println(inputLine);
                strb.append(inputLine);
            }

            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return (strb.toString());
    }

    public void WriteCandleCSV(CandleObj cndl, String fileName) throws IOException {
        StringBuilder strb = new StringBuilder();
        StringBuilder strbhdr = new StringBuilder();
        String title = "Candle CSV Data ";
        String FILE_HEADER = "time,low,high,open,close,volume";
        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        Long timeStamp = 0L;
        Path filePath = null;
        File csvFile = null;

        strbhdr.append(title);
        strbhdr.append(NEW_LINE_SEPARATOR);
        strbhdr.append(FILE_HEADER);
        strbhdr.append(NEW_LINE_SEPARATOR);

        strb.append(cndl.getTime());
        strb.append(COMMA_DELIMITER);
        strb.append(cndl.getLow());
        strb.append(COMMA_DELIMITER);
        strb.append(cndl.getHigh());
        strb.append(COMMA_DELIMITER);
        strb.append(cndl.getOpen());
        strb.append(COMMA_DELIMITER);
        strb.append(cndl.getClose());
        strb.append(COMMA_DELIMITER);
        strb.append(cndl.getVolume());
        strb.append("\n");

        System.out.println("Candle line to put to file: "+strb.toString());

        filePath = Paths.get(fileName);

        System.out.println("filePath after Paths.get: "+filePath.toString());
        Files.createDirectories(filePath);

        //csvFile = new File(filePath.toString()+"/CANDLES.csv");
        System.out.println("Making new file at location: "+filePath.toString()+"/CANDLES.csv");
        Path pathToFile = filePath.resolve("CANDLES.csv");
        System.out.println("Full path to file with NAME: "+pathToFile.toString());
        // BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile,true));

        if(Files.exists(pathToFile)&&Files.isRegularFile(pathToFile)){
            System.out.println("File : "+pathToFile.toString()+" exists");
            BufferedWriter bw = new BufferedWriter(new FileWriter(String.valueOf(pathToFile),true));
            bw.write(strb.toString());  // append data to file
            bw.close();
        }
        else {
            System.out.println("Fil: "+pathToFile.toString()+" does not exist");
            csvFile = new File(filePath.toString()+"/CANDLES.csv");
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile,true));
            bw.write(strbhdr.toString());
            bw.write(strb.toString());  // append data to file
            bw.close();
        }

        //bw.close();
    }

    public void writeCandleList(List<CandleObj> listCandles, String fileName, String URL) throws IOException {

        long timeNow = 0L;
        int i = 0;

        File csvFile = null;
        StringBuilder strb = new StringBuilder();
        StringBuilder strbhdr = new StringBuilder();
        String title = "Candle CSV Data ";
        String FILE_HEADER = "date_time,time,low,high,open,close,volume,URL";
        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        String fileTitle = "CandleData.csv";
        String pathCandles = "CANDLES";
        long timeval = 0;
        Long timeStamp = 0L;

        timeNow = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(timeNow);
        String pathNameDate = date.toString();

        System.out.println("Date for Pathname: "+pathNameDate);

        Path filePath = Paths.get(fileName);
        Path filePathDate = filePath.resolve(pathNameDate).resolve(pathCandles);
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

            System.out.println("File : "+pathToFile.toString()+" still exists, write Candles to it.");
            int k = 0;
            for(CandleObj cndl: listCandles){
                // System.out.println("**** candle value: for item # : "+k+" is : "+cndl.toString());

                timeval = cndl.getTime();
                timeval = timeval * 1000;
                //System.out.println("WRITECANDLES: Time value from API: "+timeval);
                java.sql.Timestamp ts = new Timestamp(timeval);
                Date humandate = new Date(ts.getTime());

                Instant instant = Instant.ofEpochMilli(timeval);
                LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeval), ZoneOffset.UTC);

                System.out.println("Data string long value (from Date object)"+humandate.getTime());
                System.out.println("LocalDateTime object UTC value (hopefully)"+ldt.toString());

                strb.append(ldt.toString());
                strb.append(COMMA_DELIMITER);
                strb.append(cndl.getTime());
                strb.append(COMMA_DELIMITER);
                strb.append(cndl.getLow());
                strb.append(COMMA_DELIMITER);
                strb.append(cndl.getHigh());
                strb.append(COMMA_DELIMITER);
                strb.append(cndl.getOpen());
                strb.append(COMMA_DELIMITER);
                strb.append(cndl.getClose());
                strb.append(COMMA_DELIMITER);
                strb.append(cndl.getVolume());
                strb.append(COMMA_DELIMITER);
                strb.append(URL);
                strb.append("\n");

                // System.out.println("Candle line to put to file: "+strb.toString());
                bw.write(strb.toString());  // append data to file
                strb = new StringBuilder(); //Reset strb to hold a new set of candle data
                k++;

            }
            bw.close();
        }

    }

    public List<CandleObj> buildCandleList(String JsonData){
        List<CandleObj> candleList = new ArrayList<CandleObj>();
        List<String> list = new ArrayList<String>();
        List<String> arrayElements = new ArrayList<String>();
        int itemCount = 0;

        list = getStringArray(JsonData);// These are array elements that are individual strings i.e: "[1611867600,32652.26,32812.37,32652.26,32731.29,81.96405618]"
        //System.out.println("Items returned from getStringArray: "+list.size());
        itemCount = list.size();

        for(int i=0;i<itemCount;i++){
            // System.out.println("Items from JSON Candle data are (item) "+i+" value: "+list.get(i));
            arrayElements = CandleDataStrings(list.get(i),'[',']',',');
            CandleObj candleInst = CandleObjData(arrayElements);
            // System.out.println("Candle Instance: "+candleInst.toString());
            candleList.add(candleInst);
            // System.out.println("Added an instance to the candlesList. CandlesList.size = "+candleList.size());
        }

        return(candleList);
    }
    public static void main (String[] args){
        String baseUrl = "https://api.pro.coinbase.com/products";
        String product = "BTC-USD";
        String dataFormat = "candles";
        String startDate = null;
        String endDate = null;
        String granularity = null;
        String csvFileLoc = "/home/stevenakers/Documents/scratchpadObj/";

        List<String> list = new ArrayList<String>();
        List<CandleObj> candleList = new ArrayList<CandleObj>();

        String JsonAPI = null;
        String extendedUrl = new String();
        StringBuilder strbUrl = new StringBuilder();

        List<String> arrayElements = new ArrayList<String>();
        Scanner scanner = new Scanner(new InputStreamReader(System.in));

        strbUrl.append(baseUrl+"/"+product+"/"+dataFormat);
        System.out.println("The base URL and product descriptor URL is: "+strbUrl.toString());
        System.out.println("Please enter the start date in 'yyyy-mm-dd' format: ");
        startDate = scanner.nextLine();
        System.out.println("Start date string is: "+startDate);
        System.out.println("Please enter the end date of the time period for pricing data (yyyy-mm-dd format): ");
        endDate = scanner.nextLine();
        System.out.println("End date string is: "+endDate);
        strbUrl.append("?"+"start="+startDate);
        strbUrl.append("&"+"end="+endDate);
        System.out.println("The URL for candles data with the start and end dates would be: "+ strbUrl.toString());
        System.out.println("Please enter the granularity of the data query (values are in seconds)");
        System.out.println("Enter values such as: 60 (1 minute), 300 (5 minutes),900 (15 minutes),3600,21600,86400");
        granularity = scanner.nextLine();
        System.out.println("Granularity value entered: "+granularity);
        strbUrl.append("&"+"granularity"+"="+granularity);
        System.out.println("This is the API string with parameters that will be issued in the API call: ");
        System.out.println(strbUrl.toString());
        extendedUrl = strbUrl.toString();
        System.out.println("This is the API string with parameters that will be issued in the API call: "+extendedUrl);

        int itemCount = 0;
        CandleProc candleProc = new CandleProc(baseUrl,product,dataFormat);

        try {
            JsonAPI = candleProc.returnAPIData(extendedUrl);
            System.out.println("RESULTS of API CALL: "+JsonAPI);
        } catch (IOException e) {
            e.printStackTrace();
        }

        candleList= candleProc.buildCandleList(JsonAPI);
        itemCount = candleList.size();
        System.out.println("Size of candle List built by new Method: "+itemCount);

        try {
            candleProc.writeCandleList(candleList,csvFileLoc,extendedUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("FINAL: Size of candle List built by new Method: "+itemCount);
        int i =0;
        long timeval = 0l;
        long timecheck = System.currentTimeMillis();
        System.out.println("Time check :"+timecheck);
        java.sql.Timestamp time = new Timestamp(System.currentTimeMillis());
        System.out.println("Current time: "+time);
        Date staticDate = new Date(time.getTime());
        System.out.println("Current Date: "+staticDate);

        long timeval2 = 1615334400000l;
        java.sql.Timestamp time2 = new Timestamp(timeval2);
        Date date2 = new Date(time2.getTime());
        long timeval3 = 1541797320000l;

        System.out.println("Date2: "+date2);

        java.sql.Timestamp time3 = new Timestamp(timeval3);
        Date date3 = new Date(time3.getTime());
        System.out.println("Date3: "+date3);

        for(CandleObj candle: candleList){
            timeval = candle.getTime();
            timeval = timeval * 1000;
            System.out.println("Time value from API: "+timeval);
            java.sql.Timestamp ts = new Timestamp(timeval);
            Date date = new Date(ts.getTime());
            System.out.println(date);
            System.out.println("High: "+candle.getHigh());
        }


}


}
