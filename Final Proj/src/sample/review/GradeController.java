package sample.review;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DatabaseManagers.UserManager;
import sample.DatabaseManagers.WrittenCompletedManager;
import sample.Objects.CompletedWrittenQuestion;
import sample.Objects.Course;
import sample.Objects.Unit;
import sample.Objects.User;
import sample.course.UnitSummaryController;

import java.io.IOException;
import java.util.ArrayList;

public class GradeController {

    @FXML
    TextArea comments, response;
    @FXML
    Text prompt, responseTitle;

    private User user;
    private Course course;
    private Unit unit;
    private CompletedWrittenQuestion completedWritten;

    public void setData(User user, Course course, Unit unit,CompletedWrittenQuestion completedWritten){
        this.user = user;
        this.course = course;
        this.unit = unit;
        this.completedWritten = completedWritten;
        displayData();
    }
    @FXML
    private void goToUnitSummary(ActionEvent event) throws IOException{
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
    private void done(ActionEvent event) throws IOException{
        //this should add the comments to the completed question
        WrittenCompletedManager writtenCompletedManager = new WrittenCompletedManager();
        ArrayList<CompletedWrittenQuestion> possibleQuestions = writtenCompletedManager.getByQuesId(completedWritten.getWrittenId());
        for(CompletedWrittenQuestion c : possibleQuestions){
            if(c.getGraderId() == user.getId()){
                if(c.getUserAnswer().equals(completedWritten.getUserAnswer())){
                    //set the grader comments for c in the database
                    writtenCompletedManager.setGraderComments(c.getUserId(), c.getWrittenId(), c.getGraderId(), comments.getText());
                    break;
                }
            }
        }
        goToUnitSummary(event);
    }

    private void displayData(){
        prompt.setText(completedWritten.getPrompt());
        UserManager userManager = new UserManager();
        responseTitle.setText(userManager.getUserInfo(completedWritten.getUserId()).getName() + "'s response");
        response.setText(completedWritten.getUserAnswer());
    }
}
