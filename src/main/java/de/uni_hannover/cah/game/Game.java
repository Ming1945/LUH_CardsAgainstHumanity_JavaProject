package de.uni_hannover.cah.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.uni_hannover.cah.card.Answer;
import de.uni_hannover.cah.card.Question;
import de.uni_hannover.cah.mainGui.GameplayController;
import de.uni_hannover.cah.message.Message;
import de.uni_hannover.cah.observer.SimpleObserver;
import de.uni_hannover.cah.parser.Jsonparser;
import de.uni_hannover.cah.player.Player;
import de.uni_hannover.cah.server.Server;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * class for the game. every meta information is stored here as well as most of the functions for the game logic.
 */
public class Game implements SimpleObserver {
    private int max_points;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Server server;
    private ArrayList<Answer> answerDeck;
    private ArrayList<Question> questionDeck;
    private HashMap<String, Answer[]> allAnswers;
    private Player czar;
    private Label playerList;

    public Game(Server server, final int mp) {
        this.max_points = mp;
        this.server = server;
        server.setObserver(this);
        this.answerDeck = Jsonparser.getAnswerDeck();
        this.questionDeck = Jsonparser.getQuestionDeck();
        this.allAnswers = new HashMap<>();
    }

    public void startGame() {
        startCzar();
        questionDeck = Jsonparser.getQuestionDeck();
        answerDeck = Jsonparser.getAnswerDeck();
        sendNewQuestionCard();
        checkPlayersHandCards();
        sendScoreToPlayers();
        Message msg = new Message("start",null, "");
        try {
            server.send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkPlayersHandCards() {
        for(int i = 0; i < players.size(); i++) {
            fillHandCards(players.get(i).getHandCards(), players.get(i).getName());
        }
    }

    private void fillHandCards(Answer[] answers, String playerID) {
        for(int i = 0; i < answers.length; i++) {
            if(answers[i] == null) {
                Answer newAnswerCard = getRandomAnswer();
                answers[i] = newAnswerCard;
                answerDeck.remove(newAnswerCard);
            }
        }
        sendHandCards(answers, playerID);
    }

    private void sendHandCards(Answer[] answers, String playerID) {
        Message msg = new Message("handcards", answers, playerID);
        try {
            server.send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Answer getRandomAnswer() {
        int randomInt = ThreadLocalRandom.current().nextInt(0, answerDeck.size());
        Answer ans = answerDeck.get(randomInt);
        answerDeck.remove(ans);
        return ans;
    }

    public void setplayerListLabel(Label list) {
        playerList = list;
    }

    private void updatePlayerListLabel(){
        String playerNames = "";
        for(Player p : players) {
            playerNames += p.getName() + "\n";
        }
        String finalPlayerNames = playerNames;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                playerList.setText(finalPlayerNames);
            }
        });
    }


    private void startCzar(){
        int randomInt = ThreadLocalRandom.current().nextInt(0, players.size());
        this.czar = players.get(randomInt);
        sendCzar(this.czar);
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void setMaxScore(int score) {
        this.max_points = score;
    }


    @Override
    public void messageReceived(String msg) {
        Gson gson = new Gson();
        Message<Object> message = gson.fromJson(msg, Message.class);
        switch (message.type) {
            case "answerCard":          //Karte wird an Spieler versendet
                message = gson.fromJson(msg, new TypeToken<Message<Answer[]>>(){}.getType());
                this.answerCardReceived(message.playerID, (Answer[]) message.payload);
                break;
            case "chosenCard":
                this.chosenCardReceived(message.playerID);
                break;
            case "player":
                message = gson.fromJson(msg, new TypeToken<Message<Player>>(){}.getType());
                this.addPlayer((Player) message.payload);
                updatePlayerListLabel();
                break;
        }
    }

    private void chosenCardReceived(String playerID) {
        Answer[] answer = allAnswers.get(playerID);
        sendCzarsChosenCardToPlayer(playerID, answer);
        Player winner = null;
        for(Player p: players) {
            if(playerID.equals(p.getName())) {
                winner = p;
            }
        }
        if (winner == null) { return; }
        winner.addScore();
        sendScoreToPlayers();
        newRound(winner);
    }

    private void newRound(Player newCzar) {
        allAnswers.clear();
        checkScore();
        if(this.czar != newCzar) {
            sendCzar(newCzar);
            sendCzar(this.czar);
        }
        this.czar.setCzar();
        this.czar = newCzar;
        sendNewQuestionCard();
        checkPlayersHandCards();
        sendNewRound();
    }

    private void sendNewRound() {
        Message msg = new Message("NewRound", "", "");
        try {
            server.send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCzar(Player czar) {
        Message msg = new Message("Czar", "", czar.getName());
        try {
            server.send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkScore() {
        for(Player p: players){
            if(p.getScore() >= max_points) {
                EndOfGame(p.getName(), p.getScore());
            }
        }
    }

    private void EndOfGame(String winner, Integer score) {
        Message msg = new Message("End", score, winner);
        try {
            server.send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendNewQuestionCard() {
        Question qst = getRandomQuestion();
        Message msg = new Message("question", qst, "");
        try {
            server.send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        questionDeck.remove(qst);
    }

    private Question getRandomQuestion() {
        int randomInt = ThreadLocalRandom.current().nextInt(0, questionDeck.size());
        Question qst = questionDeck.get(randomInt);
        questionDeck.remove(qst);
        return qst;
    }

    private void sendScoreToPlayers() {
        for(Player p : players) {
            Message msg = new Message("score", p.getScore(), p.getName());
            try {
                server.send(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendCzarsChosenCardToPlayer(String playerID, Answer[] answer) {
        Message msg = new Message("chosenCard", answer, playerID);
        try {
            server.send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void answerCardReceived(String playerID, Answer[] answer) {
        for(int i = 0; i < players.size(); i++) {
            for(Answer a: answer) {
                for(int j = 0; j < players.get(i).getHandCards().length; j++) {
                    if(players.get(i).getHandCards()[j] != null && a.getText().equals(players.get(i).getHandCards()[j].getText())) {
                        players.get(i).getHandCards()[j] = null;
                    }
                }
            }
        }
        allAnswers.put(playerID,answer);
        if(allAnswers.size() >= players.size()-1) {
            sendPlayerAnswerCardsToCzar();
        }

    }

    private void sendPlayerAnswerCardsToCzar() {
        Message msg = new Message("answersToCzar", allAnswers, "" );
        try {
            server.send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
