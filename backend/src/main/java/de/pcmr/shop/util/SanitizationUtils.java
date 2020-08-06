package de.pcmr.shop.util;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public final class SanitizationUtils {
    private static final PolicyFactory SANITIZE_DANGEROUS = new HtmlPolicyBuilder().toFactory();

    private SanitizationUtils() {
        throw new IllegalStateException();
    }

    public static String sanitizeHtml(String string) {
       return SANITIZE_DANGEROUS.sanitize(string);
    }
}
