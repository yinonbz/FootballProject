package presentationLayer;

import businessLayer.Exceptions.MissingInputException;
import businessLayer.Exceptions.NotApprovedException;
import businessLayer.Exceptions.NotFoundInDbException;
import businessLayer.userTypes.SystemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private SystemController systemController;

    @FXML
    private BorderPane rootTest;

    @FXML
    private TextField usernameL;

    @FXML
    private TextField passwordL;

    @FXML

    public void loginClick(ActionEvent actionEvent) {
/*
        if(usernameL.getText().equals("") || passwordL.getText().equals("")){
            showAlert("Empty Fields","Please fill all fields","Please fill all the fields in this form.", Alert.AlertType.INFORMATION);
            return;
        }*/
        try {
            FXMLLoader fxmlLoader = null;
            String user;
            //user = "AR";
            user = systemController.enterLoginDetails(usernameL.getText(), passwordL.getText());
            if (user == null) {
                return;
            }
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
                default:
                    return;
            }

            Parent root1 = null;
            try {
                root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root1, 1166, 666);
                scene.getStylesheets().add("/resources/style.css");
                stage.setScene(scene);
                //IDO ADD
                ControllerInterface Controller = fxmlLoader.getController();
                Controller.setUser(usernameL.getText());
                //
                stage.show();
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MissingInputException e){
            showAlert(e.getMessage(),"Please fill all the fields in this form.", Alert.AlertType.WARNING);
        } catch (NotFoundInDbException e){
            showAlert("Failed to log in",e.getMessage(), Alert.AlertType.WARNING);
        }catch (NotApprovedException e){
            showAlert("Failed to log in",e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String title, String text, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        systemController = SystemController.SystemController();
    }
}
