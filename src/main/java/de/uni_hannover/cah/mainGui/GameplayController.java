package de.uni_hannover.cah.mainGui;


import de.uni_hannover.cah.card.Answer;
import de.uni_hannover.cah.game.ClientGame;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Callback;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/*
Controller of the gameplay window
 */
public class GameplayController {
    public Label PlayedCardsLabel;
    public AnchorPane platform;
    public GridPane gridpaneID;
    private ClientGame clientGame;
    private ArrayList<Button> clickedButtons = new ArrayList<>();
    private PlayedcardsController playedcardsController;

    @FXML
    public TableColumn<Map.Entry<String,Integer>, String> nameColumn;
    @FXML
    public TableColumn<Map.Entry<String, Integer>, String> scoreColumn;
    @FXML
    public TableView<Map.Entry<String, Integer>> scoreTable;

    @FXML
    public void ChooseButton(ActionEvent event) throws IOException {
        if(clientGame.getPlayer().playedCardsFull()) {
            gridpaneID.setDisable(true);
            platform.setDisable(true);
            if(!clientGame.getPlayer().getCzar()) {
                playedcardsController.setCzarsButtonsActive(false);
            }
            clientGame.sendAnswers();
            clientGame.getPlayer().deleteHandCard();
            PlayedCardsLabel.setText("Wait for the other players...");
        }
    }

    @FXML
    public void WhiteCardsButton(ActionEvent event) throws IOException {
        String btnID = ((Button) event.getSource()).getId();
        Button clickedBtn = ((Button) event.getSource());
        if(!clickedButtons.contains(clickedBtn)) {
            clickedButtons.add(clickedBtn);
        } else {
            clickedButtons.remove(clickedBtn);
        }
        Answer[] answers = (Answer[]) (clientGame.getPlayer()).getHandCards();
        int pos = Integer.parseInt(btnID.substring(3));
        Answer answer = answers[pos-1];
        clientGame.getPlayer().setPlayedCard(answer); //setzt Karte als gespielt oder nimmt sie wieder aus den gespielten Karten raus
        PlayedCardsLabel.setText("Your played Cards: ");
        for(int i = 0; i < clientGame.getPlayer().getPlayedCards().length; i++) {
            if(clientGame.getPlayer().getPlayedCards()[i] != null) {
                PlayedCardsLabel.setText(PlayedCardsLabel.getText() + "\n - " + clientGame.getPlayer().getPlayedCards()[i].getText());
            }
        }
    }

    public void setPlayedcardsController(PlayedcardsController playedcardsController) {
        this.playedcardsController = playedcardsController;
    }

    public PlayedcardsController getPlayedcardsController() {
        return this.playedcardsController;
    }

    public void setClientGame(ClientGame clientgame) {
        this.clientGame = clientgame;
    }

    public void setScore() {
        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> param) {
                return new SimpleStringProperty(param.getValue().getKey());
            }
        });
        scoreColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> param) {
                String s = Integer.toString(param.getValue().getValue());
                return new SimpleStringProperty(s);
            }
        });
        scoreTable.getItems().setAll(clientGame.getPlayer().getScoreList().entrySet());
    }


}