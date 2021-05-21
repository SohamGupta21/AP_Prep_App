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
import javafx.stage.Stage;
import sample.DatabaseManagers.UserManager;
import sample.home.HomeController;

import java.io.IOException;

public class RegistrationController {

    @FXML
    TextField possUsername;
    @FXML
    PasswordField possPass, possPass2;
    @FXML
    Label registrationNotifier;

    private UserManager userManager;

    @FXML
    private void initialize(){
        //gets link to user table in the database
        userManager = new UserManager();
    }
    @FXML
    private void goBackToLogin(ActionEvent event) throws IOException{
        //takes back to the login screen
        Parent parent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    @FXML
    private void goToHome(ActionEvent event) throws IOException {
        //goes to the home screen page and passes the necessary data
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../home/home.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        HomeController homeController = loader.getController();
        homeController.setData(possUsername.getText());
        window.show();
    }
    @FXML
    private void tryToRegister(ActionEvent event) throws IOException{
        //tries to register with and acccount
        //if user input was valid
        if(!possUsername.getText().equals(null) && !possPass.getText().equals(null) &&!possPass2.getText().equals(null)){
            if(possPass.getText().equals(possPass2.getText())){
                //if passwords match
                if(!userManager.checkUserInfoRequest(possUsername.getText(), possPass.getText())){
                    //if a user doesnt already exist then creates a new user with this information
                    userManager.databaseRegistration(possUsername.getText(), possPass.getText());
                    goToHome(event);
                }else {
                    registrationNotifier.setVisible(true);
                    registrationNotifier.setText("Those are not valid credentials");
                }
            }else {
                registrationNotifier.setVisible(true);
                registrationNotifier.setText("Passwords must match");
            }
        } else {
            registrationNotifier.setVisible(true);
            registrationNotifier.setText("Please fill out all of the forms");
        }
    }
}
