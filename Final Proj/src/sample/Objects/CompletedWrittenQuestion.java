package sample.Objects;

public class CompletedWrittenQuestion {
    private String prompt;
    private int writtenId;
    private int userId;
    private int graderId;
    private String userAnswer;
    public CompletedWrittenQuestion(String prompt, int writtenId, int userId, String userAnswer){
        this.prompt = prompt;
        this.writtenId = writtenId;
        this.userId = userId;
        this.userAnswer = userAnswer;
    }

    public CompletedWrittenQuestion(String prompt, int writtenId, int userId, String userAnswer, int graderId){
        this.prompt = prompt;
        this.writtenId = writtenId;
        this.userId = userId;
        this.userAnswer = userAnswer;
        this.graderId = graderId;
    }

    public String getPrompt(){return prompt;}
    public int getWrittenId(){return writtenId;}
    public int getUserId(){return userId;}
    public int getGraderId(){return graderId;}
    public String getUserAnswer(){return userAnswer;}
}
