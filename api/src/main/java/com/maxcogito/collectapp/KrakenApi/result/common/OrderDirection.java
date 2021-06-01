package com.maxcogito.collectapp.KrakenApi.result.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderDirection {
	
    BUY("buy"),
    SELL("sell");

    private String value;

    OrderDirection(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

	
}
