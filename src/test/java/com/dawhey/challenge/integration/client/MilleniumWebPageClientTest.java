package com.dawhey.challenge.integration.client;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.request.MulticodeRequest;
import com.dawhey.challenge.request.PasswordRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MilleniumWebPageClientTest {

    @Autowired
    private MilleniumWebPageClient underTest;

    @Test
    public void shouldFetchAndParseResponseDocument_whenGettingWelcomePage() {
        var response = underTest.getMilleniumWelcomePage();
        assertNotNull(response.document);
    }

    @Test
    public void shouldFetchAndParseResponseDocument_whenPostingMillekod() {
        var response = underTest.performMultiCodeRequest(new MulticodeRequest(new HashMap<>(), "some-verification-token", "some-millekod".toCharArray()));
        assertNotNull(response.document);
    }

    @Test
    public void shouldFetchAndParseResponseDocument_whenPostingPassword() {
        var response = underTest.performPasswordRequest(new PasswordRequest(
                new HashMap<>(),
                "some-verification-token",
                "some-bot-detection-token",
                "some-security-digits-token",
                new HashMap<>(),
                "some-password".toCharArray()));

        assertNotNull(response.document);
    }

    @Test
    public void shouldFetchAndParseResponseDocument_whenGettingAccountsPage() {
        var response = underTest.getAccountListPage(new HashMap<>());
        assertNotNull(response.document);
    }
}