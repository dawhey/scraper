package com.dawhey.challenge.service;

import com.dawhey.challenge.command.AccountPageCommand;
import com.dawhey.challenge.command.MulticodeRequestCommand;
import com.dawhey.challenge.command.PasswordRequestCommand;
import com.dawhey.challenge.command.WelcomePageCommand;
import com.dawhey.challenge.model.Account;
import com.dawhey.challenge.wrapper.AccountPageCommandDataWrapper;
import com.dawhey.challenge.wrapper.MulticodeRequestCommandDataWrapper;
import com.dawhey.challenge.wrapper.PasswordRequestCommandDataWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class ScrapingService {

    private final ResponseValidatingService validatingService;

    private final WelcomePageCommand welcomePageCommand;

    private final MulticodeRequestCommand multicodeRequestCommand;

    private final PasswordRequestCommand passwordRequestCommand;

    private final AccountPageCommand accountPageCommand;

    public ScrapingService(final ResponseValidatingService validatingService,
                           final WelcomePageCommand welcomePageCommand,
                           final MulticodeRequestCommand multicodeRequestCommand,
                           final PasswordRequestCommand passwordRequestCommand,
                           final AccountPageCommand accountPageCommand) {
        this.validatingService = validatingService;
        this.welcomePageCommand = welcomePageCommand;
        this.multicodeRequestCommand = multicodeRequestCommand;
        this.passwordRequestCommand = passwordRequestCommand;
        this.accountPageCommand = accountPageCommand;
    }

    public Set<Account> scrapBankPageForAccountDetails(final char[] millekod, final char[] pesel, final char[] password) throws Exception {
        var response = welcomePageCommand.execute(Optional.empty());
        final Map<String, String> welcomePageCookies = response.cookies();

        response = multicodeRequestCommand.execute(new MulticodeRequestCommandDataWrapper(response, millekod));
        validatingService.validateMulticodeRequestResponse(response);
        response = passwordRequestCommand.execute(new PasswordRequestCommandDataWrapper(response, welcomePageCookies, pesel, password));
        validatingService.validatePasswordRequestResponse(response);
        return accountPageCommand.execute(new AccountPageCommandDataWrapper(welcomePageCookies, response));
    }
}
