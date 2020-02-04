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

    private final MilleniumWebPageClient milleniumWebPageClient;

    private final ResponseParser responseParser;

    public AccountsService(MilleniumWebPageClient milleniumWebPageClient, ResponseParser responseParser) {
        this.milleniumWebPageClient = milleniumWebPageClient;
        this.responseParser = responseParser;
    }

    public Set<Account> extractAccounts(Session session) {
        var response = milleniumWebPageClient.getAccountListPage(session.cookies);
        var document = responseParser.parse(response);
        var accountTableRows = findAccountBlockElements(document);
        return extract(accountTableRows);
    }

    private Set<Account> extract(List<Element> accountTableRows) {
        return accountTableRows.stream().map(Account::new).collect(Collectors.toSet());
    }

    private List<Element> findAccountBlockElements(ScraperDocument document) {
        return document.findElementsByClass("RowEven");
    }
}
