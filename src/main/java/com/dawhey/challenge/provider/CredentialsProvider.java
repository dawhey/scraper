package com.dawhey.challenge.provider;

import com.dawhey.challenge.model.Credentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CredentialsProvider {

    @Value("${millenium.millekod}")
    private String millekod;

    @Value("${millenium.password}")
    private String password;

    @Value("${millenium.pesel}")
    private String pesel;

    public Credentials credentials() {
        return new Credentials(millekod, password, pesel);
    }
}
