package sample.home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sample.DatabaseManagers.CourseManager;
import sample.DatabaseManagers.UserManager;
import sample.Objects.Course;
import sample.Objects.User;
import sample.classmate.AddClassmateController;
import sample.course.CourseSummaryController;
import sample.course.CreateCourseController;

import java.io.IOException;
import java.util.ArrayList;

public class HomeController {
    @FXML
    Label userNameLbl;
    @FXML
    ListView courseList, classmateList;

    private User user;

    public void setData(String userName){
        UserManager um = new UserManager();
        String[] response = um.getUserData(userName);
        user = new User(Integer.parseInt(response[0]), response[1], response[2], response[3]);
        userNameLbl.setText(user.getName());
        loadCourses();
        loadClassmates();
    }

    public void setData(User user){
        this.user = user;
        userNameLbl.setText(user.getName());
        loadCourses();
        loadClassmates();
    }

    public void loadCourses(){
        String courseString = user.getCourses();
        ArrayList<Integer> courses = new ArrayList<>();
        System.out.println(courseString);
        while(courseString.length() > 0){
            courses.add(Integer.parseInt(courseString.substring(0, courseString.indexOf("*"))));
            if(courseString.indexOf("*") + 1 > courseString.length() - 1){
                break;
            }
            courseString = courseString.substring(courseString.indexOf("*") + 1);
        }
        CourseManager courseManager = new CourseManager();
        for(int c : courses){
            courseList.getItems().add(courseManager.getCourseName(c));
        }
    }


    public void loadClassmates(){
        String classmateString = user.getClassmates();
        ArrayList<Integer> classmates = new ArrayList<>();
        while(classmateString != null && classmateString.length() > 0){
            classmates.add(Integer.parseInt(classmateString.substring(0, classmateString.indexOf("*"))));
            if(classmateString.indexOf("*") + 1 > classmateString.length() - 1){
                break;
            }
            classmateString = classmateString.substring(classmateString.indexOf("*") + 1);
        }
        CourseManager courseManager = new CourseManager();
        for(int c : classmates){
            classmateList.getItems().add(courseManager.getCourseName(c));
        }
    }

    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    @FXML
    private void goToCreateCourse(ActionEvent event) throws IOException{
        //travels to the page which allows you to create a course
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../course/createcourse.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        CreateCourseController createCourseController = loader.getController();
        //sends a user object to that page
        createCourseController.updateUser(user);
        window.show();
    }

    @FXML
    private void goToAddClassmate(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../classmate/addclassmate.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        AddClassmateController addClassmateController = loader.getController();
        addClassmateController.setData(user);
        window.show();
    }

    @FXML
    private void goToSchedule(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("../schedule/schedule.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    @FXML
    private void goToCourseSummary(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../course/coursesummary.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        CourseSummaryController  courseSummaryController = loader.getController();
        //get a course object from the course name
        CourseManager courseManager = new CourseManager();
        Course c = courseManager.getCourseByName(courseList.getSelectionModel().getSelectedItem().toString());
        //send the user and course object to the course summary page
        courseSummaryController.setData(user, c);
        window.show();
    }

    @FXML
    private void goToClassmateSummary(){

    }

}
