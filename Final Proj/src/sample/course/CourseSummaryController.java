package sample.course;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseSummaryController {

    @FXML
    private void goToHome(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../home/home.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    @FXML
    private void goToWrittenReview(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("../review/writtenreview.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    @FXML
    private void goToMultipleChoiceReview(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("../review/multiplechoicereview.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
}
