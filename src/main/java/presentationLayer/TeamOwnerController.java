package presentationLayer;

import businessLayer.Exceptions.AlreadyExistException;
import businessLayer.userTypes.SystemController;
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
import javafx.util.converter.IntegerStringConverter;
import serviceLayer.LeagueService;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;



public class TeamOwnerController implements ControllerInterface, Initializable {

     private LeagueService leagueService;

    private SystemController systemController;

    private String userName;


    private ArrayList<TitledPane> notificationPanesCollection;

    @FXML
    private Accordion notificationsPane;

    public TeamOwnerController(String userName) {
        this.userName = userName;
    }

    @Override
    public void setUser(String usernameL) {
        userName = usernameL;
    }
    @FXML
    private Spinner<Integer> yearSpinner;

    @FXML
    private TextField teamNameL;

    @FXML
    private Label titleL;

    @FXML
    private Pane newTeamPane;

    @FXML
    private Label userLable;

    public void addNewTeam(ActionEvent actionEvent) {
        titleL.setText("Add new team");
        newTeamPane.setVisible(true);
        NumberFormat format = NumberFormat.getIntegerInstance();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                ParsePosition parsePosition = new ParsePosition(0);
                // NumberFormat evaluates the beginning of the text
                format.parse(c.getControlNewText(), parsePosition);
                if (parsePosition.getIndex() == 0 ||
                        parsePosition.getIndex() < c.getControlNewText().length()) {
                    // reject parsing the complete text failed
                    return null;
                }
            }
            return c;
        };
        TextFormatter<Integer> priceFormatter = new TextFormatter<Integer>(
                new IntegerStringConverter(), 1900, filter);


        yearSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1900, 10000, Integer.parseInt("1999")));
        yearSpinner.setEditable(true);
        yearSpinner.getEditor().setTextFormatter(priceFormatter);
    }

    public void submitNewTeam(ActionEvent actionEvent) {
        try {
            systemController.sendRequestForTeam(teamNameL.getText(), "" + yearSpinner.getValue(), userName);
            showAlert("Team request has been created successfully","A team request has been created and was passed to the Association Representatives to approve.", Alert.AlertType.INFORMATION);
        } catch (AlreadyExistException e){
            showAlert("Failed to add a new team",e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String title, String text, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leagueService = new LeagueService();
        systemController = SystemController.SystemController();
        userLable.setText("Welcome " + userName);
        leagueService = new LeagueService();
        notificationPanesCollection= new ArrayList<>();

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
            leagueService.removeFromUsersOnline(userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
