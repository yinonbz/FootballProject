package presentationLayer;

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

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PlayerController implements Initializable,ControllerInterface, Observer {

    private LeagueService leagueService;

    private SystemService systemService;

    private String userName;

    private ArrayList<TitledPane> notificationPanesCollection;


    @FXML
    private Accordion notificationsPane;

    @FXML
    private Label userLable;
    @FXML
    private TextField nameField;
    @FXML
    private DatePicker bDate;
    @FXML
    private Pane namePane;
    @FXML
    private Pane bDatePane;
    @FXML
    private Pane uploadPane;
    @FXML
    private Label titleL;
    @FXML
    private TextArea postPlayer;
    @FXML
    public void changeBDate(){
        try {
            String date = bDate.getValue().toString();
            if(date!=null &&!date.equals("")) {
                systemService.updatePlayerBDate(date.replace("-","/"), userName);
                success("changing BirthDate to " +date );
            }else{
                missingAlert();
            }
        }catch (Exception e){
            missingAlert();
        }
     }
    @FXML
    public void changeName(){
        try {
            String name = nameField.getText();
            if(name!=null &&!name.equals("")) {
                systemService.updatePlayerName(name, userName);
                success("changing name to " +name );
            }else{
                missingAlert();
            }
        }catch (Exception e){
            missingAlert();
        }
    }
    @FXML
    public void switchBDatePane(){
        titleL.setText("Edit Birthday");
        bDatePane.setVisible(true);
        namePane.setVisible(false);
        uploadPane.setVisible(false);
    }
    @FXML
    public void switchNamePane(){
        titleL.setText("Edit Name");
        bDatePane.setVisible(false);
        namePane.setVisible(true);
        uploadPane.setVisible(false);

    }
    @FXML
    public void switchUploadPane(){
        titleL.setText("Upload Contact");
        bDatePane.setVisible(false);
        namePane.setVisible(false);
        uploadPane.setVisible(true);

    }
    @FXML
    public void updatePostPage(){
        try {
            String post = postPlayer.getText();
            if(post!=null &&!post.equals("")) {
                systemService.updatePlayerPost(userName,post);
                success("next iteration");
            }else{
                missingAlert();
            }
        }catch (Exception e){
            missingAlert();
        }
    }
    @Override
    public void setUser(String usernameL) {
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
        leagueService = new LeagueService();
        systemService = new SystemService();
        notificationPanesCollection= new ArrayList<>();
        systemService.addObserverForService();
        //systemService.addObserverForService(this);
        LinkedList<String> messages = leagueService.getOfflineMessages(userName);
        if(messages != null) {
            for (String msg : messages) {
                String title = msg.split(",")[0];
                String event = msg.split(",")[1];
                AnchorPane newPanelContent = new AnchorPane();
                newPanelContent.getChildren().add(new Label(event));
                TitledPane pane = new TitledPane(title, newPanelContent);
                notificationPanesCollection.add(pane);
            }
        }
        notificationsPane.getPanes().setAll(notificationPanesCollection);
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
    private void missingAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Empty Fields");
        alert.setHeaderText("Please fill all fields");
        alert.setContentText("Please fill all the fields in this form.");
        alert.showAndWait();
    }
    private void success(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(text + " was succeeded");
        alert.setHeaderText("success");
        alert.setContentText(text + " was succeeded");
        alert.showAndWait();
    }

}
