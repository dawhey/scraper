package com.dawhey.challenge.service;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.model.Account;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.util.ScraperDocument;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AccountsService {

    private final MilleniumWebPageClient milleniumWebPageClient;

    public AccountsService(MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    public Set<Account> extractAccounts(Session session) {
        var response = milleniumWebPageClient.getAccountListPage(session.cookies);
        var accountTableRows = findAccountBlockElements(response.document);
        return extract(accountTableRows);
    }

    private static List<Element> findAccountBlockElements(ScraperDocument document) {
        return document.findElementsByClass("RowEven");
    }

    private static Set<Account> extract(List<Element> accountTableRows) {
        return accountTableRows.stream()
                .map(e -> new Account(extractAccountName(e), extractAccountBalance(e)))
                .collect(Collectors.toSet());
    }

    private static String extractAccountName(Element e) {
        return e.getElementsByTag("a").first().text();
    }

    private static String extractAccountBalance(Element e) {
        return e.getElementsByClass("col2")
                .first()
                .getElementsByTag("span")
                .first()
                .attr("data-text");
    }
}
