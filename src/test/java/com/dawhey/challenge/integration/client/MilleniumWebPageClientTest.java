package com.dawhey.challenge.integration.client;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.request.MulticodeRequest;
import com.dawhey.challenge.request.PasswordRequest;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

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
        var response = underTest.performMultiCodeRequest(new MulticodeRequest(new HashMap<>(), "some-verification-token", "some-millekod".toCharArray()));
        assertEquals(Response.SC_OK, response.statusCode());
    }

    @Test
    public void shouldGetOkResponseStatus_whenPostingPassword() {
        var response = underTest.performPasswordRequest(new PasswordRequest(
                new HashMap<>(),
                "some-verification-token",
                "some-bot-detection-token",
                "some-security-digits-token",
                new HashMap<>(),
                "some-password".toCharArray()));

        assertEquals(Response.SC_OK, response.statusCode());
    }

    @Test
    public void shouldGetOkResponseStatus_whenGettingAccountsPage() {
        var response = underTest.getAccountListPage(new HashMap<>());
        assertEquals(Response.SC_OK, response.statusCode());
    }
}