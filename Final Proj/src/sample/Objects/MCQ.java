package sample.Objects;

public class MCQ {
    private String course;
    private String unit;
    private String ques;
    private String A;
    private String B;
    private String C;
    private String D;
    private String correctChoice;

    public MCQ(String course,String unit, String ques, String A, String B, String C, String D, String correctChoice){
        this.course = course;
        this.unit = unit;
        this.ques = ques;
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.correctChoice = correctChoice;
    }

    public String getUnit(){
        return unit;
    }

    public String getQues(){
        return ques;
    }

    public String getA(){
        return A;
    }

    public String getB(){
        return B;
    }
    public String getC(){
        return C;
    }
    public String getD(){
        return D;
    }
    public String getCorrectChoice(){
        return correctChoice;
    }
}
