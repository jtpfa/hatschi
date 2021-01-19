package de.pcmr.shop.util;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import javax.validation.ValidationException;

/**
 * Class validates that there is no html in user inputs.
 *
 * @author Fynn Lohse
 */

public final class ValidationUtils {

    private static final PolicyFactory DISALLOW_ALL = new HtmlPolicyBuilder().allowTextIn("@").toFactory();
    private static final String EXCEPTION_MESSAGE = "HTML is not allowed.";

    private ValidationUtils() {
        throw new IllegalStateException();
    }

    /**
     * Method validates that there is no html in user inputs.
     *
     * @param string user input string
     * @return user input string (if no html)
     * @throws ValidationException if html is detected
     */

    public static String validateNoHtml(String string) {
        String sanitized = DISALLOW_ALL.sanitize(string);
        if (!sanitized.equals(string)) {
            throw new ValidationException(EXCEPTION_MESSAGE);
        } else {
            return string;
        }
    }
}
