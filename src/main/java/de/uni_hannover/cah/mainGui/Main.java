package de.uni_hannover.cah.mainGui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    Stage mainMenuWindow;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainMenuWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        mainMenuWindow.setTitle("Cards Against Humanity");
        mainMenuWindow.setScene(new Scene(root, 857, 653));
        mainMenuWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
