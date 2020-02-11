package com.dawhey.challenge.unit.util;

import com.dawhey.challenge.web.parser.ScraperDocument;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScraperDocumentTest {

    public static final String TEST_HTML = "<html>" +
            "    <body>" +
            "        <input name='input_name' value='input_value'/>" +
            "        <div class='class_name'></div>" +
            "        <div class='class_name'></div>" +
            "        <div class='class_name'></div>" +
            "        <p data-element-id='paragraph'></p>" +
            "        <p data-element-id='paragraph'></p>" +
            "    </body>" +
            "</html>";

    private ScraperDocument underTest = new ScraperDocument(Jsoup.parse(TEST_HTML));

    @Test
    public void shouldReturnInputValue_whenFindByNameMethodCalled() {
        //when
        var value = underTest.findValueOfInputByName("input_name");

        //then
        assertEquals("input_value", value);
    }

    @Test
    public void shouldReturnDivElements_whenFindByClassMethodCalled() {
        //when
        var elements = underTest.findElementsByClass("class_name");

        //then
        assertEquals(3, elements.size());
        assertTrue(elements.stream().allMatch(element -> element.tag().getName().equals("div")));
    }

    @Test
    public void shouldReturnParagraphElements_whenFindBySelectorMethodCalled() {
        //when
        var customSelector = "p[data-element-id=paragraph]";
        var elements = underTest.findElementsBySelector(customSelector);

        //then
        assertEquals(2, elements.size());
        assertTrue(elements.stream().allMatch(element -> element.tag().getName().equals("p")));

    }
}