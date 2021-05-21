package sample.Objects;

public class CompletedWrittenQuestion {
    private String prompt;
    private int writtenId;
    private int userId;
    private int graderId;
    private String graderComments;
    private String userAnswer;
    private int numberGrade;
    public CompletedWrittenQuestion(String prompt, int writtenId, int userId, String userAnswer){
        //constructor
        this.prompt = prompt;
        this.writtenId = writtenId;
        this.userId = userId;
        this.userAnswer = userAnswer;
    }

    public CompletedWrittenQuestion(String prompt, int writtenId, int userId, String userAnswer, int graderId, String graderComments, int numberGrade){
        //constructor with more information
        this.prompt = prompt;
        this.writtenId = writtenId;
        this.userId = userId;
        this.userAnswer = userAnswer;
        this.graderId = graderId;
        this.graderComments = graderComments;
        this.numberGrade = numberGrade;
    }
    //getter methods
    public String getPrompt(){return prompt;}
    public int getWrittenId(){return writtenId;}
    public int getUserId(){return userId;}
    public int getGraderId(){return graderId;}
    public String getGraderComments(){return graderComments;}
    public String getUserAnswer(){return userAnswer;}
    public int getNumberGrade(){
        return numberGrade;
    }
}
