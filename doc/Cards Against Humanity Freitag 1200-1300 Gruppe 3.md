# Gruppenprojekt Cards Against Humanity
Freitag 12:00-13:00 Gruppe 3

Gruppenteilnehmer:
Anthony Fernando Cahyadi
Sven Fechner
Johanna Schlumbohm
Dietrich Schneider

## Aufgabenstellung

Das Kartenspiel "Cards Against Humanity" als onlinefähiges Java-Programm realisieren.

## Wie spielt man Cards Against Humanity?

Bei Cards Against Humanity bekommt jeder Spieler zehn weiße (Antwort)-Karten.
Einer der Spieler wird zu beginn als Czar(Rundenleiter) aufgewählt.
Dieser deckt die eine Schwarze (Frage)-Karte auf und liest diese vor.
Schwarze Karten enthalten eine Frage oder einen Lückentext.
Nun müssen die restlichen Spieler so viele Antwortkarten verdeckt an den Czar geben wie die Schwarze Karte vorgibt.
Der Czar liest alle Antworten laut vor und entscheidet danach welche er am lustigsten fand.
Der Gewinner bekommt die schwarze Karte als Punkt und ist nun der nächste Czar.
Das Spiel endet wenn einer ein gewisses Punktelimit erreicht hat oder eine gewisse Zeit abgelaufen ist.

## Probleme

### GUI 

Das erste Problem bestand darin, wie groß soll/muss das Spielfenster sein, da 10 weiße und 
eine schwarze Karte dargestellt werden muss. Die Karten haben z.T. auch längere Texte, somit darf die 
Darstellung nicht zu klein ausfallen. Da weder Mindestgröße noch Maximalgröße vorgegeben waren, entschieden wir
uns zu einer größeren Auflösung.

### Server-Client Kommunikation

Zu Beginn sollte das Spiel auf dem Server stattfinden und die Clients bekommen nur Befehle zugeteilt.
Das Übertragen der Karten musste sichergestellt werden, deswegen haben wir uns für eine Kommunikation per Messages entschieden.
Abschließend haben wir uns dazu entschieden, dass jeder Client sein "eigenes" Spiel hat und möglichst einfach gehalten wird.
Besonders die Synchronisation von der asynchronen Kommunikation erwies sich als schwierig.

## Benutzung

Um das Spiel auszuführen muss nur die JAR-Datei gestartet werden.
Hier kann man nun auswählen ob man einen Server starten oder sich als Client an einen Server verbinden möchte.

## Einstellungen

Bei der Erstellung des Servers kann die Rundenanzahl angegeben werden, die gespielt werden soll.
