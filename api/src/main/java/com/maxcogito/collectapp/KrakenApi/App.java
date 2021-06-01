package com.maxcogito.collectapp.KrakenApi;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.maxcogito.collectapp.KrakenApi.input.Interval;
import com.maxcogito.collectapp.KrakenApi.result.OHLCResult;
import com.maxcogito.collectapp.KrakenApi.result.OHLCResult.OHLC;
import com.maxcogito.collectapp.KrakenApi.result.OrderBookResult;
import com.maxcogito.collectapp.KrakenApi.result.RecentSpreadResult;
import com.maxcogito.collectapp.KrakenApi.result.RecentTradeResult;
import com.maxcogito.collectapp.KrakenApi.result.RecentTradeResult.RecentTrade;
import com.maxcogito.collectapp.KrakenApi.result.ServerTimeResult;
import com.maxcogito.collectapp.KrakenApi.result.TickerInformationResult;
import com.maxcogito.collectapp.KrakenApi.result.TradesHistoryResult;
import com.maxcogito.collectapp.DecimalUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!, let's get some OHLC prices from Kraken API." );
        
        KrakenAPIClient client = new KrakenAPIClient();
        
        try {
			ServerTimeResult serverTimeresult = client.getServerTime();
			ServerTimeResult.ServerTime serverTime = serverTimeresult.getResult();
			
			System.out.println(String.format("timestamp: %d => %s",
				    serverTimeresult.getResult().unixtime,
				    serverTimeresult.getResult().rfc1123));
			
			System.out.println("Server time must be: "+serverTime);
			long since = 0L;
			
			//since = 1380758400L;
			
			OHLCResult OHLCresult = client.getOHLC("XBTUSD", Interval.ONE_WEEK,(int)since);
			since = OHLCresult.getLastId();
			long adjustedSince = since * 1000;
			Instant instant = Instant.ofEpochMilli(adjustedSince);
			LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
			System.out.println("*******************************");
			System.out.println("Local time from time value in API call: "+ldt);
			System.out.println("********************************");
			System.out.println("unix time: "+Long.toString(since));
			System.out.println("unix time: "+since);
			Timestamp timestamp = new Timestamp(since);
			System.out.println("Timestamp: "+timestamp);
			Timestamp systimestamp = new Timestamp(System.currentTimeMillis());
			System.out.println("System timestamp: "+systimestamp);
			
			System.out.println("System (raw) timestamp: "+systimestamp.getTime());
			
			String input = "2020-10-30 16:11:54.488";
			System.out.println("Date string: "+input);
			
            java.sql.Timestamp ts = java.sql.Timestamp.valueOf(input);
            
            System.out.println("Timestamp value : "+ts.getTime());
			
			//System.out.println(OHLCresult.getResult()); // OHLC data
			Map<String,List<OHLC>> mres = OHLCresult.getResult();
			int i =0;
		
			for(Map.Entry<String,List<OHLCResult.OHLC>> entry : mres.entrySet()) {
				System.out.println("OHLC List size: "+ entry.getValue().size());
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().toString());
				System.out.println("Key = " + entry.getKey() + ", Index = " + i + ", Value = " +entry.getValue().get(i));
//Get the list 
				List<OHLCResult.OHLC> OHLClist = entry.getValue();
				System.out.println("OHLCresult.OHLC list size: "+OHLClist.size());
				for (int j=0;j<OHLClist.size();j++){
					System.out.println("Item: "+j+" for OHLClist open value: "+OHLClist.get(j).open);
					System.out.println("Item: "+j+" for OHLClist close value: "+OHLClist.get(j).close);
					System.out.println("Item: "+j+" for OHLClist TIME value: "+OHLClist.get(j).time);
				}
				i++;
			}
				
			//System.out.println(OHLCresult.getLastId()); // last id
			
			RecentTradeResult recenttrades = client.getRecentTrades("XBTUSD");
			
			System.out.println("\nRECENT TRADES: "+recenttrades.getResult());
			System.out.println("\n ******Last ID ******: "+recenttrades.getLastId());
			
			Map<String, List<RecentTrade>> mrecentTraderes = recenttrades.getResult();
			int k =0;
			
			for(Map.Entry<String,List<RecentTradeResult.RecentTrade>> mapentry : mrecentTraderes.entrySet()) {
				System.out.println("RECENT TRADES List size: "+ mapentry.getValue().size());
				System.out.println("Key = " + mapentry.getKey() + ", Value = " + mapentry.getValue().toString());
				System.out.println("Key = " + mapentry.getKey() + ", Index = " + k + ", Value = " +mapentry.getValue().get(k));
				
				List<RecentTradeResult.RecentTrade> RecentTradelist = mapentry.getValue();
				System.out.println("RECENT TRADE List Size: "+RecentTradelist.size());
				
				for (int l=0;l<RecentTradelist.size();l++){
					System.out.println("Item: "+l+" for Recent Trades price value: "+RecentTradelist.get(l).price);
					System.out.println("Item: "+l+" for Recent Trades volume value: "+RecentTradelist.get(l).volume);
					System.out.println("Item: "+l+" for Recent Trade time value: "+RecentTradelist.get(l).time);
					System.out.println("Item: "+l+" for Recent Trade marketLimit value: "+RecentTradelist.get(l).marketLimit);
					System.out.println("Item: "+l+" for Recent Trade buySell value: "+RecentTradelist.get(l).buySell);
					System.out.println("Item: "+l+" for Recent Trade Miscellaneous value: "+RecentTradelist.get(l).miscellaneous);
				}
			
				k++;
			}
			
			OrderBookResult orderBookResult = client.getOrderBook("XBTUSD");
			int p = 0;
			
			orderBookResult.getResult().forEach((currency,orders) -> {
				System.out.println("Size of ASKS values: " +orders.asks.size());
				System.out.println("Size of BIDS values: " +orders.bids.size());
				System.out.println(currency +" asks:" + orders.asks);
				System.out.println(currency + " bids: " + orders.bids);
				System.out.println(currency + " ask price: " + orders.asks.get(p).price);
			});
			
			
			RecentSpreadResult spreadresult = client.getRecentSpreads("XBTUSD");
			System.out.println("Spreads: "+spreadresult.getResult());
			System.out.println("last id: "+spreadresult.getLastId());
			System.out.println("\n************\n");
			System.out.println("Size of SpreadResutls List: "+spreadresult.getResult().size());

			System.out.println("\nRECENT TRADES: "+recenttrades.getResult());
			System.out.println("\n ******Last ID ******: "+recenttrades.getLastId());

			Map<String, List<RecentSpreadResult.Spread>> mrecentSpreadres = spreadresult.getResult();
			int m =0;

			for(Map.Entry<String,List<RecentSpreadResult.Spread>> mapentry : mrecentSpreadres.entrySet()) {
				System.out.println("RECENT SPREADS List size: "+ mapentry.getValue().size());
				System.out.println("Key = " + mapentry.getKey() + ", Value = " + mapentry.getValue().toString());
				System.out.println("Key = " + mapentry.getKey() + ", Index = " + m + ", Value = " +mapentry.getValue().get(m));

				List<RecentSpreadResult.Spread> RecentSpreadlist = mapentry.getValue();
				System.out.println("RECENT Spread List Size: "+RecentSpreadlist.size());
				for(int n =0;n<RecentSpreadlist.size();n++){
					System.out.println("Item: "+n+" for Recent Spread time value: "+RecentSpreadlist.get(n).time);
					BigDecimal timeVal = BigDecimal.valueOf(RecentSpreadlist.get(n).time);
					Instant instantVal = DecimalUtils.toInstant(timeVal);
					System.out.println("Time (UTC) value: "+instantVal);
					System.out.println("Item: "+n+" for Recent Spread bid value: "+RecentSpreadlist.get(n).bid);
					System.out.println("Item: "+n+" for Recent Spread ask value: "+RecentSpreadlist.get(n).ask);
				}
				m++;
			}


			TickerInformationResult tickResult = client.getTickerInformation(Arrays.asList("XBTUSD"));

			Map<String, TickerInformationResult.TickerInformation> mrecentTickres = tickResult.getResult();
			for(Map.Entry<String, TickerInformationResult.TickerInformation> mapentry:mrecentTickres.entrySet()){
				BigDecimal price = mapentry.getValue().lastTradeClosed.price;
				System.out.println("\n**** LAST TRADE CLOSED PRICE: "+mapentry.getValue().lastTradeClosed.price);
				System.out.println("\n**** LAST TRADE CLOSED lotVOlume: "+mapentry.getValue().lastTradeClosed.lotVolume);

				System.out.println("\n**** TICKERINFORMATION **** ASK WHOLELOT VOLUME: "+mapentry.getKey() + ":  " + mapentry.getValue().ask.wholeLotVolume);
				System.out.println("\n**** TICKERINFORMATION **** ASK LOT VOLUME:  "+mapentry.getKey() + ":  " + mapentry.getValue().ask.lotVolume);
				System.out.println("\n**** TICKERINFORMATION **** ASK PRICE:  "+mapentry.getKey() + ":  " + mapentry.getValue().ask.price);
				System.out.println("\n**** TICKERINFORMATION **** BID WHOLELOT VOLUME: "+mapentry.getKey() + ":  " + mapentry.getValue().bid.wholeLotVolume);
				System.out.println("\n**** TICKERINFORMATION **** BID LOT VOLUME:  "+mapentry.getKey() + ":  " + mapentry.getValue().bid.lotVolume);
				System.out.println("\n**** TICKERINFORMATION **** BID PRICE:  "+mapentry.getKey() +":  "+mapentry.getValue().bid.price);
				System.out.println("\n**** TICKERINFORMATION **** Today OPEN PRICE:  "+mapentry.getKey() +":  "+mapentry.getValue().todayOpenPrice);
				System.out.println("\n**** TICKERINFORMATION **** Today Volume Last24 Hours:  "+mapentry.getKey() +":  "+mapentry.getValue().volume.last24hours);
				System.out.println("\n**** TICKERINFORMATION **** Today Volume:  "+mapentry.getKey() +":  "+mapentry.getValue().volume.today);
				System.out.println("\n**** TICKERINFORMATION **** Today Volume Weighted Average Last24 Hours:  "+mapentry.getKey() +":  "+mapentry.getValue().volumeWeightAverage.last24hours);
				System.out.println("\n**** TICKERINFORMATION **** Today Volume Weight Average Today:  "+mapentry.getKey() +":  "+mapentry.getValue().volumeWeightAverage.today);
				System.out.println("\n**** TICKERINFORMATION **** Number of Trades Today:  "+mapentry.getKey() +":  "+mapentry.getValue().numberOfTrades.today);
				System.out.println("\n**** TICKERINFORMATION **** Number of Trades Today:  "+mapentry.getKey() +":  "+mapentry.getValue().numberOfTrades.last24hours);
				System.out.println("\n**** TICKERINFORMATION **** LOW PRICE Today:  "+mapentry.getKey() +":  "+mapentry.getValue().low.today);
				System.out.println("\n**** TICKERINFORMATION **** LOW PRICE Today:  "+mapentry.getKey() +":  "+mapentry.getValue().low.last24hours);
				System.out.println("\n**** TICKERINFORMATION **** HighPRICE Today:  "+mapentry.getKey() +":  "+mapentry.getValue().high.today);
				System.out.println("\n**** TICKERINFORMATION **** HIGH PRICE Today:  "+mapentry.getKey() +":  "+mapentry.getValue().high.last24hours);
				System.out.println("\n**** TICKERINFORMATION **** OPEN PRICE Today:  "+mapentry.getKey() +":  "+mapentry.getValue().todayOpenPrice);
			}
		
			// TickerInformationResult tickresult = client.getTickerInformation(Arrays.asList("BTCEUR","ETHEUR","XBTUSD"));
			System.out.println("Ticker result size: "+tickResult.getResult().size());


			System.out.println(tickResult.getResult().toString());



			System.out.println("Size of TickerResult Map: "+mrecentTickres.size());

		} catch (KrakenApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

   
    }
}
