package com.dawhey.challenge.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jsoup.nodes.Element;

@ToString
@EqualsAndHashCode
public class Account {

    public final String name;

    public final String balance;

    public Account(Element e) {
        this.name = extractAccountName(e);
        this.balance = extractAccountBalance(e);
    }

    private String extractAccountName(Element e) {
        return e.getElementsByTag("a").first().text();
    }

    private String extractAccountBalance(Element e) {
        return e.getElementsByClass("col2")
                .first()
                .getElementsByTag("span")
                .first()
                .attr("data-text");
    }
}
