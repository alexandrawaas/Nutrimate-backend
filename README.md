# Über Nutrimate

Dieses Repository ist Teil der Studienarbeit "Nutrimate" der Gruppe MATCH (Marina Waller, Alexandra Waas, Tim Wagner, Celine Grenz, Hien Le) im Studienmodul RESTful Webservices und enthält das Backend zu unserer App.

Für dieses Modul haben wir die App “NutriMate” entwickelt, die die Prinzipien von REST verfolgt. NutriMate bietet die Möglichkeit, Lebensmittelprodukte effizient zu verwalten und kulinarische Inspirationen zu finden. Der Nutzer kann den Barcode eines gekauften Lebensmittelprodukts scannen, woraufhin das Produkt in seinem digitalen Kühlschrank gespeichert wird. Sobald der Barcode gescannt ist, kann der Nutzer die detaillierten Informationen (Nährwertangaben, Allergene und weitere wichtige Details) zum Produkt abrufen. Zusätzlich bietet unsere App eine Funktion zur Rezeptempfehlung. Basierend auf im Kühlschrank gespeicherten Produkten werden passende Kochideen (Rezepte) angezeigt. Die App arbeitet entsprechend den Anforderungen der Studienarbeit mit zwei  externen APIs, nämlich [Open Food Facts](https://world.openfoodfacts.org/) und [Edamam](https://www.edamam.com/).

Die vollständige Dokumentation unserer Studienarbeit befindet sich im Verzeichnis [documentation](https://gitlab.hof-university.de/awaas/nutrimate-backend/-/tree/main/documentation?ref_type=heads) dieses Repositorys.

# Anleitung zur Installation des Spring-Backends auf Ubuntu

### Schritt 1: Docker installieren

Um unser Backend zu starten, benötigen Sie Docker.

Führen Sie zuerst folgendes aus, um den GPG-Schlüssel von Docker hinzuzufügen:

```shell
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc
```

Fügen Sie danach Docker zu den APT-Paketen hinzu:

```shell
echo \
"deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
$(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
```

Installieren Sie Docker über die APT-Paketverwaltung:

```shell
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

Testen Sie anschließend, ob Docker auf Ihrem Ubuntu-System ordnungsgemäß funktioniert:

```shell
sudo docker run hello-world
```

<br/>

### Schritt 2: Spring-Backend von GitLab herunterladen

Klonen Sie unser Backend von GitLab. 

Sie müssen sich möglicherweise mit Benutzername und Zugriffstoken authentifizieren:

```shell
git clone https://gitlab.hof-university.de/awaas/nutrimate-backend.git
```

Wechseln Sie zu dem Ordner des Repositorys:

```shell
cd nutrimate-backend
```

Starten Sie mit folgendem Befehl das Backend.

Es kann etwas dauern, bis das Backend aktiv ist, da Gradle inkl. aller benötigten Abhängigkeiten heruntergeladen werden muss:

```shell
sudo docker compose up
```

Optional:

Falls Sie die Endpunkte mit curl, Postman o.ä. testen wollen, können Sie mit einer Umgebungsvariable die Google-Authentifizierung deaktivieren:

```shell
export DEBUG=true
sudo -E docker compose up
```
