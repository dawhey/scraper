package com.dawhey.challenge;

import java.util.HashMap;
import java.util.Map;

public class TestUtil {

    public static final char[] PESEL = "12345678901".toCharArray();

    public static final char[] PASSWORD = "123456x".toCharArray();

    public static final char[] MILLEKOD = "123456x".toCharArray();

    public static final String ACCOUNT_1_NAME = "Konto 360°";

    public static final String ACCOUNT_2_NAME = "Konto 720°";

    public static final String ACCOUNT_1_BALANCE = "12312.22";

    public static final String ACCOUNT_2_BALANCE = "211342.33°";

    public static final String BOT_DETECTION_CLIENT_TOKEN = "7b1b3179-4afc-4d84-b187-73a596b45086";

    public static final String SECURITY_DIGITS_LOGIN_CHALLENGE = "7;1;";

    public static final String REQUEST_VERIFICATION_TOKEN = "E2I7aF4vwiJCfdWEq3s7XG3jbo1xmxaFHwvojwljHyBQ8XWBblsWmg0OhZjfs0wqo6Ahk1g_-aIdA7Ap5jx4NiNGl9k1";


    public static final String MULTICODE_STEP_HTML = String.format("<input name='__RequestVerificationToken' " +
            "type='hidden' value='%s'>", REQUEST_VERIFICATION_TOKEN);

    public static final String ACCOUNT_PAGE_STEP_HTML = String.format("<div class='RowEven'>" +
            "<div class='TemplateColumn col1 hidden-xs'><a class='MNHLink MNCommand TitleLink'>%s</a><br><span" +
            "            class='MNText FullAccountNumber NumberSmallDescription Text' data-dateformat='yy-mm-dd'></span></div>" +
            "<div class='TemplateColumn col2 AlignRight hidden-xs'><span class='MNText LargeAmount Amount'" +
            "            data-currencydecimalseparator=',' data-currencygroupseparator='&nbsp;' data-dateformat='yy-mm-dd'" +
            "            data-formattype='Amount' data-text='%s'><span class='val'></span><span class='cur'>" +
            "                PLN</span></span></div>" +
            "</div>" +
            "<div class='RowEven'>" +
            "<div class='TemplateColumn col1 hidden-xs'><a class='MNHLink MNCommand TitleLink'>%s</a><br><span" +
            "            class='MNText FullAccountNumber NumberSmallDescription Text' data-dateformat=' yy-mm-dd'></span></div>" +
            "<div class='TemplateColumn col2 AlignRight hidden-xs'><span class='MNText LargeAmount Amount'" +
            "            data-currencydecimalseparator=',' data-currencygroupseparator='&nbsp;' data-dateformat='yy-mm-dd'" +
            "            data-formattype='Amount' data-text='%s'><span class='val'></span><span class='cur'>" +
            "                PLN</span></span></div>" +
            "</div>", ACCOUNT_1_NAME, ACCOUNT_1_BALANCE, ACCOUNT_2_NAME, ACCOUNT_2_BALANCE);


    public static final String PASSWORD_STEP_HTML = String.format("<div class='SecurityDigits'>" +
            "        <input id='BotDetection_ClientToken' name='BotDetection.ClientToken' type='hidden'" +
            "                value='%s'>" +
            "<input id='__RequestVerificationToken' name='__RequestVerificationToken' type='hidden'" +
            "                value='%s'>" +
            "        <input id='SecurityDigitsViewModel_LoginChallenge' name='SecurityDigitsViewModel.LoginChallenge' type='hidden'" +
            "                value='%s'>" +
            "        <div class='MNMaskedPasswordItem Active ItemContainer' data-allowedcharacters='DigitsOnly' data-id='PESEL'>" +
            "                <div class='MNTextBox MNField Position Disabled Striped LeftEdge form-group DescriptionTop'" +
            "                        data-allowedcharacters='DigitsOnly' data-maskautoclear='true' data-readonly='True'" +
            "                        data-watermarkalign='notset' id='PESEL_0'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_0_txtContent'>1</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_0_txtContent' maxlength='1' name='PESEL_0'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div class='MNTextBox MNField Position Enabled form-group DescriptionTop'" +
            "                        data-allowedcharacters='DigitsOnly' data-appearance='focusbox' data-maskautoclear='true'" +
            "                        data-order='1' data-readonly='False' data-watermarkalign='notset' data-widget='true'" +
            "                        id='PESEL_1'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_1_txtContent'>2<span class='aria-hide'>#(Accessibility" +
            "                                                description)</span></label></div>" +
            "                        <div><input id='PESEL_1_txtContent' maxlength='1' name='PESEL_1' style='text-align: center;'" +
            "                                type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_2'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_2_txtContent'>3</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_2_txtContent' maxlength='1' name='PESEL_2'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_3'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_3_txtContent'>4</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_3_txtContent' maxlength='1' name='PESEL_3'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_4'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_4_txtContent'>5</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_4_txtContent' maxlength='1' name='PESEL_4'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_5'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_5_txtContent'>6</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_5_txtContent' maxlength='1' name='PESEL_5'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_6'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_6_txtContent'>7</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_6_txtContent' maxlength='1' name='PESEL_6'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_7'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_7_txtContent'>8</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_7_txtContent' maxlength='1' name='PESEL_7'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_8'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_8_txtContent'>9</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_8_txtContent' maxlength='1' name='PESEL_8'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_9'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_9_txtContent'>10</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_9_txtContent' maxlength='1' name='PESEL_9'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_10'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_10_txtContent'>11</label></div>" +
            "                        <div><input id='PESEL_10_txtContent' maxlength='1' name='PESEL_10' style='text-align: center;'" +
            "                                type='password'></div>" +
            "                </div>" +
            "        </div>" +
            "</div>", BOT_DETECTION_CLIENT_TOKEN, REQUEST_VERIFICATION_TOKEN, SECURITY_DIGITS_LOGIN_CHALLENGE);


    public static Map<String, String> welcomePageCookies() {
        var cookies = new HashMap<String, String>();
        cookies.put("COOKIE_A", "VALUE_A");
        cookies.put("COOKIE_B", "VALUE_B");
        return cookies;
    }

    public static Map<String, String> signInCookies() {
        var cookies = new HashMap<String, String>();
        cookies.put("COOKIE_C", "VALUE_C");
        cookies.put("COOKIE_D", "VALUE_D");
        return cookies;
    }
}
