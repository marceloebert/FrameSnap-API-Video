package com.fiap.framesnap.crosscutting.validations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringValidatorTest {

    @Test
    void isNullOrEmpty_WhenNull_ShouldReturnTrue() {
        assertTrue(StringValidator.isNullOrEmpty(null));
    }

    @Test
    void isNullOrEmpty_WhenEmpty_ShouldReturnTrue() {
        assertTrue(StringValidator.isNullOrEmpty(""));
    }

    @Test
    void isNullOrEmpty_WhenBlank_ShouldReturnTrue() {
        assertTrue(StringValidator.isNullOrEmpty("   "));
    }

    @Test
    void isNullOrEmpty_WhenNotEmpty_ShouldReturnFalse() {
        assertFalse(StringValidator.isNullOrEmpty("test"));
    }

    @Test
    void isNullOrEmpty_WhenNotEmptyWithSpaces_ShouldReturnFalse() {
        assertFalse(StringValidator.isNullOrEmpty("  test  "));
    }
} 