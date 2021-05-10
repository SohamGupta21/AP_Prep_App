package sample.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.DatabaseManagers.UserManager;
import sample.home.HomeController;

import java.io.IOException;
import java.sql.*;


public class LoginController {


    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Label lblDisplay;

    @FXML
    AnchorPane ap;

    private UserManager userManager;

    @FXML
    private void initialize(){
        userManager = new UserManager();
    }
    @FXML
    private void goToRegisterScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("registration.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    @FXML
    private void goToHomeScreen(ActionEvent event) throws IOException{
        if(userManager.checkUserInfoRequest(usernameField.getText(), passwordField.getText())){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../home/home.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(scene);

            HomeController homeController = loader.getController();
            homeController.setData(usernameField.getText());

            window.show();


        }
    }

    private void SQLQueryRequest(String request, String colName){
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
            sql = request;
            ResultSet rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
//                String username  = rs.getString(colName);
//                answer.add(username);
//                //Display values
//                System.out.println("Username: " + username);
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
    }



}
