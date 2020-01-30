package com.dawhey.challenge.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Account {

    public final String name;

    public final String balance;
}
