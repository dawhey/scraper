package com.dawhey.challenge.service;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.model.Account;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.util.ResponseParser;
import com.dawhey.challenge.util.ScraperDocument;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AccountsService {

    private MilleniumWebPageClient milleniumWebPageClient;

    private ResponseParser responseParser = new ResponseParser();

    public AccountsService(MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    public Set<Account> extract(Session session) {
        var response = milleniumWebPageClient.getAccountListPage(session.cookies);
        var document = new ScraperDocument(responseParser, response);
        var accountTableRows = findAccountBlockElements(document);

        return extractAccounts(accountTableRows);
    }

    private Set<Account> extractAccounts(List<Element> accountTableRows) {
        return accountTableRows.stream()
                .map(AccountsService::extractAccount).collect(Collectors.toSet());
    }

    private static Account extractAccount(Element row) {
        String accountName = row.getElementsByTag("a").first().text();
        String accountBalance = row.getElementsByClass("col2")
                .first()
                .getElementsByTag("span")
                .first()
                .attr("data-text");
        return new Account(accountName, accountBalance);
    }

    private List<Element> findAccountBlockElements(ScraperDocument document) {
        return document.findElementsByClass("RowEven");
    }
}
