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
import sample.DatabaseManagers.*;
import sample.Objects.*;
import sample.quiz.MultipleChoiceController;
import sample.quiz.WrittenResponseController;
import sample.review.AskForGradingController;
import sample.review.GradeController;
import sample.review.MultipleChoiceReviewController;
import sample.review.WrittenReviewController;

import java.io.IOException;
import java.util.ArrayList;

public class UnitSummaryController {

    @FXML
    Text title;
    @FXML
    ListView toCompleteList, completedList, writtenToComplete, writtenToReview, writtenToRequest;
    private User user;
    private Course course;
    private Unit unit;
    private ArrayList<CompletedQuestion> answeredQuestions = new ArrayList<>();
    private ArrayList<CompletedWrittenQuestion> answeredWritten = new ArrayList<>();
    private ArrayList<CompletedWrittenQuestion> toGradeWritten = new ArrayList<>();

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
        ArrayList<MCQ> mcqsToSend = new ArrayList<>();
        System.out.println("Complete items: " + toCompleteList.getItems());
        for(Object s : toCompleteList.getItems()){
            MCQ m = mcqManager.getMCQByPromptUnitCourse(s.toString(), unit.getName(), course.getName());
            mcqsToSend.add(m);
        }
        System.out.println("MCQS to send: " + mcqsToSend);
        //multiple choice controller to send the data
        MultipleChoiceController multipleChoiceController = loader.getController();
        multipleChoiceController.setData(user, course, unit, mcqsToSend);
        window.show();
    }

    @FXML
    private void goToMCQReview(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../review/multiplechoicereview.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        MultipleChoiceReviewController multipleChoiceReviewController = loader.getController();
        //need to pass on an array of completed questions to the controller so that it can cycle through it and the user can check
        multipleChoiceReviewController.setData(answeredQuestions, user, course, unit);
        window.show();
    }

    @FXML
    private void goToWritten(ActionEvent event) throws IOException{
        //what is unique about written questions is that you only really do one at a time, unlike multiple choice questions where you do all at once
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../quiz/writtenresponse.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        WrittenResponseController writtenResponseController = loader.getController();
        writtenResponseController.setData(user, course, unit, writtenToComplete.getSelectionModel().getSelectedItem().toString());

        window.show();
    }

    @FXML
    private void goToWrittenReview(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../review/writtenreview.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        WrittenReviewController writtenReviewController = loader.getController();

        window.show();
    }

    @FXML
    private void goToRequest(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../review/askforgrading.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);

        AskForGradingController askForGradingController = loader.getController();
        askForGradingController.setData(user, course, unit, writtenToRequest.getSelectionModel().getSelectedItem().toString());

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
        displayMultipleChoice();
        displayWritten();
    }

    private void displayMultipleChoice(){
        //display the mcqs
        String mcqs = unit.getMcqs();
        System.out.println("The unit mcqs are: " + unit.getMcqs());
        //get the answered questions by unit and course
        QuestionsCompletedManager questionsCompletedManager = new QuestionsCompletedManager();
        ArrayList<String> splitMCQS = splitString(mcqs);
        MCQManager mcqManager = new MCQManager();
        for (String m : splitMCQS) {
            MCQ temp = mcqManager.getMCQByID(Integer.parseInt(m));
            ArrayList<CompletedQuestion> cqs = questionsCompletedManager.getByQuesId(Integer.parseInt(m));
            boolean flag = false;
            for(CompletedQuestion c : cqs){
                if(c.getUserId() == user.getId()){
                    completedList.getItems().add(temp.getQues());
                    answeredQuestions.add(c);
                    flag = true;
                }
            }
            if(!flag){
                toCompleteList.getItems().add(temp.getQues());
            }
        }
    }

    private void displayWritten(){
        //display the written questions
        //gets the ids of the written questions
        String writtenQues = unit.getWritten();
        ArrayList<String> splitWritten = splitString(writtenQues);
        WrittenManager writtenManager = new WrittenManager();
        WrittenCompletedManager writtenCompletedManager = new WrittenCompletedManager();
        //loops through each id
        for(String w : splitWritten){
            //creates a written question object from the id
            WrittenQuestion temp = writtenManager.getWrittenById(Integer.parseInt(w));
            //constructs and arraylist of all of the completed written questions that match this question id
            ArrayList<CompletedWrittenQuestion> cwqs = writtenCompletedManager.getByQuesId(Integer.parseInt(w));
            boolean wflag = false;
            //for every written question in here
            for(CompletedWrittenQuestion cwq : cwqs){
                // if it is the question that this user answered
                if(cwq.getUserId() == user.getId()){
                    if(cwq.getGraderId() >= 1 && cwq.getGraderComments() != null){
                        //should be to review
                        wflag = true;
                        answeredWritten.add(cwq);
                        writtenToReview.getItems().add(temp.getPrompt());
                    }
                    if(cwq.getGraderId() < 1){
                        //should be to request
                        wflag = true;
                        answeredWritten.add(cwq);
                        writtenToRequest.getItems().add(temp.getPrompt());
                    }
                }
            }
            if(!wflag){
                //should be to complete
                writtenToComplete.getItems().add(temp.getPrompt());
            }
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