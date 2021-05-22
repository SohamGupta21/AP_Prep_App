package sample.classmate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DatabaseManagers.CourseManager;
import sample.DatabaseManagers.UserManager;
import sample.Objects.Course;
import sample.Objects.User;
import sample.home.HomeController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class UserSummaryController {

    private User mainUser;
    private User userBeingViewed;

    @FXML
    Label userName;
    @FXML
    ListView coursesList, classmatesList;


    //allows to pass data into this controller
    public void setData(User mainUser, User userBeingViewed){
        this.mainUser = mainUser;
        this.userBeingViewed = userBeingViewed;
        System.out.println(userBeingViewed);
        displayData();

    }
    //shows the data on the screen, after the data is passed in
    private void displayData() {
        CourseManager courseManager = new CourseManager();
        if (userBeingViewed.getCourses() != null) {
            ArrayList<String> userBeingViewedCourses = new ArrayList<>();
            for (String s : splitString(userBeingViewed.getCourses())) {
                userBeingViewedCourses.add(courseManager.getCourseName(Integer.parseInt(s)));
            }
            //adds the courses to the listview
            for (String s : userBeingViewedCourses) {
                coursesList.getItems().add(s);
            }
        }

        //gets the list of classmate names from the string of ids
        if(userBeingViewed.getClassmates() != null){
            UserManager userManager = new UserManager();
            ArrayList<User> userBeingViewedClassmates = new ArrayList<>();
            ///this loop throught the split string is essentially looping through an id
            for (String s : splitString(userBeingViewed.getClassmates())) {
                userBeingViewedClassmates.add(userManager.getUserInfo(Integer.parseInt(s)));
            }
            //adds the classmates to the listview
            for (User u : userBeingViewedClassmates) {
                classmatesList.getItems().add(u.getName());
            }
        }
        userName.setText(userBeingViewed.getName());

    }

    private ArrayList<String> splitString(String s){
        //splits string of ids from database into an arraylist
        ArrayList<String> answer = new ArrayList<>();
        while(s.length() > 0){
            answer.add(s.substring(0, s.indexOf("*")));
            if(s.indexOf("*") + 1 > s.length() - 1){
                break;
            }
            s = s.substring(s.indexOf("*") + 1);
        }
        return answer;
    }

    @FXML
    private void goToHome(ActionEvent event) throws IOException {
        //takes the user to the home screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../home/home.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        HomeController homeController = loader.getController();
        //sends a user object to that page
        homeController.setData(mainUser);
        window.show();
    }

    @FXML
    private void viewClassmate(ActionEvent event) throws IOException{
        //goes to classmate summary page with a new user
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../classmate/usersummary.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        UserSummaryController userSummaryController = loader.getController();
        //get a user object from the username
        UserManager userManager = new UserManager();
        int classmateId = Integer.parseInt(userManager.getUserInfo(classmatesList.getSelectionModel().getSelectedItem().toString())[0]);
        User classmate = userManager.getUserInfo(classmateId);
        System.out.println("CLASSMATE " + classmate);
        //send the main user object and the user object that is being viewed to the user summary page
        userSummaryController.setData(mainUser, classmate);


        window.show();
    }
    
}
