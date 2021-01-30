package de.uni_hannover.cah.mainGui;


import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.geometry.*;

import java.io.IOException;
/**
 * Controller of the main menu buttons
 */
public class Controller {

    /**
     * Opens SCController. User can choose if he wants to host a server or join a server as a client
     * @param event click on "play" button
     * @throws IOException
     */
    @FXML
    public void playButton(ActionEvent event) throws IOException {

        Parent server_client_parent = FXMLLoader.load(getClass().getClassLoader().getResource("ServerAndClient.fxml"));
        Scene server_client_scene = new Scene (server_client_parent);
        Stage server_client_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        server_client_stage.hide();
        server_client_stage.setScene (server_client_scene);
        server_client_stage.show();

    }


    /**
     * Opens ControllerQuit. User can choose if he wants to exit the program or not.
     * @param event click on the "quit" button
     * @throws IOException
     */
    @FXML
    public void quitButton(ActionEvent event) throws IOException {
        Parent quit_parent = FXMLLoader.load(getClass().getClassLoader().getResource("Quit.fxml"));
        Scene quit_scene = new Scene (quit_parent);
        Stage quit_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        quit_stage.setScene (quit_scene);
        quit_stage.show();

    }

}


