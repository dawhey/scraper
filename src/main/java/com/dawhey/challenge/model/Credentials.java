package com.dawhey.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Credentials {

    private char[] millekod;

    private char[] password;

    private char[] pesel;
}
