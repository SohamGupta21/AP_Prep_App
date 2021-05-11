package sample.Objects;

import sample.DatabaseManagers.MCQManager;
import sample.DatabaseManagers.UnitManager;
import sample.DatabaseManagers.UserManager;
import sample.DatabaseManagers.WrittenManager;

import java.util.ArrayList;

public class Course {
    private String name;
    private String testDate;
    private ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<MCQ> mcqs = new ArrayList<>();
    private ArrayList<WrittenQuestion> writtenQuestions = new ArrayList<>();
    private ArrayList<User> members = new ArrayList<>();

    public Course(String name, String testDate){
        this.name = name;
        this.testDate = testDate;
    }

    public Course(String name, String testDate, String units, String mcqs, String writtenQuestions, String members){
        this.name = name;
        this.testDate = testDate;
        MCQManager mcqManager = new MCQManager();
        for(String m : splitString(mcqs)){
            this.mcqs.add(mcqManager.getMCQByID(Integer.parseInt(m)));
        }
        UnitManager unitManager = new UnitManager();
        for(String u : splitString(units)){
            this.units.add(unitManager.getUnitById(Integer.parseInt(u)));
        }
        WrittenManager writtenManager = new WrittenManager();
        for(String w : splitString(writtenQuestions)){
            this.writtenQuestions.add(writtenManager.getWrittenById(Integer.parseInt(w)));
        }
        UserManager userManager = new UserManager();
        for(String m : splitString(members)){
            this.members.add(userManager.getUserInfo(Integer.parseInt(m)));
        }
    }

    private ArrayList<String> splitString(String s){
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
