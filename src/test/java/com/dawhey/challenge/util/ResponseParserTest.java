package com.dawhey.challenge.util;

import org.jsoup.Connection;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResponseParserTest {

    private ResponseParser underTest = new ResponseParser();

    @Test
    void shouldThrowRuntimeException_whenIOExceptionIsThrown() throws IOException {
        var response = mock(Connection.Response.class);
        when(response.parse()).thenThrow(new IOException());

        assertThrows(RuntimeException.class, () -> underTest.parse(response));
    }
}