package com.dawhey.challenge.unit.request;

import com.dawhey.challenge.request.PasswordRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordRequestTest {

    private static final String PESEL_1 = "1";
    private static final String PESEL_2 = "2";

    private PasswordRequest underTest;

    @BeforeEach
    public void setUp() {
        var peselFormData = new HashMap<String, String>();
        peselFormData.put("PESEL_1", PESEL_1);
        peselFormData.put("PESEL_2", PESEL_2);

        underTest = new PasswordRequest(peselFormData, null, null, null, null, null);
    }

    @Test
    public void shouldCreateCorrectSecurityDigitsPassword_whenPeselFormPassed() {
        assertEquals(PESEL_1 + PESEL_2, underTest.securityDigitsPassword);
    }

}