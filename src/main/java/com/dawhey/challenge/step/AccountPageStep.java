package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.client.RequestParams;
import com.dawhey.challenge.exception.InvalidCredentialsException;
import com.dawhey.challenge.model.Account;
import com.dawhey.challenge.step.output.PasswordRequestStepOutput;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.util.DocumentParser;
import com.dawhey.challenge.util.ResponseParser;
import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AccountPageStep {

    private MilleniumWebPageClient milleniumWebPageClient;

    private ResponseParser responseParser = new ResponseParser();

    public AccountPageStep(MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    public Set<Account> execute(PasswordRequestStepOutput output, Session session) {
        verifyIfUserLoggedIn(output.response);
        var response = milleniumWebPageClient.getAccountListPage(session.cookies);
        var documentParser = new DocumentParser(responseParser, response);
        var accountTableRows = findAccountBlockElements(documentParser);

        return convertToAccounts(accountTableRows);
    }

    private void verifyIfUserLoggedIn(Connection.Response previousResponse) {
        if (!previousResponse.url().toString().equals(RequestParams.MILLENIUM_BASE_URL + "osobiste/")) {
            throw new InvalidCredentialsException("Invalid credentials: failed to log in...");
        }
    }

    private Set<Account> convertToAccounts(List<Element> accountTableRows) {
        return accountTableRows.stream()
                .map(row -> {
                    String accountName = row.getElementsByTag("a").first().text();
                    String accountBalance = row.getElementsByClass("col2")
                            .first()
                            .getElementsByTag("span")
                            .first()
                            .attr("data-text");
                    return new Account(accountName, accountBalance);
                }).collect(Collectors.toSet());
    }

    private List<Element> findAccountBlockElements(DocumentParser documentParser) {
        return documentParser.findElementsByClass("RowEven");
    }
}
