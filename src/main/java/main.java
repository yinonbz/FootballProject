import businessLayer.Utilities.alertSystem.AlertSystem;
import businessLayer.userTypes.SystemController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*
        //URL x =getClass().getClassLoader().getResource("sample.fxml");
        URL x =getClass().getResource("sample.fxml");
      //  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
       // Parent root = (Parent) fxmlLoader.load();

        Parent root = FXMLLoader.load(x);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    */

        URL x =getClass().getResource("sample.fxml");
        Parent root = FXMLLoader.load(x);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String args[]){
        SystemController systemController = SystemController.SystemController();
        systemController.setAlertSystem(new AlertSystem());
        SystemController systemController1 = SystemController.SystemController();

        int a = 2;
        launch(args);
    }

    /*
    @Override
    public void start(Stage primaryStage) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
*/
}
