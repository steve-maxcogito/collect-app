package com.maxcogito.collectapp.KrakenApi.result;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TradeVolumeResult extends Result<TradeVolumeResult.TradeVolume> {
    public static class TradeVolume {

        public String currency;
        public BigDecimal volume;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("currency", currency)
                    .append("volume", volume)
                    .toString();
        }
    }

}
