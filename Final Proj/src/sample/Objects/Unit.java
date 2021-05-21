package sample.Objects;

public class Unit {
    private String name;
    private int id;
    private String mcqs;
    private String written;
    //constructors
    public Unit(String name){
        this.name = name;
    }
    public Unit(String name, int id, String m, String w){
        this.name = name;
        this.id = id;
        this.mcqs = m;
        this.written = w;
    }
    public Unit(){}

    //getter functions
    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

    public String getMcqs(){
        return mcqs;
    }
    public String getWritten(){return written;}
}
