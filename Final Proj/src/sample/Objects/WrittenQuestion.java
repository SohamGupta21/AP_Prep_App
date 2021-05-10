package sample.Objects;

public class WrittenQuestion {

    private String prompt;
    private String unit;

    public WrittenQuestion(String prompt, String unit){
        this.prompt = prompt;
        this.unit = unit;
    }

    public String getPrompt(){
        return prompt;
    }

    public String getUnit(){
        return unit;
    }

}
