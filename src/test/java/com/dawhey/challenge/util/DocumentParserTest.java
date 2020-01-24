package com.dawhey.challenge.util;

import org.jsoup.Connection;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DocumentParserTest {

    private DocumentParser underTest;

    @Test
    void shouldThrowRuntimeException_whenIOExceptionIsThrown() throws IOException {
        var response = mock(Connection.Response.class);
        when(response.parse()).thenThrow(new IOException());

        assertThrows(RuntimeException.class, () -> underTest.parseFrom(response));
    }
}