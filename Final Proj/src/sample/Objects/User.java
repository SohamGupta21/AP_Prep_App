package sample.Objects;

public class User {
    private int id;
    private String userName;
    private String courses;
    private String classmates;

    public User(int id, String userName, String courses, String classmates){
        this.id = id;
        this.userName = userName;
        this.courses = courses;
        this.classmates = classmates;
    }

    public String getName(){
        return userName;
    }

    public int getId() {
        return id;
    }

    public void addCourse(int id){
        if(courses == null) courses = "";
        courses += id + "*";
    }

    public void addClassmate(int id){
        if(classmates == null) classmates = "";
        classmates += id + "*";
    }

    public String getCourses(){
        return courses;
    }

    public String getClassmates(){
        return classmates;
    }
}
