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



    public void setData(User mainUser, User userBeingViewed){
        this.mainUser = mainUser;
        this.userBeingViewed = userBeingViewed;
        System.out.println(userBeingViewed);
        displayData();

    }

    private void displayData() {
        CourseManager courseManager = new CourseManager();
        if (userBeingViewed.getCourses() != null) {
            ArrayList<String> userBeingViewedCourses = new ArrayList<>();
            for (String s : splitString(userBeingViewed.getCourses())) {
                userBeingViewedCourses.add(courseManager.getCourseName(Integer.parseInt(s)));
            }
            //adds the courses to the listview
            for (String s : userBeingViewedCourses) {
                classmatesList.getItems().add(s);
            }
        }

        //gets the list of classmate names from the string of ids
        if(userBeingViewed.getClassmates() != null){
            UserManager userManager = new UserManager();
            ArrayList<User> userBeingViewedClassmates = new ArrayList<>();
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

    @FXML
    private void addCoursePractice(){
        coursesList.getItems().add("WTF");
    }

    private ArrayList<String> splitString(String s){
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
        Parent parent = FXMLLoader.load(getClass().getResource("../home/home.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    @FXML
    private void viewClassmate(){

    }

    @FXML
    private void viewCourse(){

    }
}
