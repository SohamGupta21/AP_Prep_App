package sample.quiz;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import sample.DatabaseManagers.QuestionsCompletedManager;
import sample.Objects.Course;
import sample.Objects.MCQ;
import sample.Objects.Unit;
import sample.Objects.User;

import java.util.ArrayList;

public class MultipleChoiceController {

    private User user;
    private Course course;
    private Unit unit;
    private ArrayList<MCQ> unansweredMcqs = new ArrayList<>();
    private ArrayList<MCQ> answeredMcqs = new ArrayList<>();
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
    private void submitData(){
        MCQ temp = unansweredMcqs.remove(0);
        temp.setSelectedAnswer(answerSelectionList.getSelectionModel().getSelectedItem());
        answeredMcqs.add(temp);
       if(unansweredMcqs.size() >0){
           mcq = unansweredMcqs.get(0);
       } else {
           //store all of the questions that they answered into the database
           //there really doesn't really have to be a grading system per se, just store the correct answer and what the user actually selected
           QuestionsCompletedManager questionsCompletedManager = new QuestionsCompletedManager();
       }
       displayData();
    }

}
