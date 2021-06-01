package com.maxcogito.collectapp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DecimalUtils {

	 private static final char[] ZEROES = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0'};

	  private static final BigDecimal ONE_BILLION = new BigDecimal(1000000000L);

	  private DecimalUtils() {
	    throw new RuntimeException("DecimalUtils cannot be instantiated.");
	  }

	  public static Duration toDuration(BigDecimal value) {

	    long seconds = value.longValue();
	    int nanoseconds = extractNanosecondDecimal(value, seconds);

	    return Duration.ofSeconds(seconds, nanoseconds);
	  }

	  public static BigDecimal toDecimal(Duration instant) {
	    return new BigDecimal(toDecimal(instant.getSeconds(), instant.getNano()));
	  }

	  public static Timestamp toTimestamp(BigDecimal value) {

	    long seconds = value.longValue();
	    int nanoseconds = extractNanosecondDecimal(value, seconds);

	    Timestamp ts = new Timestamp(seconds * 1000);
	    ts.setNanos(nanoseconds);
	    return ts;
	  }

	  public static BigDecimal toDecimal(Timestamp instant) {
	    long millis = instant.getTime();
	    long secs = millis/1000;
	    return new BigDecimal(toDecimal(secs, instant.getNanos()));
	  }

	  public static Instant toInstant(BigDecimal value) {

	    long seconds = value.longValue();
	    int nanoseconds = extractNanosecondDecimal(value, seconds);
	    return Instant.ofEpochSecond(seconds, nanoseconds);
	  }

	  public static BigDecimal toDecimal(Instant instant) {
	    return new BigDecimal(toDecimal(instant.getEpochSecond(), instant.getNano()));
	  }

	  public static String toDecimal(long seconds, int nanoseconds) {
	    StringBuilder string = new StringBuilder(Integer.toString(nanoseconds));
	    if (string.length() < 9)
	      string.insert(0, ZEROES, 0, 9 - string.length());
	    return seconds + "." + string;
	  }

	  public static int extractNanosecondDecimal(BigDecimal value, long integer) {
	    return value.subtract(new BigDecimal(integer)).multiply(ONE_BILLION).intValue();
	  }
	  
	  public static void main(String[] args) {
		  
		  BigDecimal value = new BigDecimal(1605799852.8802);
		  BigDecimal value2 = new BigDecimal(1381363200);
		  BigDecimal value3 = new BigDecimal(1448496000);
		  BigDecimal value4 = new BigDecimal(1605651653.9743);
		  
		  Timestamp ts = DecimalUtils.toTimestamp(value);

		  Instant instant = DecimalUtils.toInstant(value);

		  System.out.println("Timestamp value is: "+ts);
		  
		  System.out.println("Duration of value: "+DecimalUtils.toDuration(value));
		  
		  System.out.println("Instant of value: "+DecimalUtils.toInstant(value));

		  System.out.println("Instant epoch seconds: "+instant.getEpochSecond());

		  System.out.println("Instant epoch milliseconds: "+instant.toEpochMilli());

		  LocalDateTime ldtObj = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

		  System.out.println("Local Data Time from Instant: "+ldtObj);
		  
		  long seconds = value.longValue();
		  
		  System.out.println("Decimal value of Bigdecimal: "+seconds);
		  
		  System.out.println("NanosecondDecimal value: "+DecimalUtils.extractNanosecondDecimal(value,seconds));
		  
		  System.out.println("Timestamp test for early OHLC data: "+ DecimalUtils.toTimestamp(value2));
		  
		  System.out.println("Timestamp test for early+ some extra time OHLC data: "+ DecimalUtils.toTimestamp(value3)); 
		  
		  System.out.println("Timestamp test for early recent orders data: "+ DecimalUtils.toTimestamp(value4)); 
		  
	  }
	
}
