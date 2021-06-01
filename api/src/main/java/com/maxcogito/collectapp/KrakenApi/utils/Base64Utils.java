package com.maxcogito.collectapp.KrakenApi.utils;

import java.util.Base64;

public class Base64Utils {
    /**
     * Private constructor
     */
    private Base64Utils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Decode from Base64
     *
     * @param input data to decode
     * @return decoded data
     */
    public static byte[] base64Decode(String input) {
        return Base64.getDecoder().decode(input);
    }

    /**
     * Encode into Base64
     *
     * @param data to encode
     * @return encoded data
     */
    public static String base64Encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

}
