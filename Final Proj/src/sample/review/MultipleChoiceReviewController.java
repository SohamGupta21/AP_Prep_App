package sample.review;

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
import sample.DatabaseManagers.MCQManager;
import sample.Objects.*;
import sample.course.UnitSummaryController;

import java.io.IOException;
import java.util.ArrayList;

public class MultipleChoiceReviewController {

    @FXML
    ListView questionsList;
    @FXML
    Label quesLabel;
    @FXML
    Text aChoice, bChoice, cChoice, dChoice, yourChoice, correctChoice;

    private ArrayList<CompletedQuestion> answeredQuestions = new ArrayList<>();
    private ArrayList<MCQ> questionsAsMCQs = new ArrayList<>();
    private User user;
    private Course course;
    private Unit unit;

    public void setData(ArrayList<CompletedQuestion> answered, User user, Course course, Unit unit){
        this.answeredQuestions = answered;
        this.user = user;
        this.course = course;
        this.unit = unit;
        displayData(0);
    }

    private void displayData(int i){

        MCQManager mcqManager = new MCQManager();
        for(CompletedQuestion c : answeredQuestions){
            int qId = c.getQuestionId();
            MCQ m = mcqManager.getMCQByID(qId);
            questionsList.getItems().add(m.getQues());
            questionsAsMCQs.add(m);
        }
        quesLabel.setText(questionsAsMCQs.get(i).getQues());
        aChoice.setText(questionsAsMCQs.get(i).getA());
        bChoice.setText(questionsAsMCQs.get(i).getB());
        cChoice.setText(questionsAsMCQs.get(i).getC());
        dChoice.setText(questionsAsMCQs.get(i).getD());

        yourChoice.setText("Your Choice: " + answeredQuestions.get(i).getAnswer());
        correctChoice.setText("Correct Choice: " + answeredQuestions.get(i).getCorrectAnswer());
    }

    private void updateData(int i){

        quesLabel.setText(questionsAsMCQs.get(i).getQues());
        aChoice.setText(questionsAsMCQs.get(i).getA());
        bChoice.setText(questionsAsMCQs.get(i).getB());
        cChoice.setText(questionsAsMCQs.get(i).getC());
        dChoice.setText(questionsAsMCQs.get(i).getD());

        yourChoice.setText("Your Choice: " + answeredQuestions.get(i).getAnswer());
        correctChoice.setText("Correct Choice: " + answeredQuestions.get(i).getCorrectAnswer());
    }

    @FXML
    private void chooseQues(){
        //get the index of the selected item
        int i = questionsList.getSelectionModel().getSelectedIndex();
        updateData(i);
    }

    @FXML
    private void goToUnitSummary(ActionEvent event) throws IOException {
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
