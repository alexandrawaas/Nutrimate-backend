## Beschreibung der benutzten Fremddienste

# Open Food Facts API

Open Food Facts ist eine Online-Datenbank, in der Lebensmittel mithilfe ihres Namens oder Barcodes gesucht werden können und Informationen wie Nährwerte, Allergene oder Umweltauswirkungen des Lebensmittels nachgelesen werden können.

Der Dienst bietet eine kostenfreie API an, mithilfe derer Abfragen aus der Datenbank möglich sind. Diese ist unter https://world.openfoodfacts.org/files/api-documentation.html dokumentiert.

Für unsere Anwendung wurde der Endpunkt `GET https://[countrycode].openfoodfacts.org/api/v0/product/[barcode].json` mit dem countrycode "en" für den englischsprachigen Raum sowie dem jeweiligen Barcode des Produktes genutzt. Die Nutzung erfolgte in den beiden Endpunkten zum Scannen eines Barcodes und zum Speichern eines gescannten Lebensmittels im eigenen Kühlschrank. Bei letzterem Endpunkt werden ausgewählte Teile der Antwort, beispielsweise der Zuckergehalt oder einer der Bild-URLs, in unsere Datenbank übernommen. Als Kategorie, welche für die Suche nach Rezepten in der Edamam-Datenbank genutzt wird, wurde jeweils der letzte und somit spezifischste Eintrag im Array CategoryTags genutzt, damit ein allgemeiner Name des Lebensmittels zur Suche verwendet werden kann.

Da Open Food Facts eine offen bearbeitbare Datenbank ist, gibt es zahlreiche unvollständige und auch fehlerhafte Einträge. Es wurde sich dennoch für Open Food Facts entschieden, da die Datenbank vollkommen kostenlos ist und sich die Fehler bei den meisten Produkten in Grenzen halten. Aus diesen Gründen wurden als Allergene auch nur die 14 wichtigsten Allergene, die von Edamam und Open Food Facts unterstützt werden, in die App aufgenommen.

# Edamam API

Edamam ist eine Datenbank mit APIs, die zahlreiche Schnittstellen im Zusammenhang mit Lebensmitteln anbietet. Sie lässt sich nur mit API-Key und App-Code nutzen, weshalb wir uns dort registrieren mussten. 

Die Dokumentation der für unser Projekt verwendeten Recipe Search API ist zu finden unter https://developer.edamam.com/edamam-docs-recipe-api .
In unserem Backend wird der Endpunkt `GET /api/recipes/v2` mit den Query-Parametern q (Suchbegriffe) und health (Allergene) aufgerufen (sowie den verpflichtenden Parametern app-key und app-id). Hierdurch wird nach Rezepten gesucht, die bestimmte Zutaten enthalten und bestimmte Allergene nicht enthalten. Die Zutaten-Suche wird mithilfe des zuvor erwähnten Category-Attributs umgesetzt.

Edamam bietet eine sehr umfangreiche Rezeptsammlung an. Schwierigkeiten verursachten die nicht ganz mit Open Food Facts übereinstimmenden Allergen-Namen, die in unserem Backend daher umgewandelt werden mussten. Zudem stellt die Suche mithilfe der Open Food Facts Category noch ein Problem dar, denn bei zu spezifischen Kategorien (z.B. "chocolate and hazelnut spread" für Nutella) kann kein passendes Rezept gefunden werden. Hier wäre für die reale Umsetzung der App eine Lösung mit Einbindung einer KI denkbar.