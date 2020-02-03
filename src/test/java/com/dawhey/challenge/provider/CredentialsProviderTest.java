package com.dawhey.challenge.provider;

import com.dawhey.challenge.exception.NoCredentialsException;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.PropertyResolver;
import org.springframework.test.util.ReflectionTestUtils;

import static com.dawhey.challenge.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class CredentialsProviderTest {

    private CredentialsProvider underTest = new CredentialsProvider();

    @Test
    public void shouldReturnCredentials_whenThereAreProperties() {
        //given
        ReflectionTestUtils.setField(underTest, "millekod", String.valueOf(MILLEKOD));
        ReflectionTestUtils.setField(underTest, "pesel", String.valueOf(PESEL));
        ReflectionTestUtils.setField(underTest, "password", String.valueOf(PASSWORD));

        //when
        var credentials = underTest.credentials();

        //then
        assertArrayEquals(PESEL, credentials.pesel);
        assertArrayEquals(MILLEKOD, credentials.millekod);
        assertArrayEquals(PASSWORD, credentials.password);
    }

    @Test
    public void shouldThrowException_whenOneOfPropertiesNotPassed() {
        //given
        var source = mock(PropertyResolver.class);
        ReflectionTestUtils.setField(underTest, "millekod", String.valueOf(MILLEKOD));
        ReflectionTestUtils.setField(underTest, "pesel", null);
        ReflectionTestUtils.setField(underTest, "password", Strings.EMPTY);

        //then
        assertThrows(NoCredentialsException.class, () -> underTest.credentials());
    }

}