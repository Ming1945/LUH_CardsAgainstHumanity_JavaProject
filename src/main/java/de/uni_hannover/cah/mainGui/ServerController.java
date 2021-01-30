package de.uni_hannover.cah.mainGui;

import de.uni_hannover.cah.game.Game;
import de.uni_hannover.cah.server.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
/**
 * Controller of the server window
 */
public class ServerController {
    @FXML
    public Label lbServer;
    public Button btnStart;
    public TextField Score;
    public Label playerList;
    public Label ScoreLabel;
    public Label maxScore;
    private Server server;
    private Game game;

    public ServerController(){
        server = new Server();
        server.start();
    }

    @FXML
    public void initialize() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        lbServer.setText("IP Address(public):- " + getIP() + "\nIP Adress(private):- " + inetAddress.getHostAddress());
        this.game = new Game(server, 0);
        this.game.setplayerListLabel(playerList);
    }

    /**
     * Server will be closed.
     */
    @FXML
    public void stop() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * User may enter the max score of the game. After the game will be started.
     * @param event click on the "start" button
     */
    @FXML
    public void btnStart(ActionEvent event) {
            if(!Score.getText().equals("")) {
                game.setMaxScore(Integer.valueOf(Score.getText()));
                game.startGame();
                ((Button) event.getSource()).setVisible(false);
                Score.setVisible(false);
                ScoreLabel.setVisible(false);
                maxScore.setText("Ends with " + Score.getText() + " Points");
                maxScore.setVisible(true);
            }
    }

    String getIP(){
        String systemipaddress = "";
        try
        {
            URL url_name = new URL("http://bot.whatismyipaddress.com");

            BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));

            // reads system IPAddress
            systemipaddress = sc.readLine().trim();
        }
        catch (Exception e)
        {
            systemipaddress = "Cannot Execute Properly";
        }
        System.out.println("Public IP Address: " + systemipaddress +"\n");


        return systemipaddress;
    }
}