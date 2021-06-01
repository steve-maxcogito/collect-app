package com.maxcogito.collectapp.cryptocompare;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Aggregated",
        "TimeFrom",
        "TimeTo",
        "Data"
})
public class Data {

    @JsonProperty("Aggregated")
    private Boolean aggregated;
    @JsonProperty("TimeFrom")
    private Integer timeFrom;
    @JsonProperty("TimeTo")
    private Integer timeTo;
    @JsonProperty("Data")
    private List<Datum> data = new ArrayList<Datum>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Aggregated")
    public Boolean getAggregated() {
        return aggregated;
    }

    @JsonProperty("Aggregated")
    public void setAggregated(Boolean aggregated) {
        this.aggregated = aggregated;
    }

    public Data withAggregated(Boolean aggregated) {
        this.aggregated = aggregated;
        return this;
    }

    @JsonProperty("TimeFrom")
    public Integer getTimeFrom() {
        return timeFrom;
    }

    @JsonProperty("TimeFrom")
    public void setTimeFrom(Integer timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Data withTimeFrom(Integer timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    @JsonProperty("TimeTo")
    public Integer getTimeTo() {
        return timeTo;
    }

    @JsonProperty("TimeTo")
    public void setTimeTo(Integer timeTo) {
        this.timeTo = timeTo;
    }

    public Data withTimeTo(Integer timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    @JsonProperty("Data")
    public List<Datum> getData() {
        return data;
    }

    @JsonProperty("Data")
    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Data withData(List<Datum> data) {
        this.data = data;
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

    public Data withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Data.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("aggregated");
        sb.append('=');
        sb.append(((this.aggregated == null)?"<null>":this.aggregated));
        sb.append(',');
        sb.append("timeFrom");
        sb.append('=');
        sb.append(((this.timeFrom == null)?"<null>":this.timeFrom));
        sb.append(',');
        sb.append("timeTo");
        sb.append('=');
        sb.append(((this.timeTo == null)?"<null>":this.timeTo));
        sb.append(',');
        sb.append("data");
        sb.append('=');
        sb.append(((this.data == null)?"<null>":this.data));
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
        result = ((result* 31)+((this.timeFrom == null)? 0 :this.timeFrom.hashCode()));
        result = ((result* 31)+((this.aggregated == null)? 0 :this.aggregated.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.timeTo == null)? 0 :this.timeTo.hashCode()));
        result = ((result* 31)+((this.data == null)? 0 :this.data.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Data) == false) {
            return false;
        }
        Data rhs = ((Data) other);
        return ((((((this.timeFrom == rhs.timeFrom)||((this.timeFrom!= null)&&this.timeFrom.equals(rhs.timeFrom)))&&((this.aggregated == rhs.aggregated)||((this.aggregated!= null)&&this.aggregated.equals(rhs.aggregated))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.timeTo == rhs.timeTo)||((this.timeTo!= null)&&this.timeTo.equals(rhs.timeTo))))&&((this.data == rhs.data)||((this.data!= null)&&this.data.equals(rhs.data))));
    }


}
