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
    private ArrayList<MCQ> mcqs = new ArrayList<>();
    private MCQ mcq;

    @FXML
    Text promptText;
    @FXML
    ListView answerSelectionList;
    @FXML
    Label aLabel, bLabel, cLabel, dLabel;

    public void setData(User user, Course course, Unit unit, ArrayList<MCQ> mcqs){
        this.user = user;
        this.unit = unit;
        this.course = course;
        this.mcqs = mcqs;
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
        answerSelectionList.getItems().add("A");
        answerSelectionList.getItems().add("B");
        answerSelectionList.getItems().add("C");
        answerSelectionList.getItems().add("D");

    }

    @FXML
    private void submitData(){
       mcqs.remove(0);
       mcq = mcqs.get(0);
       displayData();
    }

}
