package com.dawhey.challenge.provider;

import com.dawhey.challenge.exception.InvalidCredentialsException;
import com.dawhey.challenge.model.Credentials;
import org.jsoup.internal.StringUtil;
import org.springframework.core.env.PropertyResolver;

public class SpringPropertiesCredentialsProvider implements CredentialsProvider<PropertyResolver> {

    @Override
    public Credentials getFrom(PropertyResolver source) {
        char[] millekod = getProperty(source, "millenium.millekod");
        char[] password = getProperty(source, "millenium.password");
        char[] pesel = getProperty(source, "millenium.pesel");
        return new Credentials(millekod, password, pesel);
    }

    private char[] getProperty(PropertyResolver source, String name) {
        var property = source.getProperty(name);
        if (StringUtil.isBlank(property)) {
            throw new InvalidCredentialsException("No property with name *" + name + "* passed to application.");
        }
        return property.toCharArray();
    }
}
