package sample.DatabaseManagers;

import sample.Objects.Course;
import sample.Objects.MCQ;

import java.sql.*;
import java.util.ArrayList;

public class MCQManager {
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost/FinalProject";
    private final String USER = "root";
    private final String PASS = "MySQL2021";
    //registers a new MCQ question in database
    public void databaseRegistration(String course, String unit, String question, String A, String B, String C, String D, String correctChoice){

        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost/FinalProject";
        final String USER = "root";
        final String PASS = "MySQL2021";

        Connection conn = null;
        Statement stmt = null;

        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            int rowInd = getRowsOfTable() + 1;
            sql = "INSERT INTO MultipleChoiceQuestions (idMCQ, Course, Unit, Question, A, B, C, D, CorrectChoice) " + "VALUES("+ rowInd + ",'"+ course + "','" + unit + "','"+ question + "','"  + A + "','" + B + "','"+ C + "','" + D + "','" + correctChoice + "')";
            stmt.executeUpdate(sql);

            //STEP 6: Clean-up environment
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
    //gets the number of rows in the table
    public int getRowsOfTable(){
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost/FinalProject";
        final String USER = "root";
        final String PASS = "MySQL2021";

        Connection conn = null;
        Statement stmt = null;

        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT COUNT(*) FROM MultipleChoiceQuestions";
            ResultSet rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
                return rs.getInt(1);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return 0;
    }

    //returns MCQ object from an id
    public MCQ getMCQByID(int mcqId){
        Connection conn = null;
        Statement stmt = null;

        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT idMCQ, Course, Unit, Question, A, B, C, D, CorrectChoice FROM MultipleChoiceQuestions";
            ResultSet rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id = rs.getInt("idMCQ");
                if(id == mcqId){
                    MCQ answer = new MCQ(rs.getString("Course"), rs.getString("Unit"), rs.getString("Question"), rs.getString("A"), rs.getString("B"), rs.getString("C"), rs.getString("D"), rs.getString("CorrectChoice"));
                    return answer;
                }
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return null;
    }
    //gets the MCQ object by the prompt, unit and course
    public MCQ getMCQByPromptUnitCourse(String prompt, String unit, String course){

        Connection conn = null;
        Statement stmt = null;

        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT idMCQ,Course, Unit, Question, A, B, C, D, CorrectChoice FROM MultipleChoiceQuestions";
            ResultSet rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
                if(rs.getString("Question").equals(prompt)){
                    if(rs.getString("Unit").equals(unit)){
                        if(rs.getString("Course").equals(course)){
                            MCQ temp = new MCQ(course, unit, prompt, rs.getString("A"), rs.getString("B"), rs.getString("C"), rs.getString("D"), rs.getString("CorrectChoice"), rs.getInt("idMCQ"));
                            return temp;
                        }
                    }
                }
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return null;
    }
    //gets all mcqs that have a certain unit and course
    public ArrayList<MCQ> getMCQsByUnitCourse(String unit, String course){
        ArrayList<MCQ> answer = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;

        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT idMCQ,Course, Unit, Question, A, B, C, D, CorrectChoice FROM MultipleChoiceQuestions";
            ResultSet rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
                if(rs.getString("Unit").equals(unit)){
                    if(rs.getString("Course").equals(course)){
                        MCQ temp = new MCQ(course, unit, rs.getString("Question"), rs.getString("A"), rs.getString("B"), rs.getString("C"), rs.getString("D"), rs.getString("CorrectChoice"), rs.getInt("idMCQ"));
                        answer.add(temp);
                    }
                }

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return answer;
    }


}
