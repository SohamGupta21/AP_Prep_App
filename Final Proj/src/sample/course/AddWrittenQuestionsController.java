package sample.course;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DatabaseManagers.*;
import sample.Objects.*;
import sample.home.HomeController;

import java.io.IOException;
import java.util.ArrayList;


public class AddWrittenQuestionsController {

    private User user;
    private Course course;
    private ArrayList<WrittenQuestion> questions = new ArrayList<>();

    private MCQManager mcqManager = new MCQManager();
    private WrittenManager writtenManager = new WrittenManager();
    private UnitManager unitManager = new UnitManager();
    private CourseManager courseManager = new CourseManager();
    private UserManager userManager = new UserManager();
    //private  = new UserManager();

    @FXML
    ListView unitsListView;
    @FXML
    TextField promptText;



    public void updateUser(User user) {
        this.user = user;
    }

    public void updateCourse(Course course){this.course = course;}

    public void populateListView(){
        for(Unit u : course.getUnits()){
            unitsListView.getItems().add(u.getName());
        }
    }

    private void addQuestionsToCourse(){
        course.addWritten(questions);
    }

    @FXML
    public void addWrittenQuestion(){
        questions.add(new WrittenQuestion(promptText.getText(), unitsListView.getSelectionModel().getSelectedItem().toString()));
        promptText.clear();
        //writtenManager.databaseRegistration(courseName, unitsListView.getSelectionModel().getSelectedItem().toString(), promptText.getText());
    }

    @FXML
    private void finishCourse(ActionEvent event) throws IOException {
        //adds the course to the database and takes to the homescreen
        addQuestionsToCourse();
        addCourseToDatabase();
        takeHome(event);
    }

    private void addCourseToDatabase(){
        //create all of the questions and add them to the database
        //add all of the mcqs
        String mcqString = "";
        for(MCQ m : course.getMcqs()){
            mcqString = mcqString + (mcqManager.getRowsOfTable() + 1) + "*";
            mcqManager.databaseRegistration(course.getName(),m.getUnit(), m.getQues(), m.getA(), m.getB(), m.getC(), m.getD(), m.getCorrectChoice());
        }
        //add all of the written questions
        String writtenString = "";
        for(WrittenQuestion w : course.getWrittenQuestions()){
            writtenString = writtenString + (writtenManager.getRowsOfTable() + 1) + "*";
            writtenManager.databaseRegistration(course.getName(), w.getUnit(), w.getPrompt());
        }
        //create all of the units and add it to the database with a reference to the questions
        String unitString = "";
        for(Unit u : course.getUnits()){
            unitString = unitString + (unitManager.getRowsOfTable() + 1) + "*";
            String multipleChoiceQuestions = "";
            for(MCQ m : mcqManager.getMCQsByUnitCourse(u.getName(), course.getName())){
                multipleChoiceQuestions += m.getID() + "*";
            }
            String writtenQuestions = "";
            for(WrittenQuestion w : writtenManager.getWrittenByUnitCourse(u.getName(), course.getName())){
                writtenQuestions += w.getID() + "*";
            }
            unitManager.databaseRegistration(u.getName(), writtenQuestions, multipleChoiceQuestions);
        }
        //create the course and add it to the database with a reference to the units
        int courseId = courseManager.getRowsOfTable() + 1;
        courseManager.databaseRegistration(course.getName(), course.getTestDate(), user.getName(), "", mcqString, writtenString, unitString);
        //make sure that the user has a reference to the course
        userManager.addCourseToUser(user.getId(), courseId);
    }

    private void takeHome(ActionEvent event) throws IOException{
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

}
