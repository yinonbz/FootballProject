package presentationLayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //URL url = new File("Login.fxml").toURI().toURL();
        URL url =getClass().getClassLoader().getResource("Login.fxml");
        Parent root = FXMLLoader.load(url);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 358, 693));
        primaryStage.show();
    }


    public static void main(String[] args) {
        System.out.println("Hello, World");
        launch(args);
    }
}
