package de.uni_hannover.cah.mainGui;

import de.uni_hannover.cah.card.Answer;
import de.uni_hannover.cah.game.ClientGame;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayedcardsController {
    @FXML
    public Pane paneID;
    @FXML
    public TableColumn<Map.Entry<String, Answer[]>, String> playerNameColumn;
    @FXML
    public TableColumn<Map.Entry<String, Answer[]>, String> playedCardsColumn;
    @FXML
    public TableView<Map.Entry<String, Answer[]>> tableViewID;
    @FXML
    public Button ChooseButtonID;
    @FXML
    public ChoiceBox playerChoiceBoxID;
    @FXML
    public Label labelID;
    @FXML
    public Label questionLabelID;
    private ClientGame clientGame;

    public void setActive(boolean bool) {
        paneID.setDisable(!bool);
    }

    public void setClientGame(ClientGame clientGame) {
        this.clientGame = clientGame;
    }

    @FXML
    public void chooseButton(ActionEvent event) throws IOException {
        if(playerChoiceBoxID.getValue() != null) {
            String winnerName = (String) playerChoiceBoxID.getValue();
            clientGame.sendChosenCard(winnerName);
        }
    }

    public void setCzarsButtonsActive(boolean bool) {
        ChooseButtonID.setVisible(bool);
        playerChoiceBoxID.setVisible(bool);
        if(bool) {
            labelID.setText("Your choice:");
        } else {
            labelID.setText("Czar is choosing");
        }
    }

    public void setAnswers(HashMap<String, Answer[]> allAnswers) {
        setActive(true);
        playerNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Answer[]>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Answer[]>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });

        playedCardsColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Answer[]>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Answer[]>, String> p) {
                String s = "";
                for(Answer a: p.getValue().getValue()) {
                    s += a.getText() + "\n";
                }
                return new SimpleStringProperty(s);
            }
        });
        tableViewID.getItems().setAll(allAnswers.entrySet());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                playerChoiceBoxID.getItems().clear();
                playerChoiceBoxID.getItems().removeAll();
                for(String key: allAnswers.keySet()) {
                    playerChoiceBoxID.getItems().add(key);
                }
            }
        });
    }
    public Label getLabel() {
        return this.labelID;
    }
}
