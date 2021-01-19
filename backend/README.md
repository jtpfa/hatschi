# PC-MR Backend Dokumentation

### Weitere Dokumente
[JavaDoc](./docs/javadoc/index.html)

[REST-API-Docs](./docs/rest-api/index.html)


### Projekt einrichten

1. Docker und Compose herunterladen und installieren.
3. OpenJDK 11 (oder AdoptOpenJDK 11) herunterladen und installieren.
2. Ggf. Umgebungvariablen in der `./backend/.env` Datei bearbeiten (Standardwerte sollten funktionieren).

#### Backend von einer Java-IDE ausführen (für Backend-Entwickler)
1. Das Projekt kann über den Gradle-Import einer Java-IDE importiert werden. Empfohlen dafür wird IntelliJ IDEA Ultimate.
   Das Ursprungsverzeichnis des Backend ist das `./backend` Verzechnis.
2. Ein neues Terminal im `./backend` Verzeichnis öffnen.
   Die Applikation ist von zwei weiteren Containern abhängig:
   die MariaDB Datenbank und der Keycloak Identitätsserver.
   Beide Container werden über `$ docker-compose -f docker-compose.dev.yml up` gestartet.
   Gestoppt werden können die Container mit `CTRL + C`.
   Alternativ lassen sich die Container auch mit `$ docker-compose -f docker-compose.dev.yml up -d`
   im detached modus starten und mit `$ docker-compose -f docker-compose.dev.yml stop` wieder stoppen.
3. Es muss sichergestellt werden, dass die Laufzeitumgebung der Applikation über die nötigen Umgebungsvariablen besitzt.
   Da die Anwendung direkt von der IDE und nicht in einem Docker-Container gestartet wird, müssen entweder die notwendigen
   Umgebungsvariablen systemweit gesetzt werden oder aber bei jedem Start z. B. über das [EnvFile-Plugin (IntelliJ)](https://plugins.jetbrains.com/plugin/7861-envfile) aus der `.env` Datei geladen werden.
   [Mögliche Lösung für Eclipse (nicht getestet).](https://help.eclipse.org/2020-09/index.jsp?topic=%2Forg.eclipse.cdt.doc.user%2Ftasks%2Fcdt_t_run_env.htm)


> **_Hinweis:_**  Sollte der Keycloak-Container nicht korrekt starten und sich in einer endlosen Neustart-Schleife befinden müssen
> in der `docker-compose.dev.yml` die Zeilen 33 und 34 (KEYCLOAK_USER und KEYCLOAK_PASSWORD) mit `#` auskommentiert werden. 
> Danach mit `$ docker-compose -f docker-compose.dev.yml stop` bzw `CTRL + C` die Container stoppen und anschließend wieder hochfahren.
> Falls der Zugang zum Keycloak-Admin-Panel benötigt wird, anschließend die Zeilen wieder einkommentieren und noch einmal neustarten.
> Dies ist leider einem Bug des Keycloak-Containers geschuldet worauf wir keinen Einfluss haben. Mehr dazu im
> [Keycloak Bugtracker](https://issues.redhat.com/browse/KEYCLOAK-13094) und auf 
> [Stackoverflow](https://stackoverflow.com/questions/59599620/keycloak-8-user-with-username-admin-already-added).
 
> **_Hinweis:_**  Dieses Vorgehen wurde ausschließlich mit IntelliJ IDEA Ultimate 2020 auf Unix- und Linux-basierten Betriebssystemen getestet.
> Andere IDEs wie Eclipse und andere Betriebssysteme wie Microsoft Windows sollten auch funktionieren wurden jedoch nicht explizit getestet.

> **_Hinweis:_** Dieses Vorgehen wird nicht empfohlen, um Backend und Frontend zusammen zu betreiben.

#### Backend direkt als Docker-Container ausführen (für Frontend-Entwickler)
1. Ein neues Terminal im `./backend` Verzeichnis öffnen. Mit `$ docker-compose build` wird aus dem Quellcode
   ein neues Docker-Image gebaut.
2. Die Container werden über `$ docker-compose up` gestartet.
   Gestoppt werden können die Container mit `CTRL + C`.
   Alternativ lassen sich die Container auch mit `$ docker-compose up -d`
   im detached modus starten und mit `$ docker-compose stop` wieder stoppen.
3. Keycloak hat einen Sicherheitsmechanismus implementiert, der dazu führt, dass nicht über unterschiedliche Hostnames
   auf den Keycloak zugegriffen werden darf. Damit in der Host-Umgebung und innerhalb der Docker-Umgebung (Docker-Network)
   die gleichen Hostnames verwendet werden können, muss in der `hosts` Datei des Betriebssystems folgender Eintrag vorgenommen
   werden `127.0.0.1 auth.pcmr.de`  (sic!). Mehr dazu auf [Stackoverflow](https://stackoverflow.com/questions/57213611/keycloak-and-spring-boot-web-app-in-dockerized-environment?rq=1).

> **_Hinweis:_**  Sollte der Keycloak-Container nicht korrekt starten und sich in einer endlosen Neustart-Schleife befinden müssen
> in der `docker-compose.yml` die Zeilen 45 und 46 (KEYCLOAK_USER und KEYCLOAK_PASSWORD) mit `#` auskommentiert werden. 
> Danach mit `$ docker-compose stop` bzw `CTRL + C` die Container stoppen und anschließend wieder hochfahren.
> Falls der Zugang zum Keycloak-Admin-Panel benötigt wird, anschließend die Zeilen wieder einkommentieren und noch einmal neustarten.
> Dies ist leider einem Bug des Keycloak-Containers geschuldet worauf wir keinen Einfluss haben. Mehr dazu im
> [Keycloak Bugtracker](https://issues.redhat.com/browse/KEYCLOAK-13094) und auf 
> [Stackoverflow](https://stackoverflow.com/questions/59599620/keycloak-8-user-with-username-admin-already-added).
 
> **_Hinweis:_** Auch dieses Vorgehen wurde nur auf Unix- und Linux-basierten Betriebssystemen getestet.
> Auch hier sollte Windows dennoch funktionieren.


#### Testdaten für das manuelle Testen
Es gibt zwei verschiedene Möglichkeiten Testdaten zu generieren:
1. `@Disabled` Annotation in JUnit-Test `de.pcmr.shop.db.CreateTestData` auskommentieren und ausführen.
2. SQL-Files in `./backend/src/test/resources/sql` direkt in die Datenbank importieren.
