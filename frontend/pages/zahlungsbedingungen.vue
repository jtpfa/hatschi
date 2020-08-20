<template>
    <b-container class="my-5">
        <h1>Zahlung und Versand</h1>
        <p>Es gelten folgende Bedingungen:</p>
        <p><strong>Versandbedingungen</strong></p>
        <p>
            Die Lieferung erfolgt innerhalb Deutschlands und in die nachstehenden Länder: Albanien, Belgien, Bosnien und
            Herzegowina, Bulgarien, Dänemark, Estland, Finnland, Frankreich, Griechenland, Großbritannien, Irland,
            Island, Italien, Kasachstan, Kosovo, Kroatien, Lettland, Liechtenstein, Litauen, Luxemburg, Malta,
            Mazedonien, Moldawien, Montenegro, Niederlande, Norwegen, Österreich, Polen, Rumänien, Russland, Schweden,
            Schweiz, Serbien, Slowakei, Slowenien, Spanien, Tschechien, Türkei, Ukraine, Ungarn, Weißrussland.
        </p>
        <p><strong>Versandkosten (inklusive gesetzliche Mehrwertsteuer)</strong></p>
        <p><strong>Lieferungen innerhalb Deutschlands:</strong></p>
        <p>Für die Versandart "Standard Versand" berechnen wir die Versandkosten pauschal mit 2,50 € pro Bestellung.</p>
        <p>
            Für die Versandart "Standard Buchversand" berechnen wir die Versandkosten pauschal mit 4,50 € pro
            Bestellung.
        </p>
        <p><strong>Lieferungen ins Ausland:</strong></p>
        <p>Für die Versandart "Standard Versand" berechnen wir die Versandkosten pauschal mit 4,50 € pro Bestellung.</p>
        <p>
            Für die Versandart "Standard Buchversand" berechnen wir die Versandkosten pauschal mit 4,50 € pro
            Bestellung.
        </p>
        <p>
            <strong>Lieferung von Download-Produkten:</strong>
            <br />
            <br />
            Für Download-Produkte (digitale Inhalte, die nicht auf einem körperlichen Datenträger geliefert werden)
            fallen keine Versandkosten an.
            <br />
            <br />
            Download-Produkte können Sie über einen per E-Mail mitgeteilten Link herunterladen.
            <br />
            Die Versendung dieser E-Mail mit Zugangsdaten zur Ausführung des Downloads erfolgt innerhalb von 24 Stunden
            nach Vertragsschluss (bei vereinbarter Vorauszahlung nach dem Zeitpunkt Ihrer Zahlungsanweisung).
            <br />
            <br />
            <strong>Lieferfristen</strong>
            <br />
            <br />
            Soweit im jeweiligen Angebot keine andere Frist angegeben ist, erfolgt die Lieferung der Ware im Inland
            (Deutschland) innerhalb von 2-3 Tagen, bei Auslandslieferungen innerhalb von 2-4 Tagen nach Vertragsschluss
            (bei vereinbarter Vorauszahlung nach dem Zeitpunkt Ihrer Zahlungsanweisung).
            <br />
            Beachten Sie, dass an Sonn- und Feiertagen keine Zustellung erfolgt.
            <br />
            Haben Sie Artikel mit unterschiedlichen Lieferzeiten bestellt, versenden wir die Ware in einer gemeinsamen
            Sendung, sofern wir keine abweichenden Vereinbarungen mit Ihnen getroffen haben. Die Lieferzeit bestimmt
            sich in diesem Fall nach dem Artikel mit der längsten Lieferzeit den Sie bestellt haben.
            <br />
            <br />
            <strong>Akzeptierte Zahlungsmöglichkeiten</strong>
            <br />
            <br />
            - Vorkasse per Überweisung
            <br />
            - Zahlung per PayPal
            <br />
            - Zahlung per PayPal Express
            <br />
            - Zahlung über Stripe (Kreditkarte, Sofortüberweisung, Giropay, Apple Pay)
        </p>
        <p>
            <br />
            <strong>Weitere Einzelheiten zur Zahlung</strong>
            <br />
            <br />
            Bei Zahlung per Kreditkarte erfolgt die Belastung Ihres Kreditkartenkontos mit Vertragsschluss.
            <br />
            <br />
            <br />
            Bei Fragen finden Sie unsere Kontaktdaten im Impressum.
        </p>
    </b-container>
</template>

<script>
export default {
    head() {
        return {
            title: `${this.$route.name.replace(/^\w/, c => c.toUpperCase())} – PC Masterrace`,
        }
    },
}
</script>

<style scoped></style>
