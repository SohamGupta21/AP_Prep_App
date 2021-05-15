package sample.Objects;

public class WrittenQuestion {

    private String prompt;
    private String unit;
    private int ID;

    public WrittenQuestion(String prompt, String unit){
        this.prompt = prompt;
        this.unit = unit;
    }

    public WrittenQuestion(String prompt, String unit, int id){
        this.prompt = prompt;
        this.unit = unit;
        this.ID = id;
    }

    public String getPrompt(){
        return prompt;
    }

    public String getUnit(){
        return unit;
    }

    public int getID(){
        return ID;
    }

}
