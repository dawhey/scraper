package com.dawhey.challenge.model;

import com.dawhey.challenge.exception.NoCredentialsException;
import org.jsoup.internal.StringUtil;

public class Credentials {

    public final char[] millekod;

    public final char[] password;

    public final char[] pesel;

    public Credentials(String millekod, String password, String pesel) {
        this.millekod = credential(millekod, "millekod");
        this.password = credential(password, "password");
        this.pesel = credential(pesel, "pesel");
    }

    private static char[] credential(String value, String credentialName) {
        if (StringUtil.isBlank(value)) {
            throw new NoCredentialsException("No *" + credentialName + "* credential provided.");
        }
        return value.toCharArray();
    }
}
