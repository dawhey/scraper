package com.dawhey.challenge.integration.client;

import com.dawhey.challenge.web.client.MilleniumWebPageClient;
import com.dawhey.challenge.web.request.MulticodeRequestPayload;
import com.dawhey.challenge.web.request.PasswordRequestPayload;
import org.apache.logging.log4j.util.Strings;
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
        var response = underTest.performMultiCodeRequest(new MulticodeRequestPayload("some-verification-token", "some-millekod".toCharArray()), new HashMap<>());
        assertNotNull(response.document);
    }

    @Test
    public void shouldFetchAndParseResponseDocument_whenPostingPassword() {
        var peselFormData = new HashMap<String, String>();
        var response = underTest.performPasswordRequest(new PasswordRequestPayload(
                new HashMap<>(),
                "some-verification-token",
                "some-bot-detection-token",
                "some-security-digits-token",
                "some-password".toCharArray(),
                String.join(Strings.EMPTY, peselFormData.values())), new HashMap<>());

        assertNotNull(response.document);
    }

    @Test
    public void shouldFetchAndParseResponseDocument_whenGettingAccountsPage() {
        var response = underTest.getAccountListPage(new HashMap<>());
        assertNotNull(response.document);
    }
}