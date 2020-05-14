package presentationLayer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;

public class LoginController {

    @FXML
    private BorderPane rootTest;

    @FXML
    private TextField usernameL;

    @FXML
    private TextField passwordL;

    @FXML

    public void loginClick(ActionEvent actionEvent) {

        if(usernameL.getText().equals("") || passwordL.getText().equals("")){
            alertFieldsEmpty();
            return;
        }

        FXMLLoader fxmlLoader = null;
        String user = "TeamOwner";
        switch (user) {
            case "Admin":
                fxmlLoader = new FXMLLoader(getClass().getResource("/resources/Admin.fxml"));

                break;
            case "AR":
                fxmlLoader = new FXMLLoader(getClass().getResource("/resources/AR.fxml"));
                break;
            case "Coach":
                fxmlLoader = new FXMLLoader(getClass().getResource("/resources/Coach.fxml"));
                break;
            case "Fan":
                fxmlLoader = new FXMLLoader(getClass().getResource("/resources/Fan.fxml"));
                break;
            case "Guest":
                fxmlLoader = new FXMLLoader(getClass().getResource("/resources/Guest.fxml"));
                break;
            case "Player":
                fxmlLoader = new FXMLLoader(getClass().getResource("/resources/Player.fxml"));
                break;
            case "Referee":
                fxmlLoader = new FXMLLoader(getClass().getResource("/resources/Referee.fxml"));
                break;
            case "TeamManager":
                fxmlLoader = new FXMLLoader(getClass().getResource("/resources/TeamManager.fxml"));
                break;
            case "TeamOwner":
                fxmlLoader = new FXMLLoader(getClass().getResource("/resources/TeamOwner.fxml"));
                break;
        }

        Parent root1 = null;
        try {
                root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root1,700, 700);
            scene.getStylesheets().add("/resources/style.css");
            stage.setScene(scene);
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void alertFieldsEmpty(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Empty Fields");
        alert.setHeaderText("Please fill all fields");
        alert.setContentText("Please fill all the fields in this form.");

        alert.showAndWait();
    }
}
