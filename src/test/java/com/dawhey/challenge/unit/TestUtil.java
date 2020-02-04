package com.dawhey.challenge.unit;

import java.util.HashMap;
import java.util.Map;

public class TestUtil {

    public static final char[] PESEL = "12345678901".toCharArray();

    public static final char[] PASSWORD = "123456x".toCharArray();

    public static final char[] MILLEKOD = "123456x".toCharArray();

    public static Map<String, String> welcomePageCookies() {
        var cookies = new HashMap<String, String>();
        cookies.put("COOKIE_A", "VALUE_A");
        cookies.put("COOKIE_B", "VALUE_B");
        return cookies;
    }

    public static Map<String, String> signInCookies() {
        var cookies = new HashMap<String, String>();
        cookies.put("COOKIE_C", "VALUE_C");
        cookies.put("COOKIE_D", "VALUE_D");
        return cookies;
    }
}
