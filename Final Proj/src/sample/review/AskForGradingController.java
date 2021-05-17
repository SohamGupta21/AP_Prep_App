package sample.review;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DatabaseManagers.UserManager;
import sample.DatabaseManagers.WrittenCompletedManager;
import sample.Objects.CompletedWrittenQuestion;
import sample.Objects.Course;
import sample.Objects.Unit;
import sample.Objects.User;
import sample.classmate.AddClassmateController;
import sample.course.UnitSummaryController;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class AskForGradingController {

    @FXML
    ListView classmates;

    private User user;
    private Course course;
    private Unit unit;
    private String completedQuestion;
    private ArrayList<User> classmatesList = new ArrayList<>();


    public void setData(User user, Course course, Unit unit, String completedQuestion){
        this.user = user;
        this.course = course;
        this.unit = unit;
        this.completedQuestion = completedQuestion;
        diplayData();
    }

    private void diplayData(){
        UserManager userManager = new UserManager();
        for(String s : splitString(user.getClassmates())){
            int ind = Integer.parseInt(s);
            User u = userManager.getUserInfo(ind);
            classmates.getItems().add(u.getName());
            classmatesList.add(u);
        }
    }
    private ArrayList<String> splitString(String s) {
        ArrayList<String> answer = new ArrayList<>();
        while (s.length() > 0) {
            answer.add(s.substring(0, s.indexOf("*")));
            if (s.indexOf("*") + 1 > s.length() - 1) {
                break;
            }
            s = s.substring(s.indexOf("*") + 1);
        }
        return answer;
    }


    @FXML
    private void askToGrade(){
        //identify the database reference that the string written question is referring to
        WrittenCompletedManager writtenCompletedManager = new WrittenCompletedManager();
        ArrayList<CompletedWrittenQuestion> possibleQuestions = writtenCompletedManager.getByQuesName(completedQuestion);
        for(CompletedWrittenQuestion c : possibleQuestions){
            if(c.getUserId() == user.getId()){
                //send a request to the database to change the graderId for the database reference found
                writtenCompletedManager.setGraderId(user.getId(), c.getWrittenId(), classmatesList.get(classmates.getSelectionModel().getSelectedIndex()).getId());
            }
        }

    }

    @FXML
    private void goToAddClassmate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../classmate/addclassmate.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        AddClassmateController addClassmateController = loader.getController();
        addClassmateController.setData(user);
        window.show();
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

}
