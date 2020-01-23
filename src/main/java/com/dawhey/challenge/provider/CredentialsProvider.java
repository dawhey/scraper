package com.dawhey.challenge.provider;

import com.dawhey.challenge.model.Credentials;

public interface CredentialsProvider<T> {

    Credentials getFrom(T source);
}
