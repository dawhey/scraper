package com.dawhey.challenge.service;

import com.dawhey.challenge.model.Account;
import com.dawhey.challenge.model.Credentials;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ScrapingService {

    private final AccountsService accountsService;

    private final LoginService loginService;

    public ScrapingService(AccountsService accountsService, LoginService loginService) {
        this.accountsService = accountsService;
        this.loginService = loginService;
    }

    public Set<Account> scrapeBankPageForAccountDetails(Credentials credentials) {
        var session = loginService.login(credentials);
        return accountsService.extractAccounts(session);
    }
}
