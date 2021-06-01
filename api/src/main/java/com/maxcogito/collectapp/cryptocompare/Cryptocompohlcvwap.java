package com.maxcogito.collectapp.cryptocompare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "USD",
        "ConversionType"
})

public class Cryptocompohlcvwap {

    @JsonProperty("USD")
    private Double usd;
    @JsonProperty("ConversionType")
    private ConversionType conversionType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("USD")
    public Double getUsd() {
        return usd;
    }

    @JsonProperty("USD")
    public void setUsd(Double usd) {
        this.usd = usd;
    }

    public Cryptocompohlcvwap withUsd(Double usd) {
        this.usd = usd;
        return this;
    }

    @JsonProperty("ConversionType")
    public ConversionType getConversionType() {
        return conversionType;
    }

    @JsonProperty("ConversionType")
    public void setConversionType(ConversionType conversionType) {
        this.conversionType = conversionType;
    }

    public Cryptocompohlcvwap withConversionType(ConversionType conversionType) {
        this.conversionType = conversionType;
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

    public Cryptocompohlcvwap withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Cryptocompohlcvwap.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("usd");
        sb.append('=');
        sb.append(((this.usd == null)?"<null>":this.usd));
        sb.append(',');
        sb.append("conversionType");
        sb.append('=');
        sb.append(((this.conversionType == null)?"<null>":this.conversionType));
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
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.conversionType == null)? 0 :this.conversionType.hashCode()));
        result = ((result* 31)+((this.usd == null)? 0 :this.usd.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Cryptocompohlcvwap) == false) {
            return false;
        }
        Cryptocompohlcvwap rhs = ((Cryptocompohlcvwap) other);
        return ((((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties)))&&((this.conversionType == rhs.conversionType)||((this.conversionType!= null)&&this.conversionType.equals(rhs.conversionType))))&&((this.usd == rhs.usd)||((this.usd!= null)&&this.usd.equals(rhs.usd))));
    }

    public static String callAPI(String API) throws IOException{
        String str = new String();
        String httpsURL = API;
        String inputLine;
        URL myUrl = new URL(httpsURL);
        HttpsURLConnection conn = (HttpsURLConnection)myUrl.openConnection();
        InputStream is = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        try {

            while ((inputLine = br.readLine()) != null) {
                System.out.println(inputLine);
                str+= inputLine;
            }

            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Returning API JSON: "+str);
        return(str);
    }

    public static void main(String [] args){

        String inputUrl = null;
        String jsonStr = null;
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        ObjectMapper objectMapper = new ObjectMapper();
        Cryptocompohlcvwap cmpVwap = new Cryptocompohlcvwap();
        Cryptocompohlcvwap cmpVwapUrl = new Cryptocompohlcvwap();

        System.out.println("Please enter an API url to test: ");
        inputUrl = scanner.nextLine();
        System.out.println("URL to test: "+inputUrl);
        System.out.println("Calling API at the entered URL.");

        try {
            jsonStr=callAPI(inputUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Json string to map to an object is: "+jsonStr);


        try {
            cmpVwapUrl = objectMapper.readValue(new URL(inputUrl),Cryptocompohlcvwap.class);
        } catch (JsonProcessingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Dollar value direct from URL should be: "+cmpVwapUrl.getUsd());
    }
}
