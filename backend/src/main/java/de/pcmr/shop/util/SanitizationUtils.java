package de.pcmr.shop.util;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import javax.validation.ValidationException;

public class SanitizationUtils {
    private static final PolicyFactory SANITIZE_DANGEROUS = new HtmlPolicyBuilder().toFactory();

    public static String sanitizeHtml(String string) throws ValidationException {
       return SANITIZE_DANGEROUS.sanitize(string);
    }
}
