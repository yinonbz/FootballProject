package presentationLayer;

import businessLayer.Exceptions.AlreadyExistException;
import businessLayer.userTypes.SystemController;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.util.converter.IntegerStringConverter;
import serviceLayer.LeagueService;
import serviceLayer.SystemService;
import serviceLayer.TeamService;

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

     private TeamService teamService;

     private SystemService systemService;

    private String userName;

    private ObservableList<String> listTeams;

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
    private Spinner<Integer> salarySpinner;

    @FXML
    private TextField teamNameL;

    @FXML
    private Label titleL;

    @FXML
    private Pane newTeamPane;

    @FXML
    private Label userLable;

    @FXML
    private Pane addManagerPane;

    @FXML
    private ListView teamsViewL;

    @FXML
    private ListView teamManagersViewL;

    @FXML
    private TextField searchTeam;

    @FXML
    private TextField searchTeamManagers;

    @FXML
    private ComboBox permissionCombo;

    public void addNewTeam(ActionEvent actionEvent) {
        titleL.setText("Add new team");
        newTeamPane.setVisible(true);
        addManagerPane.setVisible(false);
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
            systemService.sendRequestForTeam(teamNameL.getText(), "" + yearSpinner.getValue(), userName);
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
        teamService = new TeamService();
        systemService = new SystemService();
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

        searchTeam.setPromptText("Search");
        searchTeam.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldVal,
                                Object newVal) {
                search((String) oldVal, (String) newVal, teamsViewL);
            }
        });

        searchTeamManagers.setPromptText("Search");
        searchTeamManagers.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldVal,
                                Object newVal) {
                search((String) oldVal, (String) newVal, teamManagersViewL);
            }
        });
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

    public void addTeamManager(){
        addManagerPane.setVisible(true);
        newTeamPane.setVisible(false);

        listTeams = FXCollections.observableArrayList();
        listTeams.setAll(teamService.getTeamsOfTeamOwner(userName)); //get only the team owner's teams
        teamsViewL.setItems(listTeams);

        listTeams = FXCollections.observableArrayList();
        listTeams.setAll(systemService.getSystemSubscribers()); //get only the team owner's teams
        teamManagersViewL.setItems(listTeams);

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
                new IntegerStringConverter(), 0, filter);

        salarySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, Integer.parseInt("5000")));
        salarySpinner.setEditable(true);
        salarySpinner.getEditor().setTextFormatter(priceFormatter);

        permissionCombo.getItems().setAll(
                "Finance",
                "Player Oriented",
                "Coach Oriented",
                "General"
        );
    }

    public void search(String oldVal, String newVal, ListView listView) {

        if (oldVal != null && (newVal.length() < oldVal.length())) {
            listView.setItems(listTeams);
        }
        String value = newVal.toUpperCase();
        ObservableList<String> subentries = FXCollections.observableArrayList();
        for (Object entry : listView.getItems()) {
            boolean match = true;
            String entryText = (String) entry;
            if (!entryText.toUpperCase().contains(value)) {
                match = false;
            }
            if (match) {
                subentries.add(entryText);
            }
        }
        listView.setItems(subentries);
    }

    public void addTeamManagerB(){
        try {
            String teamName = teamsViewL.getSelectionModel().getSelectedItem().toString();
            String teamManagerName = teamManagersViewL.getSelectionModel().getSelectedItem().toString();
            String pm = permissionCombo.getValue().toString();
            pm = pm.replace(" ", "");
            pm = pm.toUpperCase();
            teamService.addManager(userName, teamManagerName, pm, teamName, "" + salarySpinner.getValue());
        }catch (NullPointerException e){
            showAlert("Warning","Please fill the form completely before adding a new Team Manager.", Alert.AlertType.WARNING);
        }catch (RuntimeException e){
            showAlert("Warning",e.getMessage(), Alert.AlertType.WARNING);
        }
    }
}
