# pcmr

> PC Masterrace

## Voraussetzung
* Node Version 12.x installiert
* NPM Version 6.x installiert

## Frontend nutzen

``` bash
# Wenn nvm als Node.js Manager (nvm) installiert ist
$ nvm use

# Ansonsten Node-Umgebungsvariable des Betriebssystems
# an den Pfad zur Node12-Installation anpassen

# Pakete installieren
# Es kann zu Warnungen sowie Sicherheitsfehlern kommen...
# Dies hängt davon ab, ob es neue Sicherheitslücken in Paketen gibt oder nicht
# Warnungen zu fehlenden "Peer-dependencies" können ignoriert werden, da diese nicht genutzt werden
# NPM zeigt diese nicht notwendigen Abhängigkeiten allerdings standardmäßig als "missing" an
$ npm ci --prod

# Applikation bauen and Node.js Server starten
$ npm run build
$ npm run start

# Dokumentation erstellen
$ npm run docs
```
