package com.maxcogito.collectapp.cryptocompare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

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
        "BTC",
        "ETH"
})
public class Pricemultisym {

    @JsonProperty("BTC")
    private Btc btc;
    @JsonProperty("ETH")
    private Eth eth;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("BTC")
    public Btc getBtc() {
        return btc;
    }

    @JsonProperty("BTC")
    public void setBtc(Btc btc) {
        this.btc = btc;
    }

    public Pricemultisym withBtc(Btc btc) {
        this.btc = btc;
        return this;
    }

    @JsonProperty("ETH")
    public Eth getEth() {
        return eth;
    }

    @JsonProperty("ETH")
    public void setEth(Eth eth) {
        this.eth = eth;
    }

    public Pricemultisym withEth(Eth eth) {
        this.eth = eth;
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

    public Pricemultisym withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Pricemultisym.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("btc");
        sb.append('=');
        sb.append(((this.btc == null)?"<null>":this.btc));
        sb.append(',');
        sb.append("eth");
        sb.append('=');
        sb.append(((this.eth == null)?"<null>":this.eth));
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
        result = ((result* 31)+((this.btc == null)? 0 :this.btc.hashCode()));
        result = ((result* 31)+((this.eth == null)? 0 :this.eth.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Pricemultisym) == false) {
            return false;
        }
        Pricemultisym rhs = ((Pricemultisym) other);
        return ((((this.btc == rhs.btc)||((this.btc!= null)&&this.btc.equals(rhs.btc)))&&((this.eth == rhs.eth)||((this.eth!= null)&&this.eth.equals(rhs.eth))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }
    public static String callAPI(String API) throws IOException {
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
        Pricemultisym pmSymUrl = new Pricemultisym();

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

        System.out.println("\n********************************* +++++++++++++++++++++++++++ ************\n");

        try {
            pmSymUrl = objectMapper.readValue(new URL(inputUrl),Pricemultisym.class);
        } catch (JsonProcessingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n********************************* +++++++++++++++++++++++++++ ************\n");
        System.out.println("Bitcoin price USD: "+pmSymUrl.btc.getUsd());
        System.out.println("Eth price per USD: "+pmSymUrl.eth.getUsd());
        System.out.println("Dollar value direct from URL should be: "+pmSymUrl.toString());


    }

}
