package sample.Objects;

import javafx.scene.control.PasswordField;

public class CompletedQuestion {
    private int questionId;
    private int userId;
    private String answer;
    private String correctAnswer;
    public CompletedQuestion(int questionId, int userId, String answer, String correctAnswer){
        this.questionId = questionId;
        this.userId = userId;
        this.answer = answer;
        this.correctAnswer = correctAnswer;
    }

    public int getQuestionId(){
        return questionId;
    }

    public int getUserId(){
        return userId;
    }

    public String getAnswer() {
        return answer;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }
}
