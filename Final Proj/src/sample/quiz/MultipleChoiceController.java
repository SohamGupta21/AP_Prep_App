package sample.quiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DatabaseManagers.QuestionsCompletedManager;
import sample.DatabaseManagers.UnitManager;
import sample.Objects.*;
import sample.course.UnitSummaryController;

import java.io.IOException;
import java.util.ArrayList;

public class MultipleChoiceController {

    private User user;
    private Course course;
    private Unit unit;
    private ArrayList<MCQ> unansweredMcqs = new ArrayList<>();
    private ArrayList<CompletedQuestion> answeredMcqs = new ArrayList<>();
    private MCQ mcq;

    @FXML
    Text promptText;
    @FXML
    ListView<String> answerSelectionList;
    @FXML
    Label aLabel, bLabel, cLabel, dLabel;

    public void setData(User user, Course course, Unit unit, ArrayList<MCQ> mcqs){
        this.user = user;
        this.unit = unit;
        this.course = course;
        this.unansweredMcqs = mcqs;
        this.mcq = mcqs.get(0);
        displayData();
    }

    private void displayData(){
        System.out.println("THE MCQ IS: " + mcq);
        promptText.setText(mcq.getQues());
        aLabel.setText("(A) " + mcq.getA());
        bLabel.setText("(B) " + mcq.getB());
        cLabel.setText("(C) " + mcq.getC());
        dLabel.setText("(D) " + mcq.getD());
        answerSelectionList.getItems().clear();
        answerSelectionList.getItems().add("A");
        answerSelectionList.getItems().add("B");
        answerSelectionList.getItems().add("C");
        answerSelectionList.getItems().add("D");

    }

    @FXML
    private void submitData(ActionEvent event) throws IOException{
        MCQ temp = unansweredMcqs.remove(0);
        temp.setSelectedAnswer(answerSelectionList.getSelectionModel().getSelectedItem());
        CompletedQuestion c = new CompletedQuestion(temp.getID(), user.getId(), temp.getSelectedAnswer(), temp.getCorrectChoice());
        answeredMcqs.add(c);
        if(unansweredMcqs.size() >0){
           mcq = unansweredMcqs.get(0);
           displayData();
       } else {
           //store all of the questions that they answered into the database
           //there really doesn't really have to be a grading system per se, just store the correct answer and what the user actually selected
           QuestionsCompletedManager questionsCompletedManager = new QuestionsCompletedManager();
           for(CompletedQuestion completedQuestion : answeredMcqs){
               //send the data to the database
               questionsCompletedManager.databaseRegistration(completedQuestion);
               goBackToUnitSummary(event);
           }
       }
    }

    @FXML
    private void goBackToUnitSummary(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../course/unitsummary.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        UnitSummaryController unitSummaryController = loader.getController();
        //gets the unit object that we want
        unitSummaryController.setData(user, course, unit);
        window.show();
    }

}
