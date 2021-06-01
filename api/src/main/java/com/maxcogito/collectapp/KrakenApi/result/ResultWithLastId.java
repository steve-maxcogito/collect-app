package com.maxcogito.collectapp.KrakenApi.result;

public class ResultWithLastId<T> extends Result<T>{
	
    private Long lastId = 0L;

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }


}
