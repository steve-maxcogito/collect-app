package com.maxcogito.collectapp.KrakenApi.result;


import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class OHLCResult extends ResultWithLastId<Map<String, List<OHLCResult.OHLC>>>{
	
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    @JsonPropertyOrder({"time", "open", "high", "low", "close", "vwap", "volume", "count"})
    public static class OHLC {
        public Integer time;
        public BigDecimal open;
        public BigDecimal high;
        public BigDecimal low;
        public BigDecimal close;
        public BigDecimal vwap;
        public BigDecimal volume;
        public Integer count;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("time", time)
                    .append("open", open)
                    .append("low", low)
                    .append("close", close)
                    .append("vwap", vwap)
                    .append("volume", volume)
                    .append("count", count)
                    .toString();
        }
    }


}
