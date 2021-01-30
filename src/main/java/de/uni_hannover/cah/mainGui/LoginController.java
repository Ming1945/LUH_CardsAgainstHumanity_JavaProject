package de.uni_hannover.cah.mainGui;

import de.uni_hannover.cah.client.Client;
import de.uni_hannover.cah.game.ClientGame;
import de.uni_hannover.cah.player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
/**
 * Controller of the client login window
 */
public class LoginController {

    public TextField textfieldIP;
    public Label errorLabel;
    public TextField textFieldName;

    /**
     * User may enter a name and an ip address in order to join a game. After clicking on the button the gameplaycontroller
     * will open and the user has entered the game. Also the playedcardscontroller will open and be visible for the user.
     * @param event click on the "connect" button
     * @throws IOException
     */
    @FXML
    public void connectButton(ActionEvent event) throws IOException {
        if(!textfieldIP.getText().equals("") && !textFieldName.getText().equals("")) {
            Client client = new Client(textfieldIP.getText());
            if(client.getConnected()) {
                Player p = new Player(textFieldName.getText());
                ClientGame clientgame = new ClientGame(client, p);
                client.listen();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Gameplay.fxml"));
                Parent game_parent = loader.load();
                Scene game_scene = new Scene (game_parent);
                GameplayController controller = loader.<GameplayController>getController();
                controller.setClientGame(clientgame);
                clientgame.sendPlayer();
                clientgame.setGameplayController(controller);
                Stage game_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                game_stage.setX(game_stage.getX() - game_stage.getWidth()/2);
                game_stage.hide();
                game_stage.setScene (game_scene);
                game_stage.show();
                clientgame.setView(game_parent);
                game_parent.setDisable(true);
                game_stage.setOnCloseRequest((WindowEvent event1) -> {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                });
                FXMLLoader loaderP = new FXMLLoader(getClass().getClassLoader().getResource("playedcards.fxml"));
                Parent playedCardsParent = loaderP.load();
                Scene playedCardsScene= new Scene (playedCardsParent);
                PlayedcardsController controllerP = loaderP.<PlayedcardsController>getController();
                controllerP.setClientGame(clientgame);
                double windowGap = 2;
                Stage playedCardsStage = new Stage();
                playedCardsStage.setX(game_stage.getX() + game_stage.getWidth() + windowGap);
                playedCardsStage.setY(game_stage.getY());
                playedCardsStage.hide();
                playedCardsStage.setScene (playedCardsScene);
                playedCardsStage.show();
                playedCardsStage.setOnCloseRequest((WindowEvent event2) -> {
                    try{
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                });
                controllerP.setActive(false);
                controller.setPlayedcardsController(controllerP);
            }
        } else {
            errorLabel.setVisible(true);
        }
    }

    //to be deleted
    public void TestConnectionButton(ActionEvent event) {

    }

}
