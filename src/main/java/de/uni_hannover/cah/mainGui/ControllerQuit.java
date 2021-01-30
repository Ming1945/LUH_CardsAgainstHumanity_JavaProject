package de.uni_hannover.cah.mainGui;


import de.uni_hannover.cah.client.Client;
import javafx.event.ActionEvent;
import javafx.application.Application;
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
 * Controller of the quit button
 */
public class ControllerQuit {
    /**
     * Exits the program
     * @param event click on the "yes" button
     */
    public void yesButton(ActionEvent event) {
        System.exit(0);
    }
}
