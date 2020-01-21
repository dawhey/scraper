package com.dawhey.challenge.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jsoup.Connection;

@Data
@AllArgsConstructor
public class MulticodeRequestCommandDataWrapper {

    private Connection.Response response;

    private char[] millekod;
}
