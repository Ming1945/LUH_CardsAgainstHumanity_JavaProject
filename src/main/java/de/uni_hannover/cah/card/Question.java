package de.uni_hannover.cah.card;

/**
 * Every round there is one question/black card.
 * The question/black cards requires one or more answers.
 * The czar decides the best answer/black for the question card.
 */
public class Question extends Card {
    private int requiredAnswers;
    private int id;

    /**
     * Constructor for question/black cards
     * @param t text written on card
     * @param a how many answer/white cards are needed to answer this card
     * @param id id of card
     */
    public Question(String t, int a, int id) {
        super(t);
        this.requiredAnswers = a;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public int getRequiredAnswers() {
        return requiredAnswers;
    }
}
