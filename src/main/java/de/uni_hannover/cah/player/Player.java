package de.uni_hannover.cah.player;

import de.uni_hannover.cah.card.Answer;
import de.uni_hannover.cah.card.Question;
import java.util.HashMap;
import java.util.Map;

/*
The player takes part in the game.
Every player has a score and ten answercards in his hand.
One of all players takes the role of the czar in one round.
All other players give their answers to the czar who choses the winner.
The winner will get a pint and become the czar in the next round.
 */
public class Player {
    private String name;
    private int score = 0;
    private boolean winner = false;
    private boolean czar = false;
    private Answer[] handCards = new Answer[10];
    private Question question;
    private Answer[] playedCards;
    private HashMap<String, Integer> scoreList = new HashMap<>();

    public Player (String n) {
        this.name = n;
    }

    public void addScore(){
        score++;
    }

    public void setCzar() {
        czar = !czar;
    }

    public boolean getCzar() { return czar; }

    public boolean isWinner() { return winner; }

    public int getScore() {
        return score;
    }

    public String getName() { return name; }

    public int requiredAnswers() {
        return question.getRequiredAnswers();
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }
    /**
     *Current number of answers the player must choose to give to the czar.
     */
    public void setPlayedCardsArray(){
        this.playedCards = new Answer[requiredAnswers()];
    }

    public Answer[] getHandCards() {
        return handCards;
    }
    /**
     *Choosing an answer from your hand cards
     */
    public void setPlayedCard(Answer answer) {
        int l = playedCards.length;
        int j = -1;
        for (int i= 0; i < l; i++) {
            if (answer == playedCards[i]) {
                playedCards[i] = null;
                break;
            } else if (playedCards[i] == null) {
                playedCards[i] = answer;
                j = i;
                break;
            }
        }
        if (j != -1 && l > 1) {
            for (int i = 0; i < l; i++) {
                if (j == i) {
                    i++;
                    if( i >= l) {
                        break;
                    }
                }
                if (answer == playedCards[i]) {
                    playedCards[i] = null;
                    playedCards[j] = null;
                }
            }
        }

    }
    /**
     *True when the player chose enough answers for the question card
     */
    public boolean playedCardsFull(){
        for(int i = 0; i < playedCards.length; i++){
            if(playedCards[i]==null) {
                return  false;
            }
        }
        return true;
    }


    public Answer[] getPlayedCards() {
        return playedCards;
    }


    /**
     * updates the score
     */

    public void setScoreList(int score , String playerID) {
        scoreList.put(playerID, score);
        if(playerID.equals(this.name)){
            this.score = score;
        }
    }

    public HashMap<String, Integer> getScoreList() {
        return scoreList;
    }
    /**
     * deleting played cards from the card hands to draw new cards
     */
    public void deleteHandCard() {
        for(int i = 0; i < handCards.length; i++) {
            for(int j = 0; j < playedCards.length; j++) {
                if(playedCards[j] == handCards[i]){
                    handCards[i] = null;
                    playedCards[j] = null;
                }
            }
        }
    }

    public void setHandcards(Answer[] answers) {
        this.handCards = answers;
    }
}
