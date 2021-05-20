package sample.home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sample.DatabaseManagers.CourseManager;
import sample.DatabaseManagers.UserManager;
import sample.DatabaseManagers.WrittenCompletedManager;
import sample.Objects.CompletedWrittenQuestion;
import sample.Objects.Course;
import sample.Objects.User;
import sample.classmate.AddClassmateController;
import sample.classmate.UserSummaryController;
import sample.course.CourseSummaryController;
import sample.course.CreateCourseController;
import sample.review.GradeController;
import sample.schedule.ScheduleController;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class HomeController {
    @FXML
    Label userNameLbl;
    @FXML
    ListView courseList, classmateList,toGrade;

    private User user;
    private ArrayList<CompletedWrittenQuestion> completedWrittenQuestionsList = new ArrayList<>();


    public void setData(String userName) {
        UserManager um = new UserManager();
        String[] response = um.getUserInfo(userName);
        user = new User(Integer.parseInt(response[0]), response[1], response[2], response[3]);
        userNameLbl.setText(user.getName());
        loadCourses();
        loadClassmates();
        loadGrade();
    }

    public void setData(User user) {
        this.user = user;
        userNameLbl.setText(user.getName());
        loadCourses();
        loadClassmates();
        loadGrade();
    }

    public void loadCourses(){
        String courseString = user.getCourses();
        ArrayList<Integer> courses = new ArrayList<>();
        System.out.println(courseString);
        while(courseString != null && courseString.length() > 0){
            courses.add(Integer.parseInt(courseString.substring(0, courseString.indexOf("*"))));
            if(courseString.indexOf("*") + 1 > courseString.length() - 1){
                break;
            }
            courseString = courseString.substring(courseString.indexOf("*") + 1);
        }
        CourseManager courseManager = new CourseManager();
        for(int c : courses){
            courseList.getItems().add(courseManager.getCourseName(c));
        }
    }


    public void loadClassmates(){
        String classmateString = user.getClassmates();
        System.out.println("Classmate string: " + classmateString);
        ArrayList<Integer> classmates = new ArrayList<>();
        while(classmateString != null && classmateString.length() > 0){
            System.out.println("THE STRING IS: " + classmateString);
            classmates.add(Integer.parseInt(classmateString.substring(0, classmateString.indexOf("*"))));
            if(classmateString.indexOf("*") + 1 > classmateString.length() - 1){
                break;
            }
            classmateString = classmateString.substring(classmateString.indexOf("*") + 1);
        }
        UserManager userManager = new UserManager();
        for(int c : classmates){
            classmateList.getItems().add(userManager.getUserInfo(c).getName());
        }
    }

    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    @FXML
    private void goToCreateCourse(ActionEvent event) throws IOException{
        //travels to the page which allows you to create a course
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../course/createcourse.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        CreateCourseController createCourseController = loader.getController();
        //sends a user object to that page
        createCourseController.updateUser(user);
        window.show();
    }

    @FXML
    private void goToAddClassmate(ActionEvent event) throws IOException{
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
    private void goToSchedule(ActionEvent event) throws IOException, ParseException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../schedule/schedule.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);

        ScheduleController scheduleController = loader.getController();
        scheduleController.setData(user, event);

        window.show();
    }

    @FXML
    private void goToCourseSummary(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../course/coursesummary.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        CourseSummaryController  courseSummaryController = loader.getController();
        //get a course object from the course name
        CourseManager courseManager = new CourseManager();
        Course c = courseManager.getCourseByName(courseList.getSelectionModel().getSelectedItem().toString());
        //send the user and course object to the course summary page
        courseSummaryController.setData(user, c);
        window.show();
    }

    @FXML
    private void goToClassmateSummary(ActionEvent event) throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../classmate/usersummary.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        UserSummaryController userSummaryController = loader.getController();
        //get a user object from the username
        UserManager userManager = new UserManager();
        int classmateId = Integer.parseInt(userManager.getUserInfo(classmateList.getSelectionModel().getSelectedItem().toString())[0]);
        User classmate = userManager.getUserInfo(classmateId);
        System.out.println("CLASSMARWE " + classmate);
        //send the main user object and the user object that is being viewed to the user summary page
        userSummaryController.setData(user, classmate);


        window.show();
    }

    private void loadGrade(){
        System.out.println("Loading grade");
        WrittenCompletedManager writtenCompletedManager = new WrittenCompletedManager();
        ArrayList<CompletedWrittenQuestion> completedWrittenQuestions = writtenCompletedManager.getByGraderId(user.getId());
        System.out.println("By grader: " + completedWrittenQuestions);
        for(CompletedWrittenQuestion c : completedWrittenQuestions){
            if(c.getGraderComments() == null){
                toGrade.getItems().add(c.getPrompt());
                completedWrittenQuestionsList.add(c);
            }
        }
    }

    @FXML
    private void goToGrade(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../review/grade.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        GradeController gradeController = loader.getController();
        gradeController.setData(user, completedWrittenQuestionsList.get(toGrade.getSelectionModel().getSelectedIndex()));
        //send the main user object and the user object that is being viewed to the user summary page


        window.show();
    }

}
