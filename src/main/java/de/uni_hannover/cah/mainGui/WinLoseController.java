package de.uni_hannover.cah.mainGui;


import de.uni_hannover.cah.client.Client;
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
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Controller of the victory screen
 */
public class WinLoseController {
    /**
     * Opens quit controller. User may now exit the game.
     * @param event click on the "quit" button
     * @throws IOException
     */
    @FXML
    public void quitButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Quit.fxml"));
        Parent quit_parent = null;
        try {
            quit_parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene quit_scene = new Scene(quit_parent);
        ControllerQuit controller = loader.<ControllerQuit>getController();
        Stage quit_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        quit_stage.hide();
        quit_stage.setScene(quit_scene);
        quit_stage.show();
        quit_stage.setOnCloseRequest((WindowEvent event1) -> {
            System.exit(0);
        });
    }
}