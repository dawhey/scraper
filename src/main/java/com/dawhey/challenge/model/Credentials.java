package com.dawhey.challenge.model;

import com.dawhey.challenge.exception.NoCredentialsException;
import org.jsoup.internal.StringUtil;

public class Credentials {

    public final char[] millekod;

    public final char[] password;

    public final char[] pesel;

    public Credentials(String millekod, String password, String pesel) {
        this.millekod = property(millekod, "millenium.millekod");
        this.password = property(password, "millenium.password");
        this.pesel = property(pesel, "millenium.pesel");
    }

    private static char[] property(String value, String propertyName) {
        if (StringUtil.isBlank(value)) {
            throw new NoCredentialsException("No property with name *" + propertyName + "* passed to application.");
        }
        return value.toCharArray();
    }
}
