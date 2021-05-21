package sample.schedule;

import com.mysql.cj.x.protobuf.MysqlxExpr;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DatabaseManagers.*;
import sample.Objects.*;
import sample.home.HomeController;
import sample.quiz.MultipleChoiceController;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ScheduleController {

    @FXML
    Label monthLabel;
    @FXML
    GridPane calendarGrid;
    @FXML
    ListView possibleMonths, mcqs, written, tests;
    @FXML
    Label progressLabel;
    @FXML
    Button loadBtn, selectMonthBtn;
    @FXML
    Text monthText, mcqsText, writtenText, testsText;


    private User user;

    private ArrayList<Course> userCourses = new ArrayList<>();
    private ArrayList<Unit> userUnits  = new ArrayList<>();
    private ArrayList<CompletedQuestion> userCompletedQuestions = new ArrayList<>();
    private ArrayList<MCQ> userUncompletedQuestions = new ArrayList<>();
    private ArrayList<CompletedWrittenQuestion> userCompletedWrittenQuestions = new ArrayList<>();
    private ArrayList<WrittenQuestion> userUncompletedWrittenQuestions = new ArrayList<>();
    private ArrayList<Date> testDates = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<DayPlan>>> plan = new ArrayList<>();
    private ArrayList<DayPlan> selectedMonth = new ArrayList<>();

    private final Button[][] cal = new Button[5][7];
    private ArrayList<ArrayList<Integer>> yearMonthDayCombos = new ArrayList<ArrayList<Integer>>();
    // accepts the data from the home screen
    public void setData(User user, ActionEvent e){
        this.user = user;
    }
    //when user decides to load the schedule
    @FXML
    private void loadSchedule(ActionEvent e) throws ParseException {
        progressLabel.setText("Gathering data and setting up the calendar...");
        gatherInformation();
        setUpCalendar(e);
    }
    //consolidates all of the information that will be needed in the creation of the formula
    private void gatherInformation(){
        //all of the courses of the user
        CourseManager courseManager = new CourseManager();
        for(String c : splitString(user.getCourses())){
            String cName = courseManager.getCourseName(Integer.parseInt(c));
            userCourses.add(courseManager.getCourseByName(cName));
        }
        //all of the units of the user
        for(Course course : userCourses){
            for(Unit u : course.getUnits()){
                userUnits.add(u);
            }
        }
        //all completed multiple choice questions
        QuestionsCompletedManager questionsCompletedManager = new QuestionsCompletedManager();
        for(CompletedQuestion cq : questionsCompletedManager.getByUserId(user.getId())){
            userCompletedQuestions.add(cq);
        }
        //all uncompleted multiple choice questions
        //using the database to gather the information that is needed
        MCQManager mcqManager = new MCQManager();
        for(Unit u : userUnits){
            for(String mcq : splitString(u.getMcqs())){
                MCQ temp = mcqManager.getMCQByID(Integer.parseInt(mcq));
                boolean add = true;
                for(CompletedQuestion c : userCompletedQuestions){
                    if(c.getQuestionId() == temp.getID()){
                        add = false;
                    }
                }
                if(add){
                    userUncompletedQuestions.add(temp);
                }
            }
        }
        //all completed written questions
        WrittenCompletedManager writtenCompletedManager = new WrittenCompletedManager();
        for(CompletedWrittenQuestion cwq : writtenCompletedManager.getByUserId(user.getId())){
            userCompletedWrittenQuestions.add(cwq);
        }
        //all uncompleted written questions
        //uses the database the gather the information
        WrittenManager writtenManager = new WrittenManager();
        for(Unit u : userUnits){
            for(String w : splitString(u.getWritten())){
                WrittenQuestion temp = writtenManager.getWrittenById(Integer.parseInt(w));
                boolean addW = true;
                for(CompletedWrittenQuestion wcq : userCompletedWrittenQuestions){
                    if(wcq.getWrittenId() == temp.getID()){
                        addW = false;
                    }
                }
                if(addW){
                    userUncompletedWrittenQuestions.add(temp);
                }
            }
        }
    }

    private void setUpCalendar(ActionEvent e) throws ParseException {
        //creates and orders the test dates as needed
        generateTestDatesArray();
        //create a plan 3d array that contains the entire plan
        fillBasicPlan();
        //applies the math formula that determines what makes the most sense for a user to work on at the time
        applyFormula();
        // at this point the plan array is filled with all of the data that it needs now we need to put it on the screen
        setUpGridPane(e);
        //make relevant things visible
        makeThingsVisible();
    }

    private void makeThingsVisible(){
        //sets things invisible when the database has all loaded
        loadBtn.setVisible(false);
        progressLabel.setVisible(false);
        //makes things visible
        calendarGrid.setVisible(true);
        mcqs.setVisible(true);
        written.setVisible(true);
        tests.setVisible(true);
        monthLabel.setVisible(true);
        possibleMonths.setVisible(true);
        selectMonthBtn.setVisible(true);
        monthText.setVisible(true);
        mcqsText.setVisible(true);
        writtenText.setVisible(true);
        testsText.setVisible(true);
    }

    private void setUpGridPane(ActionEvent e){
        //add all of the buttons in the gridpane
        int count = 1;
        System.out.println(cal.length);
        System.out.println(cal[0].length);
        //adds the buttons to create the grid - similar to the bold project
        for(int r = 0; r < cal.length; r++){
            for(int c = 0; c < cal[0].length; c++){
                cal[r][c] = new Button();
                cal[r][c].setPrefHeight(106);
                cal[r][c].setPrefWidth(110);
                calendarGrid.add(cal[r][c], c, r);
                cal[r][c].setText(Integer.toString(count));
                count ++;
            }
        }
        // add a function for all of the buttons
        EventHandler z = new EventHandler() {
            @Override
            public void handle(Event event) {
                int rowIndex = GridPane.getRowIndex((Button) event.getSource());
                int colIndex = GridPane.getColumnIndex((Button) event.getSource());
                int number = rowIndex * 7 + colIndex + 1;
                //make the things visible based on the day plan object
                //fills the entire thing
                DayPlan d  = selectedMonth.get(number-1);
                tests.getItems().clear();
                mcqs.getItems().clear();
                written.getItems().clear();
                //fills listview with the things that the user has to do on that day
                for(MCQ m : d.getMultipleChoiceQuestions()){
                    mcqs.getItems().add(m.getQues() + " in " + m.getUnit());
                }
                for(WrittenQuestion w : d.getWrittenQuestions()){
                    written.getItems().add(w.getPrompt() + " in " + w.getUnit());
                }
                for(String s : d.getCoursesToTest()){
                    tests.getItems().add(s);
                }
            }
        };
        //sets the onclicked function
        for(int r = 0; r < cal.length; r++){
            for(int c = 0; c < cal[0].length; c++){
                cal[r][c].setOnMouseClicked(z);
            }
        }
        //load all the possible months in the listview so the user can select it
        for(int y = 0; y < plan.size(); y++){
            for(int m = 0; m < 12; m++){
                for(int d = 0; d < 31; d++){
                    if(plan.get(y).get(m).get(d).getUsed()){
                        ArrayList<Integer> temp = new ArrayList<>();
                        temp.add(y);
                        temp.add(m);
                        temp.add(d);
                        yearMonthDayCombos.add(temp);
                    }
                }
            }
        }
        Date current = new Date();
        //now that the yearMonthDayCombos array is filled with all of the days that have been used, we can
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for(ArrayList<Integer> combo : yearMonthDayCombos){
            String year = "20" + Integer.toString(current.getYear() + combo.get(0)).substring(1);
            String month = months[combo.get(1)];
            possibleMonths.getItems().add(month + " " + year);
        }
        //remove duplicates
        ArrayList<String> monthsInList = new ArrayList<>();
        boolean exists = false;
        //this makes sure that the user can pick the months sibgularly and it doesn't write the same thing over and over
        for(int i = 0; i < possibleMonths.getItems().size(); i++){
            exists = false;
            for(String s : monthsInList){
                if(s.equals(possibleMonths.getItems().get(i).toString())){
                    exists = true;
                }
            }
            if(!exists){
                monthsInList.add(possibleMonths.getItems().get(i).toString());
            }
        }
        possibleMonths.getItems().clear();
        //adds the new list to the array
        for(String s : monthsInList){
            possibleMonths.getItems().add(s);
        }
    }

    private void generateTestDatesArray() throws ParseException{
        //this function identifies when the test days are for the user
        Date currentDate = new Date();
        //test dates are stored in the courses list
        for(Course c : userCourses){
            testDates.add(new SimpleDateFormat("yyyy-MM-dd").parse(c.getTestDate().substring(0,c.getTestDate().indexOf(" "))));
        }
        //we need to remove all of the courses which have a test date that already passed
        for(int d = testDates.size()-1; d>=0;d--){
            if(testDates.get(d).compareTo(currentDate) <= 0 ){
                testDates.remove(d);
                userCourses.remove(d);
            }
        }
        // now we need to order test dates and courses by date
        for(int i = 0; i<testDates.size(); i++){
            Date earliestDate = testDates.get(i);
            int earliestDateIndex = i;
            for(int j = i; j<testDates.size(); j++){
                //this compare function is in the date class and is used to compare two dates
                if(testDates.get(j).compareTo(earliestDate) < 0){
                    earliestDate = testDates.get(j);
                    earliestDateIndex = j;
                }
            }
            //switching code
            Date temp = testDates.get(i);
            testDates.set(i, earliestDate);
            testDates.set(earliestDateIndex, temp);
        }
    }

    private void fillBasicPlan(){
        //fills the plan arraylist which is a 3d array list that contains year, months and days
        //add the required number of years
        for(Date d : testDates){
            System.out.println("YEARSSS: " + d.getYear());
        }
        int earliestYear = testDates.get(0).getYear();
        int latestYear = testDates.get(testDates.size()-1).getYear();
        for(int i = 0; i<=latestYear-earliestYear;i++){
            plan.add(new ArrayList<>());
        }
        //adds the 12 months
        for(ArrayList a : plan){
            for(int m = 0; m<= 11; m++){
                a.add(new ArrayList<>());
            }
        }
        //adds 31 days to each month
        for(ArrayList<ArrayList<DayPlan>> a : plan){
            for(ArrayList month : a){
                for(int i = 0; i<31; i++){
                    month.add(new DayPlan());
                }
            }
        }
        //loops through the testDates arraylist to add a date object to the corresponding spot
        for(Course c : userCourses) {
            String testD = c.getTestDate();
            int year = Integer.parseInt("1" + testD.substring(2, 4))-earliestYear;
            int month = Integer.parseInt(testD.substring(5, 7))-1;
            int day = Integer.parseInt(testD.substring(8, 10))-1;
            plan.get(year).get(month).get(day).addTestDate(true, c.getName());
        }
    }
    //INTERESTING CODE
    private void applyFormula() throws ParseException {
        //makes the scheduling formula and applies it then
        UnitManager unitManager = new UnitManager();
        //get the unit scores from the getUnitScores function
        //this has arraylists whihc represent each course and those have a list of numbers that represent the confidence in each unit
        ArrayList<ArrayList<Double>> unitScores = getUnitScores();
        //for every course
        for (int c = 0; c <userCourses.size();c++){
            //determine the days that we have to work on each unit
            Date current = new Date();
            Date test = new SimpleDateFormat("yyyy-MM-dd").parse(userCourses.get(c).getTestDate().substring(0,userCourses.get(c).getTestDate().indexOf(" ")));
            double difference = (double) TimeUnit.DAYS.convert(test.getTime()-current.getTime(),TimeUnit.MILLISECONDS);
            //this has to account for the fact that if there is less than a day's gap then it will think there are 0 days in between
            difference ++;
            //order the units based on their priority
            ArrayList<Unit> courseUnits = userCourses.get(c).getUnits();
            ArrayList<Double> scores = unitScores.get(c);
            //SELECTION SORT ALGORITHM <- Mr. Cortez
            //this is used to sort the scores array to find out what units are most important
            for(int i = 0; i<scores.size(); i++){
                double minVal = scores.get(i);
                int minIndex = i;
                for(int j=i; j < scores.size(); j++){
                    if(scores.get(j) < scores.get(i)){
                        minIndex = j;
                        minVal = scores.get(j);
                    }
                }
                Unit tempUnit = courseUnits.get(i);
                double tempScore = scores.get(i);
                courseUnits.set(i, courseUnits.get(minIndex));
                scores.set(i, minVal);
                courseUnits.set(minIndex, tempUnit);
                scores.set(minIndex, tempScore);
            }
            //determine the written and multiple choice questions for each unit
            ArrayList<ArrayList<MCQ>> mcqsPerUnit = new ArrayList<>();
            ArrayList<ArrayList<WrittenQuestion>> writtenPerUnit = new ArrayList<>();
            for(int un = 0;un < courseUnits.size();un++){
                //basically checks what questions are completed and not and which to ask the user to do
                mcqsPerUnit.add(new ArrayList<>());
                writtenPerUnit.add(new ArrayList<>());
                for(MCQ mcq : userUncompletedQuestions){
                    int unitId = unitManager.getUnitByName(mcq.getUnit()).getId();
                    if(unitId == courseUnits.get(un).getId()){
                        mcqsPerUnit.get(un).add(mcq);
                    }
                }
                //does the same thing as above for written questions
                for(WrittenQuestion writtenQuestion : userUncompletedWrittenQuestions){
                    int unitIdW = unitManager.getUnitByName(writtenQuestion.getUnit()).getId();
                    if(unitIdW == courseUnits.get(un).getId()){
                        writtenPerUnit.get(un).add(writtenQuestion);
                    }
                }
            }
            //divide the assignments across the multiple days
            //reminder: days for the course = difference variable
            //adds everything for the mcq
            ArrayList<ArrayList<MCQ>> mcqDays = new ArrayList<>();
            //adds the number of days that we could possibly add stuff to
            for(int d = 0; d < difference; d++){
                mcqDays.add(new ArrayList<MCQ>());
            }
            ArrayList<MCQ> fullMcqs = new ArrayList<>();
            for(ArrayList<MCQ> a : mcqsPerUnit){
                for(MCQ m : a){
                    fullMcqs.add(m);
                }
            }
            ///adds the number of questions that are necessary for each of the days that we need it for
            int questionsPerDay = fullMcqs.size()/mcqDays.size();
            for(int dayInd = 0; dayInd < mcqDays.size(); dayInd++){
                for(int quesCount = 0; quesCount < questionsPerDay; quesCount++){
                    mcqDays.get(dayInd).add(fullMcqs.remove(0));
                }
            }
            for(int i = 0; i<fullMcqs.size(); i++){
                mcqDays.get(mcqDays.size()-1).add(fullMcqs.remove(0));
            }
            //adds everything for the written
            //this code does the same as the mcq code above but for the written part
            ArrayList<ArrayList<WrittenQuestion>> writtenDays = new ArrayList<>();
            for(int d = 0; d < difference; d++){
                writtenDays.add(new ArrayList<WrittenQuestion>());
            }
            ArrayList<WrittenQuestion> fullWritten = new ArrayList<>();
            for(ArrayList<WrittenQuestion> a : writtenPerUnit){
                for(WrittenQuestion w : a){
                    fullWritten.add(w);
                }
            }
            //adds the questions that are needed to be done in each day
            int questionsPerDayW = fullWritten.size()/writtenDays.size();
            for(int dayInd = 0; dayInd < writtenDays.size(); dayInd++){
                for(int quesCount = 0; quesCount < questionsPerDayW; quesCount++){
                    writtenDays.get(dayInd).add(fullWritten.remove(0));
                }
            }
            for(int i = 0; i<fullWritten.size(); i++){
                writtenDays.get(writtenDays.size()-1).add(fullWritten.remove(0));
            }
            //now, starting with today, we need to add the work of writtendays into the plan
            int count = 0;
            int[] daysInMonth = {31,28,31,30,31,30,31,31,30,31,30,31};
            int year = 0;
            int month = current.getMonth();
            int day = current.getDay();
            //updating the master 3d array list called plan, essentially just loading the information that we got previously
            while(true){
                plan.get(year).get(month).get(day).addMultipleChoiceQuestions(mcqDays.get(count));
                plan.get(year).get(month).get(day).addWrittenQuestions(writtenDays.get(count));
                if(day % daysInMonth[month] == 0){
                    if(month == 11){
                        month = 0;
                        day = 0;
                        year +=1;
                    }else {
                        month++;
                        day = 1;
                    }
                } else {
                    day ++;
                }
                count ++;
                if(count == mcqDays.size()){
                    break;
                }
            }

        }
    }
    //INTERESTING CODE
    private ArrayList<ArrayList<Double>> getUnitScores(){
        //applying a formula to get the scores of all of the units of each course
        //looping through the courses
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        for(Course c : userCourses){
            //looping through the units
            ArrayList<Unit> unitsOfCourse = c.getUnits();
            ArrayList<Double> unitScores = new ArrayList<>();
            for(Unit u : unitsOfCourse){
                double unitScore = 0;
                //figuring out the MCQ score 0-5
                int mcqNum = 0;
                int mcqDenom = 0;
                //for uncompleted questions
                //adds one to denominator, the total amount of questions
                for(MCQ m : userUncompletedQuestions){
                    if(m.getUnit().equals(u.getName())){
                        mcqDenom ++;
                    }
                }
                //for completed questions
                //adds to the demon and possibly num if it is completed
                for(CompletedQuestion cq : userCompletedQuestions){
                    for(String cm : splitString(u.getMcqs())){
                        if(cq.getQuestionId() == Integer.parseInt(cm)){
                            mcqDenom ++;
                            if(cq.getAnswer().equals(cq.getCorrectAnswer())){
                                mcqNum ++;
                            }
                        }
                    }
                }
                //calculating score...
                double mcqScore = (double) mcqNum / (double) mcqDenom;
                mcqScore *=100;
                mcqScore /= 20;
                //figuring out the written score 1-5
                ArrayList<Integer> writtenScores = new ArrayList<>();
                UnitManager unitManager = new UnitManager();
                //written uncompleted questions
                //adding scores of 0 for undone questions
                for(WrittenQuestion w : userUncompletedWrittenQuestions){
                    Unit unitFromData = unitManager.getUnitByName(w.getUnit());
                    if(unitFromData.getId() == u.getId()){
                        writtenScores.add(0);
                    }
                }
                //written completed questions
                //adding scores for questions that are completed
                for(CompletedWrittenQuestion cwq : userCompletedWrittenQuestions){
                    for(String w : splitString(u.getWritten())){
                        if((cwq.getWrittenId() == Integer.parseInt(w))){
                            writtenScores.add(cwq.getNumberGrade());
                        }
                    }
                }
                double writtenScore = 0;
                for(int i : writtenScores){
                    writtenScore += i;
                }
                writtenScore /= writtenScores.size();
                //averaging the MCQ and written score and adding it to the unit scores array
                unitScore =  ((mcqScore + writtenScore)/2);
                if(unitScore >= 0) unitScores.add(unitScore);
                else unitScores.add(5.0);
            }
            result.add(unitScores);
        }
        return result;
    }

    private ArrayList<String> splitString(String s){
        //in the database there are no arrays
        //this just separates a string of ids into an arraylist
        ArrayList<String> answer = new ArrayList<>();
        while(s.length() > 0){
            answer.add(s.substring(0, s.indexOf("*")));
            if(s.indexOf("*") + 1 > s.length() - 1){
                break;
            }
            s = s.substring(s.indexOf("*") + 1);
        }
        return answer;
    }

    @FXML
    private void selectMonth(){
        //undisable everything first
        for(int r = 0; r<5; r++){
            for(int c = 0; c < 5; c++){
                cal[r][c].setDisable(false);
                cal[r][c].setVisible(true);
            }
        }
        // this code is to load the actual plan into the calendar
        String month = possibleMonths.getSelectionModel().getSelectedItem().toString().substring(0, possibleMonths.getSelectionModel().getSelectedItem().toString().indexOf(" "));
        monthLabel.setText(month);
        // figure out which month we are working with in plan
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int planMonthIndex = 0;
        for(int m = 0; m< 12; m++){
            if(month.equals(months[m])){
                planMonthIndex = m;
            }
        }
        //this year is relative to the current year, because that is how the plan listview is set up
        int planYearIndex = 0;
        int year = Integer.parseInt("1" + possibleMonths.getSelectionModel().getSelectedItem().toString().substring(possibleMonths.getSelectionModel().getSelectedItem().toString().indexOf(" ") + 3));
        Date current = new Date();
        planYearIndex = year - current.getYear();
        // now we know what index we need in the plan array
        selectedMonth = plan.get(planYearIndex).get(planMonthIndex);
        //now we need to make the relevant buttons visible
        int count = 0;
        for(int r = 0; r < cal.length; r++){
            for(int c = 0; c < cal[0].length; c++){
                if(count > 30){
                    cal[r][c].setVisible(false);
                }
                else if(!selectedMonth.get(count).getUsed()){
                    cal[r][c].setDisable(true);
                }
                count ++;
            }
        }
    }

    @FXML
    private void goToHome(ActionEvent event) throws IOException {
        //takes the user to the home screen
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
