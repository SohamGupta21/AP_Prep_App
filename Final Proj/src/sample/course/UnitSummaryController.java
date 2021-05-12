package sample.course;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DatabaseManagers.MCQManager;
import sample.DatabaseManagers.UnitManager;
import sample.Objects.Course;
import sample.Objects.MCQ;
import sample.Objects.Unit;
import sample.Objects.User;
import sample.quiz.MultipleChoiceController;

import java.io.IOException;
import java.util.ArrayList;

public class UnitSummaryController {

    @FXML
    Text title;
    @FXML
    ListView toCompleteList, completedList;
    private User user;
    private Course course;
    private Unit unit;

    @FXML
    private void goToCourseSummary(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../course/coursesummary.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        CourseSummaryController courseSummaryController = loader.getController();
        courseSummaryController.setData(user, course);
        window.show();
    }

    @FXML
    private void goToMultipleChoice(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../quiz/multiplechoice.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        //getting the MCQ
        MCQManager mcqManager = new MCQManager();
        MCQ m = mcqManager.getMCQByPromptUnitCourse(toCompleteList.getSelectionModel().getSelectedItem().toString(), unit.getName(), course.getName());
        //multiple choice controller to send the data
        MultipleChoiceController multipleChoiceController = loader.getController();
        multipleChoiceController.setData(user, course, unit, m);
        window.show();
    }

    public void setData(User user, Course course, Unit unit) {
        this.user = user;
        this.course = course;
        this.unit = unit;
        displayData();
    }

    private void displayData() {
        title.setText(unit.getName());
        String mcqs = unit.getMcqs();
        ArrayList<String> splitMCQS = splitString(mcqs);
        MCQManager mcqManager = new MCQManager();
        for (String m : splitMCQS) {
            MCQ temp = mcqManager.getMCQByID(Integer.parseInt(m));
            toCompleteList.getItems().add(temp.getQues());
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

}

