package sample.Objects;

import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Date;

public class DayPlan {
    Date date = new Date();
    boolean isTestDate = false;
    boolean used = false;
    ArrayList<String> coursesToTest = new ArrayList<>();
    ArrayList<MCQ> multipleChoiceQuestions = new ArrayList<>();
    ArrayList<WrittenQuestion> writtenQuestions = new ArrayList<>();

    public void addTestDate(boolean input, String courseToTest){
        if(coursesToTest.size() > 0){
            used = true;
        }
        isTestDate = input;
        this.coursesToTest.add(courseToTest);
    }

    public ArrayList<String> getCoursesToTest(){
        return coursesToTest;
    }
    public boolean getIsTestDate(){
        return isTestDate;
    }

    public void addMultipleChoiceQuestions(ArrayList<MCQ> mcq){
        if(mcq.size() > 0){
            used = true;
        }
        for(MCQ m : mcq){
            multipleChoiceQuestions.add(m);
        }
    }

    public ArrayList<MCQ> getMultipleChoiceQuestions(){
        return multipleChoiceQuestions;
    }

    public void addWrittenQuestions(ArrayList<WrittenQuestion> writtenQuestions){
        if(writtenQuestions.size() > 0){
            used = true;
        }
        for(WrittenQuestion w : writtenQuestions){
            this.writtenQuestions.add(w);
        }
    }

    public ArrayList<WrittenQuestion> getWrittenQuestions(){
        return writtenQuestions;
    }

    public boolean getUsed(){
        return used;
    }

    public String toString(){
        //this function is for testing
        String answer = "Tests: ";
        for(String t : coursesToTest){
            answer += t;
        }
        answer += " MCQs: ";
        for(MCQ m : multipleChoiceQuestions){
            answer += m.getQues();
        }
        answer += " Written ";
        for(WrittenQuestion w : writtenQuestions){
            answer += w.getPrompt();
        }
        return answer;
    }

}
