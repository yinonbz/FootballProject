package presentationLayer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.awt.*;
import java.io.IOException;

public class Controller {

    @FXML
    private BorderPane rootTest;

    @FXML
    public void loginClick(){
        System.out.println("OK");
        String user = "Player";
        switch (user){
            case "Player":
                BorderPane bp = null;
                try {
                    bp = FXMLLoader.load(getClass().getResource("Guest.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                rootTest.getChildren().setAll(bp);
        }
    }
}
