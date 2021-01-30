package de.uni_hannover.cah.card;

/**
 * Answer/White cards will be given from every player to the czar.
 * The czar will decide which answer will win the current round
 */
public class Answer extends Card {
    private int id;

    /**
     * Constructor for answer/white cards
     * @param t text written on card
     * @param id id of card
     */
    public Answer(String t, int id) {
        super(t);
        this.text = t;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }
}
