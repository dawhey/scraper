package com.dawhey.challenge.model;

import com.dawhey.challenge.exception.InvalidCredentialsException;
import org.jsoup.internal.StringUtil;

public class Credentials {

    public final char[] millekod;

    public final char[] password;

    public final char[] pesel;

    public Credentials(String millekod, String password, String pesel) {
        this.millekod = fromString(millekod, "millenium.millekod");
        this.password = fromString(password, "millenium.password");
        this.pesel = fromString(pesel, "millenium.pesel");
    }

    private char[] fromString(String property, String propertyName) {
        if (StringUtil.isBlank(property)) {
            throw new InvalidCredentialsException("No property with name *" + propertyName + "* passed to application.");
        }
        return property.toCharArray();
    }
}
