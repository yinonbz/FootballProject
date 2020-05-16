package presentationLayer;

import businessLayer.userTypes.SystemController;
import dataLayer.DataBaseValues2;
import dataLayer.DemoDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import serviceLayer.LeagueService;
import serviceLayer.SystemService;

import java.net.URL;
import java.util.LinkedList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //URL url = new File("Login.fxml").toURI().toURL();
        URL url =getClass().getClassLoader().getResource("Login.fxml");
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root, 356, 700);
        scene.getStylesheets().add("/css/login.css");
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();

        ////////////////////////////////////////////////////////////////STUB!! Delete after cpnnecting the real DB
        SystemService testingSystemService = new SystemService();
        testingSystemService.initializeSystem("admin");
        LeagueService leagueService = new LeagueService();
        DataBaseValues2 testingDBValues = new DataBaseValues2();
        DemoDB testingDB = testingDBValues.getDB();
        SystemController systemController = SystemController.SystemController();
        systemController.connectToDB(testingDB);
        LinkedList<String> temp = new LinkedList<>();
        temp.add("HapoelBeerSheva1");
        temp.add("1888");
        temp.add("Tomer");
        testingDB.addUnconfirmedTeamsToDB("HapoelBeerSheva",temp);
        ////////////////////////////////////////////////////////////////STUB!! Delete after cpnnecting the real DB
    }


    public static void main(String[] args) {
        launch(args);
    }

}
