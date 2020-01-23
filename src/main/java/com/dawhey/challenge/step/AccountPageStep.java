package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.model.Account;
import com.dawhey.challenge.step.result.PasswordRequestStepResultSession;
import com.dawhey.challenge.util.DocumentHandler;
import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AccountPageStep {

    private MilleniumWebPageClient milleniumWebPageClient;

    public AccountPageStep(MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    public Set<Account> execute(PasswordRequestStepResultSession session) {
        Connection.Response response = milleniumWebPageClient.getAccountListPage(session.getCookies());
        DocumentHandler documentHandler = new DocumentHandler(response);
        List<Element> accountTableRows = findAccountBlockElements(documentHandler);

        return convertToAccounts(accountTableRows);
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

    private List<Element> findAccountBlockElements(DocumentHandler documentHandler) {
        return documentHandler.findElementsByClass("RowEven");
    }
}
