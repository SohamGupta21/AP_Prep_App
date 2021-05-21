package sample.course;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DatabaseManagers.UserManager;
import sample.Objects.Course;
import sample.Objects.User;
import sample.home.HomeController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class CreateCourseController {

    @FXML
    TextField courseNameField;

    @FXML
    DatePicker testDateField;


    private User user;

    private Course course;

    public void updateUser(User userObj){
        //receives data
        user = userObj;
    }
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

    private void createCourseObj(){
        //creates a course object that will be updated across multiple screens
        Course cs = new Course(courseNameField.getText(),testDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        course = cs;
    }

    @FXML
    private void goToAddUnits(ActionEvent event) throws IOException {
        //takes to add units screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../course/addunit.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);

        AddUnitsController addUnitsController = loader.getController();

        addUnitsController.updateUser(user);
        //creates and sends a course object
        createCourseObj();
        addUnitsController.setCourse(course);

        window.show();
    }
}
