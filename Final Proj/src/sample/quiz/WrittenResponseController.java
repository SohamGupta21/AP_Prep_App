package sample.quiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DatabaseManagers.WrittenCompletedManager;
import sample.DatabaseManagers.WrittenManager;
import sample.Objects.*;
import sample.course.UnitSummaryController;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class WrittenResponseController {

    @FXML
    TextArea textArea;
    @FXML
    Text prompt;

    private User user;
    private Course course;
    private Unit unit;
    private String writtenQuestion;

    public void setData(User user, Course course, Unit unit, String writtenQuestion){
        //receives data
        this.user = user;
        this.course = course;
        this.unit = unit;
        this.writtenQuestion = writtenQuestion;
        displayData();
    }

    private void displayData(){
        //shows the question as the prompt
        prompt.setText(writtenQuestion);
    }

    @FXML
    private void goToUnitSummary(ActionEvent event) throws IOException{
        //takes to unit summary screen
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

    @FXML
    private void submit(ActionEvent event) throws IOException{
        //allows a user to submit the assignment that they completed
        //create a new written completed database object
        WrittenManager writtenManager = new WrittenManager();
        ArrayList<WrittenQuestion> writtenQuestions = writtenManager.getWrittenByUnitCourse(unit.getName(), course.getName());
        WrittenQuestion actualWrittenQuestion = null;
        //finds the written question that the user completed
        for(WrittenQuestion w : writtenQuestions){
            if(w.getPrompt().equals(writtenQuestion)){
                actualWrittenQuestion = w;
                break;
            }
        }
        //makes a new completed question
        CompletedWrittenQuestion completedWrittenQuestion = new CompletedWrittenQuestion(writtenQuestion, actualWrittenQuestion.getID(), user.getId(),textArea.getText());
        WrittenCompletedManager writtenCompletedManager = new WrittenCompletedManager();
        writtenCompletedManager.databaseRegistration(completedWrittenQuestion);
    }
}
