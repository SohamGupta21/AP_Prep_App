package sample.review;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Objects.CompletedWrittenQuestion;
import sample.Objects.Course;
import sample.Objects.Unit;
import sample.Objects.User;
import sample.course.UnitSummaryController;

import java.awt.*;
import java.io.IOException;

public class WrittenReviewController {

    @FXML
    TextArea userResponseText, comments;

    private User user;
    private Course course;
    private Unit unit;
    private CompletedWrittenQuestion completedWrittenQuestion;

    public void setData(User user, Course course, Unit unit, CompletedWrittenQuestion completedWrittenQuestion){
        this.user = user;
        this.course = course;
        this.unit = unit;
        //this completed question has to have the grader id and comments in thr object
        this.completedWrittenQuestion = completedWrittenQuestion;
    }

    @FXML
    public void goToUnitSummary(ActionEvent event) throws IOException{
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
