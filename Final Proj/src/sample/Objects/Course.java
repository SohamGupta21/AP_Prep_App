package sample.Objects;

import javafx.scene.control.PasswordField;

import java.util.ArrayList;

public class Course {
    private String name;
    private String testDate;
    private ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<MCQ> mcqs = new ArrayList<>();
    private ArrayList<WrittenQuestion> writtenQuestions = new ArrayList<>();

    public Course(String name, String testDate){
        this.name = name;
        this.testDate = testDate;
    }

    public void addUnits(ArrayList<Unit> unitsIn){
        for(Unit u: unitsIn){
            units.add(u);
        }
    }

    public ArrayList<Unit> getUnits(){
        return units;
    }


    public void addMCQs(ArrayList<MCQ> mcqsIn){
        for(MCQ m : mcqsIn){
            mcqs.add(m);
        }
    }

    public ArrayList<MCQ> getMcqs(){
        return mcqs;
    }

    public void addWritten(ArrayList<WrittenQuestion> writtenIn){
        for(WrittenQuestion w : writtenIn){
            writtenQuestions.add(w);
        }
    }

    public ArrayList<WrittenQuestion> getWrittenQuestions(){
        return writtenQuestions;
    }

    public void setName(String n){
        this.name = n;
    }

    public void setTestDate(String testDate){
        this.testDate = testDate;
    }

    public void setUnits(ArrayList<Unit> units){
        this.units = units;
    }
    public String getName(){
        return name;
    }

    public String getTestDate(){
        return testDate;
    }
}
