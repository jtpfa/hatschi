package de.pcmr.shop.util;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import javax.validation.ValidationException;

public class ValidationUtils {

    private static final PolicyFactory DISALLOW_ALL = new HtmlPolicyBuilder().allowTextIn("@").toFactory();
    private static final String EXCEPTION_MESSAGE = "HTML is not allowed.";

    public static String validateNoHtml(String string) throws ValidationException {
        String sanitized = DISALLOW_ALL.sanitize(string);
        if (!sanitized.equals(string)) {
            throw new ValidationException(EXCEPTION_MESSAGE);
        } else {
            return string;
        }
    }
}
