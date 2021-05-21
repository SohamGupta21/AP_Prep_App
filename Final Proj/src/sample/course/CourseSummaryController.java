package sample.course;

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
import sample.DatabaseManagers.UnitManager;
import sample.Objects.Course;
import sample.Objects.Unit;
import sample.Objects.User;
import sample.home.HomeController;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CourseSummaryController {

    private User user;
    private Course course;

    @FXML
    Text title;
    @FXML
    ListView unitsList;
    @FXML
    Label dateString;

    public void setData(User user, Course course) {
        this.user = user;
        this.course = course;
        fillScreenData();
    }

    private void fillScreenData(){
        title.setText(course.getName());
        unitsList.getItems().clear();
        for(Unit u : course.getUnits()){
            unitsList.getItems().add(u.getName());
        }
        dateString.setText("Test Date: " + course.getTestDate().substring(0, course.getTestDate().indexOf("00:00")));
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


    @FXML
    private void goToUnitSummary(ActionEvent event) throws IOException{
        //takes to unit summary
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../course/unitsummary.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        UnitSummaryController unitSummaryController = loader.getController();
        //gets the unit object that we want
        UnitManager unitManager = new UnitManager();
        //get unit ids by name
        ArrayList<Integer> possibleIds = unitManager.getUnitsByName(unitsList.getSelectionModel().getSelectedItem().toString());
        //figure out which unit id corresponds to this course
        Unit actualUnit = new Unit();
        for(Unit u : course.getUnits()){
            for(Integer i : possibleIds){
                if(u.getId() == i){
                    actualUnit = u;
                    break;
                }
            }
        }
        //sends the course, unit and user object across
        unitSummaryController.setData(user, course, actualUnit);
        window.show();
    }
}
