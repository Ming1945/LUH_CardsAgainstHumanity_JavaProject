# CAH - Aufgabenstellung

Vielen Dank, dass ihr euch für unser _________ Gruppenprojekt entschieden habt! Um euch dieses Projekt so ________ wie möglich zu gestalten, bekommt ihr alle eine Aufgabenstellung.


# Allgemeines

Der Projektaufbau sieht grob eine **Serveranwendung** und eine **Clientanwendung** vor. Beide haben eine **GUI in JavaFX**. Spielen könnt ihr im lokalen Netzwerk mit mehreren Geräten. Zum Testen könnt ihr die Anwendungen aber auch mehrmals lokal starten, hierfür kann das Loopback-Interace (localhost, 127.0.0.1) verwendet werden.
CAH Fragen liegen als JSON Datei vor. Diese könnt ihr euch unter https://github.com/crhallberg/json-against-humanity herunterladen. 
Als IDE verwenden wir **IntelliJ IDEA Community Edition**. Das Projekt soll als Buildsystem von Maven verwenden, IntelliJ hat eine gute Integration dafür.

# Wochenplan

**Start: 14.6 / 15.6**
**Ende: 13.7**

## Woche 1
- UML Diagramm
- Setup IDE und Projekt im GIT (e.g. HelloWord, das gilt für ALLE Gruppenmitglieder)
- Dependencies über Maven (e.g. mit Maven vertraut machen, Jackson Faster XML oder GSON ziehen)

## Woche 2
- UI für Server und Client mit SceneBuilder entwerfen.
- Verbindung zwischen Server und Clients (Ihr könnt Strings senden und empfangen)
- Kartenmodell erstellen
- Nachrichtenmodell für Server und Client
- JSON parsen

## Woche 3
- Spielregeln umsetzen
- UI größtenteils fertig
- Einstellungen
- Server-Client Kommunikation mit Spielregeln steht

## Woche 4
- UI komplett fertig und skalierbar.
- CAH ist spielbar
- Zusatzaufgabe

## Woche 5
- Bugs fixen
- Planung der Präsentation
- Zusatzaufgabe

## Woche 6
- Vorstellung der Ergebnisse

# Zusatz

Folgende Zusatzaufgaben stehen für _______ zur Verfügung.

 - Eigene Fragenpakete erstellen und sie in der JSON Datei sichern.
	 - Mit UI-Editor
 - Text-to-Speech liest Fragen und Antworten vor.
	 - Ihr könnt dafür Mary http://mary.dfki.de/
 - Am Server können unterschiedliche Spielmodi eingestellt werden.

 
# Regeln
## Basic Rules
To start the game, each player draws ten White Cards.

One randomly chosen player begins as the Card Czar and plays a Black Card. The Card Czar reads the question or fill-in-the-blank phrase on the Black Card out loud.

Everyone else answers the question or fills in the blank by passing one White Card, face down, to the Card Czar.

The Card Czar shuffles all of the answers and shares each card combination with the group. For full effect, the Card Czar should usually re-read the Black Card before presenting each answer. The Card Czar then picks a favorite, and whoever played that answer keeps the Black Card as one Awesome Point.

After the round, a new player becomes the Card Czar, and everyone draws back up to ten White Cards.

### Pick 2
Some cards say PICK 2 on the bottom.

To answer these, each player plays two White Cards in combination. Play them in the order that the Card Czar should read them— the order matters.

If the Card Czar has lobster claws for hands, you can use paperclips to secure the cards in the right order.

### Gambling
If a Black Card is played and you have more than one White Card that you think could win, you can bet one of your Awesome Points to play an additional White Card.

If you win, you keep your point. If you lose, whoever won the round gets the point you wagered.

## House Rules
Cards Against Humanity is meant to be remixed. Here are some of our favorite ways to pimp out the rules:

Happy Ending: When you're ready to stop playing, play the "Make a Haiku" Black Card to end the game. This is the official ceremonial ending of a good game of Cards Against Humanity, and this card should be reserved for the end. (Note: Haikus don't need to follow the 5-7-5 form. They just have to be read dramatically).

Rebooting the Universe: At any time, players may trade in an Awesome Point to return as many White Cards as they'd like to the deck and draw back up to ten.

Packing Heat: For Pick 2s, all players draw an extra card before playing the hand to open up more options.

Rando Cardrissian: Every round, pick one random White Card from the pile and place it into play. This card belongs to an imaginary player named Rando Cardrissian, and if he wins the game, all players go home in a state of everlasting shame.

God Is Dead: Play without a Card Czar. Each player picks his or her favorite card each round. The card with the most votes wins the round.

Survival of the Fittest: After everyone has answered the question, players take turns eliminating one card each. The last remaining card is declared the funniest.

Serious Business: Instead of picking a favorite card each round, the Card Czar ranks the top three in order. The best card gets 3 Awesome Points, the second-best gets 2, and the third gets 1 . Keep a running tally of the score, and at the end of the game, the winner is declared the funniest, mathematically speaking.

Never Have I Ever: At any time, players may discard cards that they don't understand, but they must confess their ignorance to the group and suffer the resulting humiliation.
