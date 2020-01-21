package com.dawhey.challenge.command;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.model.Account;
import com.dawhey.challenge.util.DocumentHandler;
import com.dawhey.challenge.wrapper.AccountPageCommandDataWrapper;
import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AccountPageCommand extends ScrapingCommand<AccountPageCommandDataWrapper, Set<Account>> {

    private final MilleniumWebPageClient milleniumWebPageClient;

    public AccountPageCommand(MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    @Override
    protected Set<Account> executeLogic(AccountPageCommandDataWrapper commandData) throws Exception {
        final Connection.Response response = milleniumWebPageClient.getAccountListPage(commandData.getPreviousResponse(), commandData.getCookies());
        final DocumentHandler documentHandler = new DocumentHandler(response.parse());
        final List<Element> accountTableRows = findAccountBlockElements(documentHandler);

        return convertToAccounts(accountTableRows);
    }

    private Set<Account> convertToAccounts(List<Element> accountTableRows) {
        return accountTableRows.stream()
                .map(row -> {
                    final String accountName = row.getElementsByTag("a").first().text();
                    final String accountBalance = row.getElementsByClass("col2")
                            .first()
                            .getElementsByTag("span")
                            .first()
                            .attr("data-text");
                    return new Account(accountName, accountBalance);
                }).collect(Collectors.toSet());
    }

    @Override
    protected String name() {
        return "Account Page Command";
    }

    private List<Element> findAccountBlockElements(final DocumentHandler documentHandler) {
        return documentHandler.findElementsByClass("RowEven");
    }
}
