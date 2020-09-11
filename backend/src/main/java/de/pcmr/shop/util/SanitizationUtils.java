package de.pcmr.shop.util;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

/**
 * Class sanitizes untrusted html inputs to defined standards.
 *
 * @author Fynn Lohse
 */

public final class SanitizationUtils {
    private static final PolicyFactory SANITIZE_DANGEROUS = new HtmlPolicyBuilder()
            .allowElements("b", "strong", "i", "p", "ul", "ol", "li", "a")
            .allowAttributes("href").onElements("a")
            .allowUrlProtocols("http", "https")
            .toFactory();

    private SanitizationUtils() {
        throw new IllegalStateException();
    }

    /**
     * Method sanitizes untrusted html.
     * Allowed tags: "b", "strong", "i", "p", "ul", "ol", "li", "a"
     * Allowed attributes: "href" on "a"
     * Allowed url protocols: "http", "https"
     *
     * @param string unsanitized html string
     * @return sanitized html string
     */

    public static String sanitizeHtml(String string) {
       return SANITIZE_DANGEROUS.sanitize(string);
    }
}
