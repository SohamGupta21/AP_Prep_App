package sample.classmate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DatabaseManagers.UserManager;
import sample.Objects.User;
import sample.home.HomeController;

import java.io.IOException;
import java.util.ArrayList;

public class AddClassmateController {

    private UserManager userManager = new UserManager();

    private User user;
    private ArrayList<String> names = new ArrayList<>();
    //allows the user to pass data to this screen
    public void setData(User user){
        this.user = user;
        names = userManager.getNames();
    }

    @FXML
    ListView namesList;
    @FXML
    TextField nameField;
    //takes to home screen
    @FXML
    private void goToHome(ActionEvent event) throws IOException {
        //takes to home screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../home/home.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        HomeController homeController = loader.getController();
        //sends a user object to that page
        homeController.setData(user);
        window.show();
    }

    @FXML
    private void updateList(){
        //searching algorithm that allows people to show results after each type
        namesList.getItems().clear();
        String s = nameField.getText();
        //loops through the people in the users and checks if they match up with what was typed in
        for(String name : names){
            if(s.length() <= name.length()){
                for(int i = 0; i + s.length() <= name.length(); i++){
                    if(s.equals(name.substring(i, i + s.length()))){
                        namesList.getItems().add(name);
                        break;
                    }
                }
            }
        }
    }

    @FXML
    private void addClassmate(){
        //get the id of the classmate that is trying to get added
        int classmateId = Integer.parseInt(userManager.getUserInfo(namesList.getSelectionModel().getSelectedItem().toString())[0]);
        //add the id to the user's database row
        userManager.addClassmateToUser(user.getId(), classmateId);
        nameField.clear();
        user.addClassmate(classmateId);
    }


}
