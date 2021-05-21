package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.login.LoginController;
import java.sql.*;

public class Main extends Application {

    @FXML
    Button twoToOne;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login/login.fxml"));
        primaryStage.setTitle("Recursion");
        primaryStage.setScene(new Scene(root, 1000   , 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
