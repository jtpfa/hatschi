package de.pcmr.shop.util;

import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;


public class ValidationUtilsTest {

    @Test
    public void testSuccessfulHtmlValidation() {
        ValidationUtils.validateNoHtml("Héllô Wôrld");
    }

    @Test
    public void testNotSuccessfulHtmlValidation() {
        assertThrows(ValidationException.class, () -> ValidationUtils.validateNoHtml("<script> Héllô Wôrld </script>"));
    }
}
