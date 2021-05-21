package sample.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.DatabaseManagers.UserManager;
import sample.home.HomeController;

import java.io.IOException;
import java.sql.*;


public class LoginController {


    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Label lblDisplay;

    @FXML
    AnchorPane ap;

    private UserManager userManager;

    @FXML
    private void initialize(){
        //creates a link to the user table in the database
        userManager = new UserManager();
    }
    @FXML
    private void goToRegisterScreen(ActionEvent event) throws IOException {
        //goes to the screen where registration happens
        Parent parent = FXMLLoader.load(getClass().getResource("registration.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    @FXML
    private void goToHomeScreen(ActionEvent event) throws IOException{
        //goes to home screen if information was accurate
        if(userManager.checkUserInfoRequest(usernameField.getText(), passwordField.getText())){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../home/home.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(scene);

            HomeController homeController = loader.getController();
            homeController.setData(usernameField.getText());

            window.show();


        }
    }


}
