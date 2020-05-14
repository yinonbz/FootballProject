package presentationLayer;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class ARController {


    @FXML
    Pane approveOrCreatePane;
    @FXML
    Pane arHomeMenuPane;
    @FXML
    Pane createTeamPane;
    @FXML
    Pane approveTeamPane;

    @FXML
    public void switchApproveCreate(){
        approveOrCreatePane.setVisible(true);
        arHomeMenuPane.setVisible(false);
    }
    @FXML
    public void switchApprove(){
        approveTeamPane.setVisible(true);
        createTeamPane.setVisible(false);
    }
    @FXML
    public void switchCreate(){
        createTeamPane.setVisible(true);
        approveTeamPane.setVisible(false);
    }
}
