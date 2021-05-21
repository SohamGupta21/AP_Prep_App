package sample.course;

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
import sample.Objects.Course;
import sample.Objects.Unit;
import sample.Objects.User;

import java.io.IOException;
import java.util.ArrayList;

public class AddUnitsController {

    private User user;
    private ArrayList<Unit> units = new ArrayList<>();
    private Course course;

    @FXML
    TextField unitField;

    @FXML
    ListView unitsListView;

    @FXML
    public void addUnitToListView(){
        //fills the units list view
        if(unitField.getText().length() > 0){
            unitsListView.getItems().add(unitField.getText());
            units.add(new Unit(unitField.getText()));
        }
        unitField.clear();
    }


    public void updateUser(User userObj){
        user = userObj;
    }

    public void setCourse(Course course){
        this.course = course;
    }

    @FXML
    private void goToCreateCourse(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("createcourse.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    @FXML
    private void goToAddQuestions(ActionEvent event) throws IOException {
        //goes to the mcq questions screen
        //loading the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addMCQquestions.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        //sends the data to the next screen
        addUnitsToCourse();
        AddMultipleChoiceQuestionsController addMultipleChoiceQuestionsController = loader.getController();
        addMultipleChoiceQuestionsController.updateUser(user);
        addMultipleChoiceQuestionsController.updateCourse(course);

        window.show();

        addMultipleChoiceQuestionsController.populateListView();
    }

    private void addUnitsToCourse(){
        course.addUnits(units);
    }

}
