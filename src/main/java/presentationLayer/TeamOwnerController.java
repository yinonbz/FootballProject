package presentationLayer;

import businessLayer.Exceptions.AlreadyExistException;
import businessLayer.Exceptions.NotFoundInDbException;
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
import java.util.NoSuchElementException;
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

    @FXML
    private Label userLabel;

    @Override
    public void setUser(String usernameL) {
        userName = usernameL;
        userLabel.setText(usernameL);
        notificationPanesCollection = new ArrayList<>();

        LinkedList<String> messages = leagueService.getOfflineMessages(userName);
        if (messages != null) {
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
    private ListView teamsViewL2;

    @FXML
    private ListView closedTeamsViewL;

    @FXML
    private ListView teamManagersViewL;

    @FXML
    private TextField searchTeam;

    @FXML
    private TextField searchTeam2;

    @FXML
    private TextField searchTeam3;

    @FXML
    private TextField searchTeamManagers;

    @FXML
    private ComboBox permissionCombo;

    @FXML
    private Pane closeTeamPane;

    @FXML
    private Pane reopenTeamPane;




    public void addNewTeam(ActionEvent actionEvent) {
        titleL.setText("Add new team");
        newTeamPane.setVisible(true);
        addManagerPane.setVisible(false);
        closeTeamPane.setVisible(false);
        reopenTeamPane.setVisible(false);

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
            showAlert("Team request has been created successfully", "A team request has been created and was passed to the Association Representatives to approve.", Alert.AlertType.INFORMATION);
        } catch (AlreadyExistException e) {
            showAlert("Failed to add a new team", e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String title, String text, Alert.AlertType alertType) {
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


    public void addTeamManager() {
        titleL.setText("Add Team Manager");
        addManagerPane.setVisible(true);
        newTeamPane.setVisible(false);
        closeTeamPane.setVisible(false);
        reopenTeamPane.setVisible(false);


        listTeams = FXCollections.observableArrayList();
        listTeams.setAll(teamService.getActiveTeamOfTeamOwner(userName)); //get only the active team owner's teams
        teamsViewL.setItems(listTeams);

        listTeams = FXCollections.observableArrayList();
        listTeams.setAll(systemService.getSystemSubscribers()); //get only the team owner's teams
        teamManagersViewL.setItems(listTeams);

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

    public void addTeamManagerB() {
        try {
            String teamName = teamsViewL.getSelectionModel().getSelectedItem().toString();
            String teamManagerName = teamManagersViewL.getSelectionModel().getSelectedItem().toString();
            String pm = permissionCombo.getValue().toString();
            pm = pm.replace(" ", "");
            pm = pm.toUpperCase();
            teamService.addManager(userName, teamManagerName, pm, teamName, "" + salarySpinner.getValue());
            showAlert("Success","The user was assigned as a Team Manager for the selected team.", Alert.AlertType.INFORMATION);
        } catch (NullPointerException e) {
            showAlert("Warning", "Please fill the form completely before adding a new Team Manager.", Alert.AlertType.WARNING);
        } catch (RuntimeException e) {
            showAlert("Warning", e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    public void closeTeam(ActionEvent actionEvent) {
        titleL.setText("Close Team");
        addManagerPane.setVisible(false);
        newTeamPane.setVisible(false);
        closeTeamPane.setVisible(true);
        reopenTeamPane.setVisible(false);

        listTeams = FXCollections.observableArrayList();
        listTeams.setAll(teamService.getActiveTeamOfTeamOwner(userName)); //get only the team owner's teams
        teamsViewL2.setItems(listTeams);

        searchTeam2.setPromptText("Search");
        searchTeam2.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldVal,
                                Object newVal) {
                search((String) oldVal, (String) newVal, teamsViewL2);
            }
        });

    }

    public void closeTeamB(ActionEvent actionEvent) {
        try {
            teamService.disableTeamStatus(teamsViewL2.getSelectionModel().getSelectedItem().toString(), userName);
        } catch (NotFoundInDbException e) {
            showAlert("Success", "The Team was closed successfully. You are able to reopen it in the Reopen Team menu.", Alert.AlertType.INFORMATION);

            listTeams = FXCollections.observableArrayList();
            listTeams.setAll(teamService.getActiveTeamOfTeamOwner(userName)); //get only the team owner's teams
            teamsViewL2.setItems(listTeams);
        } catch (RuntimeException e) {
            e.printStackTrace();
            showAlert("Warning", e.getMessage(), Alert.AlertType.WARNING);
        }

    }

    public void reopenTeamB(ActionEvent actionEvent) {
        try {
            teamService.enableTeamStatus(closedTeamsViewL.getSelectionModel().getSelectedItem().toString(), userName);
        } catch (NotFoundInDbException e) {
            showAlert("Success", "The Team was reopened successfully.", Alert.AlertType.INFORMATION);

            listTeams = FXCollections.observableArrayList();
            listTeams.setAll(teamService.getInactiveTeamOfTeamOwner(userName)); //get only the team owner's teams
            closedTeamsViewL.setItems(listTeams);
        } catch (RuntimeException e) {
            e.printStackTrace();
            showAlert("Warning", e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    public void reopenTeam(ActionEvent actionEvent) {
        titleL.setText("Reopen Team");
        addManagerPane.setVisible(false);
        newTeamPane.setVisible(false);
        closeTeamPane.setVisible(false);
        reopenTeamPane.setVisible(true);

        searchTeam3.setPromptText("Search");
        searchTeam3.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldVal,
                                Object newVal) {
                search((String) oldVal, (String) newVal, closedTeamsViewL);
            }
        });

        listTeams = FXCollections.observableArrayList();
        listTeams.setAll(teamService.getInactiveTeamOfTeamOwner(userName)); //get only the active team owner's teams
        closedTeamsViewL.setItems(listTeams);

    }
}
