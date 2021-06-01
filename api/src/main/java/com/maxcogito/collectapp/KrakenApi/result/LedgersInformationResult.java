package com.maxcogito.collectapp.KrakenApi.result;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maxcogito.collectapp.KrakenApi.result.common.LedgerInformation;

public class LedgersInformationResult extends Result<LedgersInformationResult.LedgersInformation> {
	
    public static class LedgersInformation {

        @JsonProperty("ledger")
        public Map<String, LedgerInformation> ledger;

        public Long count;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("ledger", ledger)
                    .append("count", count)
                    .toString();
        }
    }

}
