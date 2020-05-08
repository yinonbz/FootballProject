import businessLayer.Utilities.alertSystem.AlertSystem;
import businessLayer.userTypes.SystemController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class main {

    public static void main(String args[]){
        SystemController systemController = SystemController.SystemController();
        systemController.setAlertSystem(new AlertSystem());
        SystemController systemController1 = SystemController.SystemController();


        int a = 2;
    }
}
