package presentationLayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import serviceLayer.LeagueService;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class TeamManagerController implements Initializable,ControllerInterface, Observer {

    private LeagueService leagueService;

    private String userName;

    private ArrayList<TitledPane> notificationPanesCollection;

    @FXML
    private Accordion notificationsPane;



    @FXML
    private Label userLable;

    @Override
    public void setUser(String usernameL) {
        userLable.setText(usernameL);
        userName = usernameL;
        leagueService = new LeagueService();
        notificationPanesCollection= new ArrayList<>();

        LinkedList<String> messages = leagueService.getOfflineMessages(userName);
        if(messages != null) {
            for (String msg : messages) {
                String title = msg.split(",")[1];
                String event = msg.split(",")[0];
                AnchorPane newPanelContent = new AnchorPane();
                newPanelContent.getChildren().add(new Label(event));
                TitledPane pane = new TitledPane(title, newPanelContent);
                notificationPanesCollection.add(pane);
            }
        }
        notificationsPane.getPanes().setAll(notificationPanesCollection);

    }

    @Override
    public void update(Observable o, Object arg) {
        LinkedList<String> message = ((LinkedList<String>)arg);
        notificationPanesCollection= new ArrayList<>();
        AnchorPane newPanelContent = new AnchorPane();
        newPanelContent.getChildren().add(new Label(message.get(1)));
        TitledPane pane = new TitledPane(message.get(0), newPanelContent);
        notificationsPane.getPanes().add(pane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void logoutB(ActionEvent actionEvent) {
        Parent root1 = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root1, 356, 700);
            scene.getStylesheets().add("/css/login.css");
            stage.setScene(scene);
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            leagueService.removeFromUsersOnline(userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
