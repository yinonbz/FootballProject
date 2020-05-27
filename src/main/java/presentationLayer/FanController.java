package presentationLayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import serviceLayer.LeagueService;
import serviceLayer.SystemService;
import serviceLayer.TeamService;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class FanController implements Initializable,ControllerInterface, Observer {

    private LeagueService leagueService;

    private SystemService systemService;

    private ObservableList<String> listCoaches;

    private ObservableList<String> listPlayers;

    private ObservableList<String> listMatches;

    private String userName;

    private ArrayList<TitledPane> notificationPanesCollection;

    @FXML
    private Accordion notificationsPane;

    @FXML
    private Label userLable;

    @FXML
    private Pane subscribePlayerPane;

    @FXML
    private Pane subscribeCoachPane;

    @FXML
    private Parent subscribeMatchhPane;

    @FXML
    private ListView playersToSubscribe;

    @FXML
    private ListView coachesToSubscribe;

    @FXML
    private ListView matchesToSubscribe;

    @FXML
    private Label titleL;

    @Override
    public void setUser(String usernameL) {
        leagueService = new LeagueService();
        userName = usernameL;
        userLable.setText(usernameL);
        notificationPanesCollection = new ArrayList<>();
        LinkedList<String> messages = leagueService.getOfflineMessages(userName);
        if (messages != null) {
            for (String msg : messages) {
                String title = msg.substring(0,10) + "...";
                String event = msg;
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
        systemService = new SystemService();
        systemService.addObserverForService(this);
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
            systemService.removeFromUsersOnline(userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void selectPlayer(ActionEvent actionEvent) {

        listPlayers = FXCollections.observableArrayList();
        listPlayers.setAll(systemService.getAllPlayers());
        playersToSubscribe.setItems(listPlayers);

        titleL.setText("Subscribe to Players");
        subscribePlayerPane.setVisible(true);
        subscribeCoachPane.setVisible(false);
        subscribeMatchhPane.setVisible(false);
    }

    public void selectCoach(ActionEvent actionEvent) {
        listCoaches = FXCollections.observableArrayList();
        listCoaches.setAll(systemService.getAllCoachesNames());
        coachesToSubscribe.setItems(listCoaches);

        titleL.setText("Subscribe to Coaches");
        subscribePlayerPane.setVisible(false);
        subscribeCoachPane.setVisible(true);
        subscribeMatchhPane.setVisible(false);
    }

    public void subscribePlayersB(ActionEvent actionEvent) {
        try {
            systemService.userRequestToFollowPlayer(userName, playersToSubscribe.getSelectionModel().getSelectedItem().toString());
            showAlert("Success","You have subscribed for the selected Player.", Alert.AlertType.INFORMATION);
        } catch (NullPointerException e){
            showAlert("Warning","Please select a Player to subscribe.", Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String title, String text, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void subscribeCoachesB(ActionEvent actionEvent) {
        try {
            systemService.userRequestToFollowCoach(userName, coachesToSubscribe.getSelectionModel().getSelectedItem().toString());
            showAlert("Success","You have subscribed for the selected Coach.", Alert.AlertType.INFORMATION);
        } catch (NullPointerException e){
            showAlert("Warning","Please select a Coach to subscribe.", Alert.AlertType.WARNING);
        }
    }

    public void subscribeMatchB(ActionEvent actionEvent) {
        try {
            systemService.userRequestToFollowMatch(userName, matchesToSubscribe.getSelectionModel().getSelectedItem().toString());
            showAlert("Success","You have subscribed for the selected Match.", Alert.AlertType.INFORMATION);
        } catch (NullPointerException e){
            showAlert("Warning","Please select a Match to subscribe.", Alert.AlertType.WARNING);
        }
    }

    public void selectMatch(ActionEvent actionEvent) {
        listMatches = FXCollections.observableArrayList();
        listMatches.setAll(systemService.getAllMatchesInDB());
        matchesToSubscribe.setItems(listMatches);

        titleL.setText("Subscribe to Matches");
        subscribePlayerPane.setVisible(false);
        subscribeCoachPane.setVisible(false);
        subscribeMatchhPane.setVisible(true);
    }
}
