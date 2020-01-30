package com.dawhey.challenge.provider;

import com.dawhey.challenge.exception.InvalidCredentialsException;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.PropertyResolver;

import static com.dawhey.challenge.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpringPropertiesCredentialsProviderTest {

    private SpringPropertiesCredentialsProvider underTest = new SpringPropertiesCredentialsProvider();

    @Test
    public void shouldReturnCredentials_whenThereAreProperties() {
        //given
        var source = mock(PropertyResolver.class);
        when(source.getProperty("millenium.millekod")).thenReturn(String.valueOf(MILLEKOD));
        when(source.getProperty("millenium.pesel")).thenReturn(String.valueOf(PESEL));
        when(source.getProperty("millenium.password")).thenReturn(String.valueOf(PASSWORD));

        //when
        var credentials = underTest.getFrom(source);

        //then
        assertArrayEquals(PESEL, credentials.pesel);
        assertArrayEquals(MILLEKOD, credentials.millekod);
        assertArrayEquals(PASSWORD, credentials.password);
    }

    @Test
    public void shouldThrowException_whenOneOfPropertiesNotPassed() {
        //given
        var source = mock(PropertyResolver.class);
        when(source.getProperty("millenium.millekod")).thenReturn(String.valueOf(MILLEKOD));
        when(source.getProperty("millenium.pesel")).thenReturn(Strings.EMPTY);
        when(source.getProperty("millenium.password")).thenReturn(Strings.EMPTY);

        //then
        assertThrows(InvalidCredentialsException.class, () -> underTest.getFrom(source));
    }

}