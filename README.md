# Party & Friends - BungeeCord & Spigot

Ein leistungsstarkes und flexibles Party- und Freundesystem für BungeeCord und Spigot!

## Features ✨
- Freundesystem mit Anfragen, Annahmen und Blockier-Funktion
- Partysystem mit Einladungen, Beitritt und Verlassen
- Unterstützung für mehrere Server (BungeeCord-kompatibel)
- Konfigurierbare Nachrichten und Einstellungen
- Datenbank-Unterstützung (MySQL)
- Umfangreiche API für Entwickler

## Installation 📥
1. Lade das Plugin herunter und speichere es im `plugins`-Ordner deines BungeeCord- oder Spigot-Servers.
2. Starte den Server neu, um die Konfigurationsdateien generieren zu lassen.
3. Passe die Einstellungen in der `config.yml` an.
4. Starte den Server erneut, und das Plugin ist einsatzbereit!

## Befehle 📜
| Befehl | Beschreibung |
|--------|-------------|
| `/friend add <Spieler>` | Freundesanfrage senden |
| `/friend remove <Spieler>` | Freund entfernen |
| `/party create` | Neue Party erstellen |
| `/party invite <Spieler>` | Spieler in Party einladen |
| `/party leave` | Party verlassen |

## Berechtigungen 🔑
| Permission | Beschreibung |
|------------|-------------|
| `partyandfriends.friend` | Zugriff auf das Freundesystem |
| `partyandfriends.party` | Zugriff auf das Partysystem |
| `partyandfriends.admin` | Admin-Funktionen nutzen |

## Datenbank 🎲
Das Plugin nutzt MySQL zur Speicherung von Freundes- und Party-Daten. Stelle sicher, dass dein MySQL-Server läuft und die Zugangsdaten in der `config.yml` korrekt eingetragen sind.

## Unterstützung 🛠️
Falls du Hilfe benötigst, kannst du ein Issue auf GitHub erstellen oder dich mit der Community austauschen.

🔗 [GitHub-Repository](https://github.com/crackscout123/PartyAndFriends)
