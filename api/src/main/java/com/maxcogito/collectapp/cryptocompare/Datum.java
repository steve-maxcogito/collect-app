package com.maxcogito.collectapp.cryptocompare;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "time",
        "high",
        "low",
        "open",
        "volumefrom",
        "volumeto",
        "close",
        "conversionType",
        "conversionSymbol"
})

public class Datum {

    @JsonProperty("time")
    private Integer time;
    @JsonProperty("high")
    private Double high;
    @JsonProperty("low")
    private Double low;
    @JsonProperty("open")
    private Double open;
    @JsonProperty("volumefrom")
    private Double volumefrom;
    @JsonProperty("volumeto")
    private Double volumeto;
    @JsonProperty("close")
    private Double close;
    @JsonProperty("conversionType")
    private String conversionType;
    @JsonProperty("conversionSymbol")
    private String conversionSymbol;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("time")
    public Integer getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(Integer time) {
        this.time = time;
    }

    public Datum withTime(Integer time) {
        this.time = time;
        return this;
    }

    @JsonProperty("high")
    public Double getHigh() {
        return high;
    }

    @JsonProperty("high")
    public void setHigh(Double high) {
        this.high = high;
    }

    public Datum withHigh(Double high) {
        this.high = high;
        return this;
    }

    @JsonProperty("low")
    public Double getLow() {
        return low;
    }

    @JsonProperty("low")
    public void setLow(Double low) {
        this.low = low;
    }

    public Datum withLow(Double low) {
        this.low = low;
        return this;
    }

    @JsonProperty("open")
    public Double getOpen() {
        return open;
    }

    @JsonProperty("open")
    public void setOpen(Double open) {
        this.open = open;
    }

    public Datum withOpen(Double open) {
        this.open = open;
        return this;
    }

    @JsonProperty("volumefrom")
    public Double getVolumefrom() {
        return volumefrom;
    }

    @JsonProperty("volumefrom")
    public void setVolumefrom(Double volumefrom) {
        this.volumefrom = volumefrom;
    }

    public Datum withVolumefrom(Double volumefrom) {
        this.volumefrom = volumefrom;
        return this;
    }

    @JsonProperty("volumeto")
    public Double getVolumeto() {
        return volumeto;
    }

    @JsonProperty("volumeto")
    public void setVolumeto(Double volumeto) {
        this.volumeto = volumeto;
    }

    public Datum withVolumeto(Double volumeto) {
        this.volumeto = volumeto;
        return this;
    }

    @JsonProperty("close")
    public Double getClose() {
        return close;
    }

    @JsonProperty("close")
    public void setClose(Double close) {
        this.close = close;
    }

    public Datum withClose(Double close) {
        this.close = close;
        return this;
    }

    @JsonProperty("conversionType")
    public String getConversionType() {
        return conversionType;
    }

    @JsonProperty("conversionType")
    public void setConversionType(String conversionType) {
        this.conversionType = conversionType;
    }

    public Datum withConversionType(String conversionType) {
        this.conversionType = conversionType;
        return this;
    }

    @JsonProperty("conversionSymbol")
    public String getConversionSymbol() {
        return conversionSymbol;
    }

    @JsonProperty("conversionSymbol")
    public void setConversionSymbol(String conversionSymbol) {
        this.conversionSymbol = conversionSymbol;
    }

    public Datum withConversionSymbol(String conversionSymbol) {
        this.conversionSymbol = conversionSymbol;
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

    public Datum withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Datum.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("time");
        sb.append('=');
        sb.append(((this.time == null)?"<null>":this.time));
        sb.append(',');
        sb.append("high");
        sb.append('=');
        sb.append(((this.high == null)?"<null>":this.high));
        sb.append(',');
        sb.append("low");
        sb.append('=');
        sb.append(((this.low == null)?"<null>":this.low));
        sb.append(',');
        sb.append("open");
        sb.append('=');
        sb.append(((this.open == null)?"<null>":this.open));
        sb.append(',');
        sb.append("volumefrom");
        sb.append('=');
        sb.append(((this.volumefrom == null)?"<null>":this.volumefrom));
        sb.append(',');
        sb.append("volumeto");
        sb.append('=');
        sb.append(((this.volumeto == null)?"<null>":this.volumeto));
        sb.append(',');
        sb.append("close");
        sb.append('=');
        sb.append(((this.close == null)?"<null>":this.close));
        sb.append(',');
        sb.append("conversionType");
        sb.append('=');
        sb.append(((this.conversionType == null)?"<null>":this.conversionType));
        sb.append(',');
        sb.append("conversionSymbol");
        sb.append('=');
        sb.append(((this.conversionSymbol == null)?"<null>":this.conversionSymbol));
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
        result = ((result* 31)+((this.high == null)? 0 :this.high.hashCode()));
        result = ((result* 31)+((this.low == null)? 0 :this.low.hashCode()));
        result = ((result* 31)+((this.conversionSymbol == null)? 0 :this.conversionSymbol.hashCode()));
        result = ((result* 31)+((this.volumeto == null)? 0 :this.volumeto.hashCode()));
        result = ((result* 31)+((this.volumefrom == null)? 0 :this.volumefrom.hashCode()));
        result = ((result* 31)+((this.time == null)? 0 :this.time.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.conversionType == null)? 0 :this.conversionType.hashCode()));
        result = ((result* 31)+((this.close == null)? 0 :this.close.hashCode()));
        result = ((result* 31)+((this.open == null)? 0 :this.open.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Datum) == false) {
            return false;
        }
        Datum rhs = ((Datum) other);
        return (((((((((((this.high == rhs.high)||((this.high!= null)&&this.high.equals(rhs.high)))&&((this.low == rhs.low)||((this.low!= null)&&this.low.equals(rhs.low))))&&((this.conversionSymbol == rhs.conversionSymbol)||((this.conversionSymbol!= null)&&this.conversionSymbol.equals(rhs.conversionSymbol))))&&((this.volumeto == rhs.volumeto)||((this.volumeto!= null)&&this.volumeto.equals(rhs.volumeto))))&&((this.volumefrom == rhs.volumefrom)||((this.volumefrom!= null)&&this.volumefrom.equals(rhs.volumefrom))))&&((this.time == rhs.time)||((this.time!= null)&&this.time.equals(rhs.time))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.conversionType == rhs.conversionType)||((this.conversionType!= null)&&this.conversionType.equals(rhs.conversionType))))&&((this.close == rhs.close)||((this.close!= null)&&this.close.equals(rhs.close))))&&((this.open == rhs.open)||((this.open!= null)&&this.open.equals(rhs.open))));
    }

}
