package com.zerobase.zbfintech.util;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.zerobase.zbfintech.config.Aes256Config;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;


class Aes256UtilTest {

    @Mock
    private Aes256Config aes256Config;

    @InjectMocks
    private Aes256Util aes256Util;

    public Aes256UtilTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void encrypt() {
        // Given
        String inputText = "Hello World";
        String key = "aes256testkeykey";
        String iv = "aes256testkeykey";

        // When
        when(aes256Config.getKey()).thenReturn(key);
        when(aes256Config.getIv()).thenReturn(iv);
        String encrypt = aes256Util.encrypt(inputText);

        // Then
        assertNotNull(encrypt);
        assertNotEquals(inputText, encrypt);
        verify(aes256Config).getKey();
        verify(aes256Config).getIv();
    }

    @Test
    void decrypt() {
        // Given
        String encryptedText = "mockedEncryptedText";
        String key = "aes256testkeykey";
        String iv = "aes256testkeykey";

        // When
        when(aes256Config.getKey()).thenReturn(key);
        when(aes256Config.getIv()).thenReturn(iv);
        String decrypt = aes256Util.decrypt(encryptedText);

        // Then
        assertEquals(decrypt, decrypt);
        verify(aes256Config).getKey();
        verify(aes256Config).getIv();
    }
}