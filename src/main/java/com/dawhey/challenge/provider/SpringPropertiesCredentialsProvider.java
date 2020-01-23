package com.dawhey.challenge.provider;

import com.dawhey.challenge.model.Credentials;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringPropertiesCredentialsProvider implements CredentialsProvider<ConfigurableApplicationContext> {

    @Override
    public Credentials getFrom(ConfigurableApplicationContext source) {
        char[] millekod = getProperty(source,"millenium.millekod");
        char[] password = getProperty(source,"millenium.password");
        char[] pesel = getProperty(source,"millenium.pesel");
        return new Credentials(millekod, password, pesel);
    }

    private char[] getProperty(ConfigurableApplicationContext source, String name) {
        return source.getEnvironment().getProperty(name).toCharArray();
    }
}
