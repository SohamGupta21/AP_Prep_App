package sample.Objects;

public class MCQ {
    private final String course;
    private final String unit;
    private final String ques;
    private final String A;
    private final String B;
    private final String C;
    private final String D;
    private final String correctChoice;

    public MCQ(String course, String unit, String ques, String A, String B, String C, String D, String correctChoice) {
        this.course = course;
        this.unit = unit;
        this.ques = ques;
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.correctChoice = correctChoice;
    }

    public String getUnit() {
        return unit;
    }

    public String getQues() {
        return ques;
    }

    public String getA() {
        return A;
    }

    public String getB() {
        return B;
    }

    public String getC() {
        return C;
    }

    public String getD() {
        return D;
    }

    public String getCorrectChoice() {
        return correctChoice;
    }
}
