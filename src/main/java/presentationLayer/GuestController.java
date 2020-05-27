package presentationLayer;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.IOException;

public class GuestController{


    @FXML
    Pane addTeamP;
    @FXML
    Pane removeTeamP;

    @FXML
    public void addTeam(){
        addTeamP.setVisible(true);
        removeTeamP.setVisible(false);
    }

    @FXML
    public void removeTeam(){
        addTeamP.setVisible(false);
        removeTeamP.setVisible(true);
    }

    @Override
    public void setUser(String usernameL) {

    }
}
