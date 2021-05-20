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

    public void addMultipleChoiceQuestions(ArrayList<MCQ> mcq){
        for(MCQ m : mcq){
            multipleChoiceQuestions.add(m);
        }
    }

    public void addWrittenQuestions(ArrayList<WrittenQuestion> writtenQuestions){
        for(WrittenQuestion w : writtenQuestions){
            this.writtenQuestions.add(w);
        }
    }

}
