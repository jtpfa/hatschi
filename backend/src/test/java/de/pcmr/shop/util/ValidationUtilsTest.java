package de.pcmr.shop.util;

import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;


class ValidationUtilsTest {

    @Test
    void testSuccessfulHtmlValidation() {
        ValidationUtils.validateNoHtml("Héllô Wôrld");
    }

    @Test
    void testNotSuccessfulHtmlValidation() {
        assertThrows(ValidationException.class, () -> ValidationUtils.validateNoHtml("<script> Héllô Wôrld </script>"));
    }
}
