package sample.review;

import sample.Objects.CompletedWrittenQuestion;
import sample.Objects.Course;
import sample.Objects.Unit;
import sample.Objects.User;

public class WrittenReviewController {

    private User user;
    private Course course;
    private Unit unit;
    private CompletedWrittenQuestion completedWrittenQuestion;

    public void setData(User user, Course course, Unit unit, CompletedWrittenQuestion completedWrittenQuestion){
        this.user = user;
        this.course = course;
        this.unit = unit;
        this.completedWrittenQuestion = completedWrittenQuestion;
    }
}
