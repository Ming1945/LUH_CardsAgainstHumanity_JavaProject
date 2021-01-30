package de.uni_hannover.cah.card;

/**
 * Cards are played by the players.
 * There are answer cards and question cards.
 * Every cards has a certain text on it.
 */
public class Card {

    /**
     * Text which written on the card
     */
    public String text;

    /**
     * Information about how many answer/white cards are needed to answer the {@code Answer} card
     */
    public int pick;

    /**
     * Constructor for every card
     * @param t contains information about the text which is written on the given card
     */
    public Card(String t) {
        this.text = t;
    }

    /**
     * Constructor for empty cards ("blank cards")
     */
    public Card() {
    }
}