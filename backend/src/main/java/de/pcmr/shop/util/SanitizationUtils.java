package de.pcmr.shop.util;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public final class SanitizationUtils {
    private static final PolicyFactory SANITIZE_DANGEROUS = new HtmlPolicyBuilder()
            .allowElements("b", "strong", "i", "p", "ul", "ol", "li", "a")
            .allowAttributes("href").onElements("a")
            .allowUrlProtocols("http", "https")
            .toFactory();

    private SanitizationUtils() {
        throw new IllegalStateException();
    }

    public static String sanitizeHtml(String string) {
       return SANITIZE_DANGEROUS.sanitize(string);
    }
}
