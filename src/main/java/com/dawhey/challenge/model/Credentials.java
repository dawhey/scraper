package com.dawhey.challenge.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class Credentials {

    public final char[] millekod;

    public final char[] password;

    public final char[] pesel;
}
