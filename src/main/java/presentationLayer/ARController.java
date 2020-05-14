package presentationLayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;

import java.util.LinkedList;

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
    ListView teamsViewL;

    @FXML
    public void switchApprove(){
        ObservableList<String> list = FXCollections.observableArrayList();
        LinkedList<String> teamStringList = new LinkedList<>();
        //import all unapproved team names to teamStringList from DB
        list.addAll(teamStringList);
        teamsViewL.setItems(list);
        teamsViewL.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        approveTeamPane.setVisible(true);
    }
    @FXML
    public void switchCreate(){
        createTeamPane.setVisible(true);
        approveTeamPane.setVisible(false);
    }

    public void clickApprove(ActionEvent actionEvent) {
        ObservableList<String> list = teamsViewL.getSelectionModel().getSelectedItems();
        //approve team
    }
}
