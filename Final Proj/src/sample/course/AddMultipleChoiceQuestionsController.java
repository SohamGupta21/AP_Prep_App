package sample.course;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DatabaseManagers.MCQManager;
import sample.Objects.Course;
import sample.Objects.MCQ;
import sample.Objects.Unit;
import sample.Objects.User;
import sample.home.HomeController;

import java.io.IOException;
import java.util.ArrayList;

public class AddMultipleChoiceQuestionsController {

    private MCQManager  mcqManager = new MCQManager();
    private User user;
    private Course course;

    private ArrayList<MCQ> questions = new ArrayList<>();

    @FXML
    ListView unitsListView;
    @FXML
    TextField choiceA, choiceB, choiceC, choiceD, prompt, correctChoicePicker;



    public void updateUser(User user) {
        //receives data
        this.user = user;
    }

    public void updateCourse(Course c){
        //receives data
        this.course = c;}

    public void populateListView(){
        //displays inputted data
        for(Unit u : course.getUnits()){
            unitsListView.getItems().add(u.getName());
        }
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
        homeController.setData(user);
        window.show();
    }
    @FXML
    private void goToUnitsAdd(ActionEvent event) throws IOException {
        //takes to add units screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../course/addunit.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);

        AddUnitsController addUnitsController = loader.getController();

        addUnitsController.updateUser(user);
        //creates and sends a course object
        addUnitsController.setCourse(course);

        window.show();
    }

    private void addQuestionsToCourse(){
        course.addMCQs(questions);
    }

    @FXML
    private void addQuestion(){
        questions.add(new MCQ(course.getName(),unitsListView.getSelectionModel().getSelectedItem().toString(), prompt.getText(), choiceA.getText(), choiceB.getText(), choiceC.getText(), choiceD.getText(), correctChoicePicker.getText()));
        clearFields();
        //mcqManager.databaseRegistration(course.getName(), unitsListView.getSelectionModel().getSelectedItem().toString(), prompt.getText(), choiceA.getText(), choiceB.getText(), choiceC.getText(), choiceD.getText(), correctChoicePicker.getAccessibleText());
    }
    private void clearFields(){
        prompt.clear();
        choiceA.clear();
        choiceB.clear();
        choiceC.clear();
        choiceD.clear();
        correctChoicePicker.clear();
    }
    @FXML
    private void goToWrittenQuestions(ActionEvent event) throws IOException{
        //go to written question screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addWrittenQuestions.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);

        addQuestionsToCourse();
        AddWrittenQuestionsController addWrittenQuestionsController = loader.getController();
        addWrittenQuestionsController.updateUser(user);
        addWrittenQuestionsController.updateCourse(course);

        window.show();

        addWrittenQuestionsController.populateListView();
    }
}
