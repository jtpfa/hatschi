<template>
    <main class="container my-5">
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
        <p>Es fallen keine Lieferkosten an.</p>
        <p>
            <strong>Akzeptierte Zahlungsmöglichkeiten</strong>
            <br />
            <br />
            - Kauf auf Rechnung (Klarna)
            <br />
            - Zahlung per PayPal
            <br />
            - Zahlung per Kreditkarte
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
    </main>
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
