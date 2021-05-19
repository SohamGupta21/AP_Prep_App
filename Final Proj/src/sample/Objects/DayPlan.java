package sample.Objects;

import java.util.ArrayList;
import java.util.Date;

public class DayPlan {
    Date date = new Date();
    boolean isTestDate = false;
    String courseToTest;
    ArrayList<MCQ> multipleChoiceQuestions = new ArrayList<>();
    ArrayList<WrittenQuestion> writtenQuestions = new ArrayList<>();

    public void setIsTestDate(boolean input, String courseToTest){
        isTestDate = input;
        this.courseToTest = courseToTest;
    }
    public boolean getIsTestDate(){
        return isTestDate;
    }

    public void setMultipleChoiceQuestions(ArrayList<MCQ> mcq){
        multipleChoiceQuestions = mcq;
    }

    public void setWrittenQuestions(ArrayList<WrittenQuestion> writtenQuestions){
        this.writtenQuestions = writtenQuestions;
    }

}
