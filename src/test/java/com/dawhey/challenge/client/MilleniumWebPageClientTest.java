package com.dawhey.challenge.client;

import com.dawhey.challenge.request.MulticodeRequest;
import com.dawhey.challenge.request.PasswordRequest;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static com.dawhey.challenge.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MilleniumWebPageClientTest {

    private MilleniumWebPageClient underTest = new MilleniumWebPageClient();

    @Test
    public void shouldGetOkResponseStatus_whenGettingWelcomePage() {
        var response = underTest.getMilleniumWelcomePage();
        assertEquals(Response.SC_OK, response.statusCode());
    }

    @Test
    public void shouldGetOkResponseStatus_whenPostingMillekod() {
        var response = underTest.performMultiCodeRequest(new MulticodeRequest(new HashMap<>(), REQUEST_VERIFICATION_TOKEN, MILLEKOD));
        assertEquals(Response.SC_OK, response.statusCode());
    }

    @Test
    public void shouldGetOkResponseStatus_whenPostingPassword() {
        var response = underTest.performPasswordRequest(new PasswordRequest(new HashMap<>(), REQUEST_VERIFICATION_TOKEN, BOT_DETECTION_CLIENT_TOKEN, SECURITY_DIGITS_LOGIN_CHALLENGE, new HashMap<>(), PASSWORD));
        assertEquals(Response.SC_OK, response.statusCode());
    }

    @Test
    public void shouldGetOkResponseStatus_whenGettingAccountsPage() {
        var response = underTest.getAccountListPage(new HashMap<>());
        assertEquals(Response.SC_OK, response.statusCode());
    }
}