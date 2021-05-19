package sample.Objects;

import java.util.Date;

public class DayPlan {
    Date date = new Date();
    boolean isTestDate = false;
    String courseToTest;

    public void setIsTestDate(boolean input, String courseToTest){
        isTestDate = input;
        this.courseToTest = courseToTest;
    }
    public boolean getIsTestDate(){
        return isTestDate;
    }
}
