package com.maxcogito.collectapp;

import java.sql.Timestamp;
import java.time.Instant;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeManager {

    private String dateStr = null;
    private Instant instant = null;
    private Timestamp ts = null;
    private Date date = null;

    public TimeManager(String dateStr, Instant instant, Timestamp ts, Date date){
        this.dateStr = dateStr;
        this.instant = instant;
        this.ts = ts;
        this.date = date;
    }

    public TimeManager(){

    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public final String DotZero = "yyyy-MM-dd'T'HH:mm:ssX";
    public final String DotOne = "yyyy-MM-dd'T'HH:mm:ss.SX";
    public final String DotTwo = "yyyy-MM-dd'T'HH:mm:ss.SSX";
    public final String DotThree = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
    public final String DotFour = "yyyy-MM-dd'T'HH:mm:ss.SSSSX";
    public final String DotFive = "yyyy-MM-dd'T'HH:mm:ss.SSSSSX";
    public final String DotSix = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX";
    public final String DotSeven = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSX";
    public final String DotEight = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSX";
    public final String HOURS = "HH";
    public final String MINUTES = "MM";
    public final String SECONDS = "ss";
    public final String MILLISEC = ".SSSX";
    public final String InvalidDateStr = "Invalid_Date_Str";

    //Takes in a String and parses it for DateTime information
    //Returns a LocalDateTime object
   public LocalDateTime parseDateTimeStr (String dateStr){

       String tmpDate = "yyyy-MM-dd'T'HH:mm[:ss][X][.SX][.SSX][.SSSX][.SSSSX][.SSSSSX][.SSSSSSX]";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(tmpDate);
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateStr,formatter);

       System.out.println("ParsedDateTime Instant value: "+parsedDateTime.toInstant(ZoneOffset.UTC));

       Instant intValDateTime = parsedDateTime.toInstant(ZoneOffset.UTC);
       Timestamp tsFromInstant = Timestamp.from(intValDateTime);
       System.out.println("Instant Value from LocalDateTime object: "+intValDateTime);
       System.out.println("Instant Value from LocalDateTime object String: "+intValDateTime.toString());
       System.out.println("Instant Value from LocalDateTime object(EpochMillis): "+intValDateTime.toEpochMilli());
       System.out.println("As EPOCH seconds: "+intValDateTime.getEpochSecond());
       System.out.println("Timestamp Value from Instant Value Date Time: "+tsFromInstant.getTime());

       LocalDateTime testDateTimeObj = LocalDateTime.ofInstant(intValDateTime, ZoneOffset.UTC);
       System.out.println("Inside parseDateTimeStr value of DateTimeStr BUILT from an INSTANT value: "+testDateTimeObj.toString());
        return (parsedDateTime);

   }


    public java.sql.Timestamp parseDateStrtoTsVal (String dateStr) throws DateTimeParseException {

       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        //String tmpDate = analyzeDateStr(dateStr);
        String tmpDate = "yyyy-MM-dd'T'HH:mm[:ss][X][.SX][.SSX][.SSSX][.SSSSX][.SSSSSX][.SSSSSSX]";
        System.out.println("Calling formatter with a date string of: "+tmpDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(tmpDate);
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateStr, formatter);

        System.out.println("ParsedDateTime Instant value: " + parsedDateTime.toInstant(ZoneOffset.UTC));

        Instant intValDateTime = parsedDateTime.toInstant(ZoneOffset.UTC);
        Timestamp tsFromInstant = Timestamp.from(intValDateTime);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(intValDateTime, ZoneOffset.UTC);
        System.out.println("Instant Value from Date String localDateTime: " + intValDateTime.toEpochMilli());
        System.out.println("Timestamp Value from Date String derived Instant object: " +tsFromInstant.getTime());
        System.out.println("Instant Value AS Date String derived from localDateTime: " + intValDateTime);
        System.out.println("Timestamp Value AS Date String derived Instant object: " +tsFromInstant.toString());
        System.out.println("LocalDateTime: " + localDateTime);

        return(tsFromInstant);
    }


   public java.sql.Timestamp parseDateStrtoTs (String dateStr) throws DateTimeParseException {
            String tmpDate = "yyyy-MM-dd'T'HH:mm[:ss][X][.SX][.SSX][.SSSX][.SSSSX][.SSSSSX][.SSSSSSX]";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(tmpDate);
            System.out.println("Calling formatter with a date string of: "+tmpDate);
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern(tmpDate);
            LocalDateTime parsedDateTime = LocalDateTime.parse(dateStr, formatter);

            System.out.println("ParsedDateTime Instant value: " + parsedDateTime.toInstant(ZoneOffset.UTC));

            Instant intValDateTime = parsedDateTime.toInstant(ZoneOffset.UTC);
            Timestamp tsFromInstant = Timestamp.from(intValDateTime);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(intValDateTime, ZoneOffset.UTC);
            System.out.println("LocalDateTime: " + localDateTime);

            return(tsFromInstant);
    }
    public String findResolution(String dateStr){
       String tempStr = new String();
       int i = 0;
       int lengthStr = dateStr.length();
       int resolutionCnt = 0;

       for (i =0;i<lengthStr;i++){
           if(dateStr.charAt(i)==':'){
               resolutionCnt++;
           }
       }

       switch (resolutionCnt){
           case 0:
               tempStr = HOURS;
               break;
           case 1:
               tempStr = MINUTES;
               break;
           case 2:
               tempStr = SECONDS;
               break;
           default: //FIX THIS
               tempStr = MILLISEC;
               break;
        }
       System.out.println("Resolution of the data time string is: "+tempStr+ " with a resolutionCnt of: "+resolutionCnt);
       return(tempStr);
    }
    public String analyzeDateStr(String dateStr){
        String tmpStr = new String();
        int lengthDateStr = 0;
        int indexDot = 0;
        int endMarker = 0;
        int precision = 0;
        lengthDateStr = dateStr.length();
        indexDot = dateStr.lastIndexOf(".");
        endMarker = dateStr.lastIndexOf("Z");

        if(indexDot == -1){
            tmpStr = DotZero;
            System.out.println("ONLY Seconds resolution in string: "+dateStr);
            return(tmpStr);
        }

        precision = endMarker - indexDot;
        precision = precision - 1;
        if(precision>0){
            switch(precision){
                case 1:
                    tmpStr = DotOne;
                    break;
                case 2 :
                    tmpStr = DotTwo;
                    break;
                case 3:
                    tmpStr = DotThree;
                    break;
                case 4:
                    tmpStr = DotFour;
                    break;
                case 5:
                    tmpStr = DotFive;
                    break;
                case 6:
                    tmpStr = DotSix;
                    break;
                case 7:
                    tmpStr = DotSeven;
                    break;
                case 8:
                    tmpStr = DotEight;
                    break;
                default:
                    tmpStr = InvalidDateStr;
                    break;
            }

        }
        else{
            System.out.println("Invalid Date Str input: "+dateStr);
        }
        System.out.println("INDEXDOT value: "+indexDot+" end Marker value: "+endMarker);
        System.out.println("String value set: "+tmpStr+" String precision digit value: "+precision);
        return(tmpStr);
    }

   public java.sql.Date instantParseDateTime(String dateStr){
       Instant instant = Instant.parse(dateStr);
       long timeval = instant.toEpochMilli();
       System.out.println("From Instant API.parse: "+instant.toString());
       System.out.println("Instant timeval :"+timeval);

       java.sql.Date date = new java.sql.Date(timeval);
       java.sql.Timestamp ts = new Timestamp(date.getTime());

       System.out.println("Time Value from date object (date.getTime()): "+date.getTime());

       System.out.println("Timestamp from date object: "+ts.getTime());

       java.sql.Timestamp ts2 = Timestamp.from(instant);
       System.out.println("Timestamp from Timestamp object: "+ts2.getTime());

       return(date);
   }

   public java.util.Date instantParseDate(String dateStr){
       Instant instant = Instant.parse(dateStr);
       System.out.println("From Instant API.parse: "+instant.toString());
       java.util.Date instDate = java.util.Date.from(instant);
       System.out.println("Printing Date object time value:"+instDate.getTime());
       Timestamp instantTimestamp = new Timestamp(instDate.getTime());
       System.out.println("Timestamp Value from Date derived from instant: "+instantTimestamp.getTime());
       return(instDate);
   }
    public static void main(String [] args)
    {
        String testStr = "2021-03-28T21:06:21.566Z";
        TimeManager tm = new TimeManager();
        tm.findResolution(testStr);
        java.sql.Timestamp ts = tm.parseDateStrtoTsVal(testStr);

        System.out.println("************ PARSE DATE STRING TO TIMESTAMP VALUE 1 **************");
        System.out.println("Timestamp Value :"+ts.getTime());

        String testStr2 = "2021-03-28T21:06:06Z";
        TimeManager tm2 = new TimeManager();
        System.out.println("Testing RESOLUTION: ");
        tm.findResolution(testStr2);
        System.out.println("************ PARSE DATE STRING TO TIMESTAMP VALUE 2 **************");
        java.sql.Timestamp ts2 = tm2.parseDateStrtoTsVal(testStr2);

        System.out.println("Timestamp Value :"+ts2.getTime());


    }
}
