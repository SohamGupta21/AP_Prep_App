package sample.quiz;

import sample.Objects.Course;
import sample.Objects.MCQ;
import sample.Objects.Unit;
import sample.Objects.User;

public class MultipleChoiceController {

    private User user;
    private Course course;
    private Unit unit;
    private MCQ mcq;

    public void setData(User user, Course course, Unit unit, MCQ mcq){
        this.user = user;
        this.unit = unit;
        this.course = course;
        this.mcq = mcq;
        System.out.println(mcq.getQues());
    }

}
