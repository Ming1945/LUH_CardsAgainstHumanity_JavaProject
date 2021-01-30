package de.uni_hannover.cah.mainGui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;

/**
 * Controller of the server client window
 */
public class SCController {

    /**
     * Login controller will open.
     * @param event click on the "client login" button
     * @throws IOException
     */
    @FXML
    public void ClientLoginButton(ActionEvent event) throws IOException {
        Parent client_login_parent = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        Scene client_login_scene = new Scene (client_login_parent);
        Stage client_login_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        client_login_stage.hide();
        client_login_stage.setScene (client_login_scene);
        client_login_stage.show();
        client_login_stage.setOnCloseRequest((WindowEvent event1) -> {
            System.exit(0);
        });

    }

    /**
     * Server controller will open.
     * @param event click on the "server" button.
     * @throws IOException
     */
    @FXML
    public void ServerButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Server.fxml"));
        Parent server_parent = loader.load();
        Scene server_scene = new Scene (server_parent);
        ServerController controller = loader.<ServerController>getController();
        Stage server_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        server_stage.hide();
        server_stage.setScene (server_scene);
        server_stage.show();
        server_stage.setOnCloseRequest((WindowEvent event1) -> {
            controller.stop();
            System.exit(0);
        });

        Parent client_login_parent = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        Scene client_login_scene = new Scene (client_login_parent);
        Stage client_login_stage = new Stage();
        client_login_stage.hide();
        client_login_stage.setScene (client_login_scene);
        client_login_stage.show();
        client_login_stage.setOnCloseRequest((WindowEvent event1) -> {
            System.exit(0);
        });
    }

}
