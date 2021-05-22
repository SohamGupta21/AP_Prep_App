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
    //adds testdates and marks used if applicable, this tells the code that this plan object is not empty
    public void addTestDate(boolean input, String courseToTest){
        used = true;
        isTestDate = input;
        this.coursesToTest.add(courseToTest);
    }
    //getter methods
    public ArrayList<String> getCoursesToTest(){
        return coursesToTest;
    }
    public boolean getIsTestDate(){
        return isTestDate;
    }
    //adds multiple choice questions and marks used
    public void addMultipleChoiceQuestions(ArrayList<MCQ> mcq){
        if(mcq.size() > 0){
            used = true;
        }
        for(MCQ m : mcq){
            multipleChoiceQuestions.add(m);
        }
    }
    //get methods
    public ArrayList<MCQ> getMultipleChoiceQuestions(){
        return multipleChoiceQuestions;
    }
    //adds written questions and marks used
    public void addWrittenQuestions(ArrayList<WrittenQuestion> writtenQuestions){
        if(writtenQuestions.size() > 0){
            used = true;
        }
        for(WrittenQuestion w : writtenQuestions){
            this.writtenQuestions.add(w);
        }
    }
    //gets the written questions
    public ArrayList<WrittenQuestion> getWrittenQuestions(){
        return writtenQuestions;
    }
    //getter
    public boolean getUsed(){
        return used;
    }
    //converts the plan object into a readable format, mainly for testing
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
