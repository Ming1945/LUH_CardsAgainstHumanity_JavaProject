package de.uni_hannover.cah.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.uni_hannover.cah.card.Answer;
import de.uni_hannover.cah.card.Question;
import de.uni_hannover.cah.client.Client;
import de.uni_hannover.cah.mainGui.GameplayController;
import de.uni_hannover.cah.mainGui.WinLoseController;
import de.uni_hannover.cah.message.Message;
import de.uni_hannover.cah.observer.SimpleObserver;
import de.uni_hannover.cah.player.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.HashMap;

/**
 * client game class
 */
public class ClientGame implements SimpleObserver {
    private Client client;
    private Player player;
    private Answer winnerCards[];
    private HashMap<String, Answer[]> allAnswers = new HashMap<>();
    private Parent gameParent;
    private GameplayController gameplayController;

    public ClientGame(Client client, Player player) {
        this.client = client;
        this.player = player;
        this.client.setObserver(this);
    }


    @Override
    public void messageReceived(String msg) {
        Gson gson = new Gson();
        Message<Object> message = gson.fromJson(msg, Message.class);
        switch (message.type) {
            case "question":
                message = gson.fromJson(msg, new TypeToken<Message<Question>>(){}.getType());
                player.setQuestion((Question) message.payload);
                player.setPlayedCardsArray();
                break;
            case "score":
                message = gson.fromJson(msg, new TypeToken<Message<Integer>>(){}.getType());
                player.setScoreList((Integer) message.payload, message.playerID);
                gameplayController.setScore();
                break;
            case "chosenCard":
                message = gson.fromJson(msg, new TypeToken<Message<Answer[]>>(){}.getType());
                winnerCards = (Answer[]) message.payload;
                allAnswers.clear();
                allAnswers.put(message.playerID, (Answer[]) message.payload);
                gameplayController.getPlayedcardsController().setAnswers(allAnswers);
                break;
            case "answersToCzar":
                message = gson.fromJson(msg, new TypeToken<Message<HashMap<String, Answer[]>>>(){}.getType());
                showCzarCards((HashMap<String, Answer[]>) message.payload);
                break;
            case "start":
                newRound();
                break;
            case "handcards":
                message = gson.fromJson(msg, new TypeToken<Message<Answer[]>>(){}.getType());
                if(message.playerID.equals(this.player.getName())) {
                    this.player.setHandcards((Answer[]) message.payload);
                }
                break;
            case "End":
                message = gson.fromJson(msg, new TypeToken<Message<Integer>>(){}.getType());
                End(message.playerID, message.payload);
                break;
            case "Czar":
                if(message.playerID.equals(player.getName())){
                    player.setCzar();
                }
                break;
            case "NewRound":
                newRound();
                break;
        }
    }


    private void newRound() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameParent.setDisable(false);
                gameplayController.gridpaneID.setDisable(false);
                gameplayController.platform.setDisable(false);
                gameplayController.getPlayedcardsController().questionLabelID.setText(player.getQuestion().getText());
                for(int i = 0; i < player.getHandCards().length; i++) {
                    ((Button) gameParent.lookup("#btn" + (i + 1))).setText(player.getHandCards()[i].getText()); //Iteration über Buttons, Text wird durch Handarray des Spielers gesetzt
                    ((Label) gameParent.lookup("#Black")).setText(player.getQuestion().getText()); //Sets Questionlabel

                }
                gameplayController.PlayedCardsLabel.setText("Your played Cards:");
                gameplayController.getPlayedcardsController().setActive(false);
                if(player.getCzar()) {
                    gameplayController.PlayedCardsLabel.setText("You're the czar, wait for the chosen cards");
                    gameParent.setDisable(true);
                    gameplayController.getPlayedcardsController().setCzarsButtonsActive(true);
                }
            }
        });
    }

    @FXML
    private void End(String playerID, Object payload) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (player.getName().equals(playerID)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("YouWin.fxml"));
                    Parent server_client_parent = null;
                    try {
                        server_client_parent = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene server_client_scene = new Scene(server_client_parent);
                    WinLoseController controller = loader.<WinLoseController>getController();
                    Stage server_client_stage = (Stage) gameParent.getScene().getWindow();
                    server_client_stage.hide();
                    server_client_stage.setScene(server_client_scene);
                    server_client_stage.show();
                    server_client_stage.setOnCloseRequest((WindowEvent event1) -> {
                        System.exit(0);
                    });
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("YouLose.fxml"));
                    Parent server_client_parent = null;
                    try {
                        server_client_parent = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene server_client_scene = new Scene(server_client_parent);
                    WinLoseController controller = loader.<WinLoseController>getController();
                    Stage server_client_stage = (Stage) gameParent.getScene().getWindow();
                    server_client_stage.hide();
                    server_client_stage.setScene(server_client_scene);
                    server_client_stage.show();
                    server_client_stage.setOnCloseRequest((WindowEvent event1) -> {
                        System.exit(0);
                    });
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    public void sendPlayer() {
        Message<Player> msg = new Message<>("player", player, player.getName());
        try {
            client.send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer(){
        return this.player;
    }

    public void sendAnswers() {
        Message msg = new Message("answerCard", player.getPlayedCards(), player.getName());
        try {
            client.send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void sendChosenCard(String playerID) {
        Message msg = new Message("chosenCard", null, playerID);
        try {
            client.send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCzarCards(HashMap<String, Answer[]> allAnswers) {
            this.allAnswers = allAnswers;
            gameplayController.getPlayedcardsController().setAnswers(allAnswers);
    }

    public void setView(Parent game_parent) {
        this.gameParent = game_parent;
    }

    public HashMap<String,Answer[]> getAllAnswers() {
        return allAnswers;
    }

    public void setGameplayController(GameplayController gameplayController) {
        this.gameplayController = gameplayController;
    }

}
