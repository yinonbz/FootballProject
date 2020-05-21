package presentationLayer;

import businessLayer.userTypes.Administration.Player;
import javafx.fxml.FXML;
import serviceLayer.SystemService;

public class PlayerController implements ControllerInterface{
    SystemService service = new SystemService();
    @Override
    public void setUser(String usernameL) {

    }

    @FXML
    public void updatePage(){
        String s = "updateTextArea";
        //Player player = service.getPlayerUser(user);
        //player.updatePage(s);
    }

}
