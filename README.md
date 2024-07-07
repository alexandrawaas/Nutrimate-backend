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

Optional: 

Falls Sie die Endpunkte mit Curl o.ä. testen wollen, können Sie die Google-Authentifizierung deaktivieren.

Setzen Sie dazu folgende Umgebungsvariable:

```shell
export DEBUG=true
```

Starten Sie mit folgendem Befehl das Backend.

Es kann etwas dauern, bis das Backend aktiv ist, da Gradle inkl. aller benötigten Abhängigkeiten heruntergeladen werden muss:

```shell
sudo docker compose up
```
