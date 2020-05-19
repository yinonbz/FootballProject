package presentationLayer;

import businessLayer.Exceptions.AlreadyExistException;
import businessLayer.Exceptions.MissingInputException;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import serviceLayer.LeagueService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class ARController implements ControllerInterface, Initializable {

    private LeagueService leagueService;


    @FXML
    private Pane approveOrCreatePane;
    @FXML
    private Pane arHomeMenuPane;
    @FXML
    private Label titleL;
    @FXML
    private Pane createTeamPane;
    @FXML
    private Pane approveTeamPane;
    @FXML
    private Pane createLeaguePane;
    @FXML
    private Pane createSeasonPane;
    @FXML
    private Pane activatePolicyPane;
    @FXML
    private Pane addTeamדToSeasonPane;
    @FXML
    private javafx.scene.control.Label userLable;
    @FXML
    private javafx.scene.control.TextField leagueIdField;
    @FXML
    private Spinner<Integer> winSpinner;
    @FXML
    private Spinner<Integer> loseSpinner;
    @FXML
    private Spinner<Integer> tieSpinner;
    @FXML
    private Spinner<Integer> seasonSpinner;
    @FXML
    private Spinner<Integer> seasonTeamsSpinner;
    @FXML
    private ComboBox<String> leagueCombo;
    @FXML
    private ComboBox<String> leagueCombo2;
    @FXML
    private ComboBox<String> leagueCombo3;
    @FXML
    private ComboBox<String> seasonCombo2;
    @FXML
    private ComboBox<String> seasonCombo3;
    @FXML
    private ComboBox<String> policyCombo;
    @FXML
    private DatePicker startingDate;
    @FXML
    private DatePicker endingDate;
    @FXML
    ListView teamsViewL;
    @FXML
    ListView addTeamsViewL;


    @FXML
    public void switchApprove() {

        displayUnconfirmedTeams();

        titleL.setText("Approve Teams");
        createSeasonPane.setVisible(false);
        createLeaguePane.setVisible(false);
        approveTeamPane.setVisible(true);
        activatePolicyPane.setVisible(false);
        addTeamדToSeasonPane.setVisible(false);


    }

    @FXML
    public void switchCreate() {

    }

    @FXML
    public void switchLeaguePane() {
        titleL.setText("Create League");
        createLeaguePane.setVisible(true);
        activatePolicyPane.setVisible(true);
        createSeasonPane.setVisible(false);
        approveTeamPane.setVisible(false);
        addTeamדToSeasonPane.setVisible(false);

    }

    @FXML
    public void switchActivatePolicyPane() {
        titleL.setText("Activate Match Policy");
        activatePolicyPane.setVisible(true);
        createSeasonPane.setVisible(false);
        approveTeamPane.setVisible(false);
        createLeaguePane.setVisible(false);
        addTeamדToSeasonPane.setVisible(false);
        //leagueTeamsSpinner.getValueFactory().setValue(0);
        seasonTeamsSpinner.getValueFactory().setValue(0);
    }

    public void activatePolicy() {
        //int leagueId = leagueTeamsSpinner.getValue();
        int seasonId = seasonTeamsSpinner.getValue();
        //leagueService.activateMatchPolicyForSeason(leagueId,seasonId,userLable.getText());
    }

    public void clickApprove(ActionEvent actionEvent) {
        ObservableList<String> list = teamsViewL.getSelectionModel().getSelectedItems();
        for (int i = 0; i < list.size(); i++) {
            String teamToApprove = list.get(i);
            leagueService.confirmTeamRequestThroughRepresentative(teamToApprove, userLable.getText());
        }
        showAlert("Teams Approved Successfully", "Teams were confirmed successfully.", Alert.AlertType.INFORMATION);
        displayUnconfirmedTeams();
    }

    @FXML
    public void chooseTeam() {
        ObservableList list = addTeamsViewL.getSelectionModel().getSelectedItems();
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.addAll(list);
        //int leagueId = leagueTeamsSpinner.getValue();
        int seasonId = seasonTeamsSpinner.getValue();
        String user = userLable.getText();
        leagueService.chooseTeamForSeason(linkedList, "leagueId", "seasonId", user);
    }

    @FXML
    public void switchSeasonPane() {
        titleL.setText("Create Season");
        addTeamדToSeasonPane.setVisible(false);
        createLeaguePane.setVisible(false);
        approveTeamPane.setVisible(false);
        activatePolicyPane.setVisible(false);
        createSeasonPane.setVisible(true);

        leagueCombo3.getItems().setAll(
                leagueService.getAllULeagues()
        );

    }

    @FXML
    public void switchAddTeamPane() {
        titleL.setText("Add Teams To Season");
        addTeamדToSeasonPane.setVisible(true);
        createLeaguePane.setVisible(false);
        approveTeamPane.setVisible(false);
        createSeasonPane.setVisible(false);
        activatePolicyPane.setVisible(false);

        leagueCombo2.getItems().setAll(
                leagueService.getAllULeagues()
        );

        ObservableList<String> listTeams = FXCollections.observableArrayList();
        listTeams.setAll(leagueService.getAllTeamsNames());
        addTeamsViewL.setItems(listTeams);
        addTeamsViewL.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    @FXML
    public void createLeague() {
        String league = leagueIdField.getText();
        String arName = userLable.getText();
        if (!league.equals("") && !arName.equals("")) {
            leagueService.addLeagueThroughRepresentative(league, arName);
        } else {
            missingAlert();
        }
    }

    public void missingAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Empty Fields");
        alert.setHeaderText("Please fill all fields");
        alert.setContentText("Please fill all the fields in this form.");
        alert.showAndWait();
    }

    @FXML
    public void createSeason() {
        try {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate start = startingDate.getValue();
        LocalDate end = endingDate.getValue();
        if(start == null || end == null)
            throw new MissingInputException("Please choose a Starting and an Ending Date for the season.");
        int win = winSpinner.getValue();
        int lose = loseSpinner.getValue();
        int tie = tieSpinner.getValue();
        int season = seasonSpinner.getValue();
        String league = leagueCombo3.getValue();
        String policy = policyCombo.getValue();
        Date sDate = Date.from(start.atStartOfDay(defaultZoneId).toInstant());
        Date eDate = Date.from(end.atStartOfDay(defaultZoneId).toInstant());
            leagueService.addSeasonThroughRepresentative(league, season, sDate, eDate, win, lose, tie, policy, userLable.getText());
            showAlert("Success", "Season was created successfully. Please confirm the Match Policy to activate the Season.", Alert.AlertType.INFORMATION);
        } catch (RuntimeException e) {
            showAlert("Warning", e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    @Override
    public void setUser(String usernameL) {
        userLable.setText(usernameL);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        leagueService = new LeagueService();

        SpinnerValueFactory<Integer> valueFactoryWin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        SpinnerValueFactory<Integer> valueFactoryLose = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        SpinnerValueFactory<Integer> valueFactoryTie = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        SpinnerValueFactory<Integer> valueFactorySeason = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 3000, 0);
        SpinnerValueFactory<Integer> valueFactorySeasonTeams = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 3000, 0);
        SpinnerValueFactory<Integer> valueFactorySeasonLeague = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 3000, 0);
        //seasonTeamsSpinner.setValueFactory(valueFactorySeasonTeams);
        //leagueTeamsSpinner.setValueFactory(valueFactorySeasonLeague);
        winSpinner.setValueFactory(valueFactoryWin);
        loseSpinner.setValueFactory(valueFactoryLose);
        tieSpinner.setValueFactory(valueFactoryTie);
        seasonSpinner.setValueFactory(valueFactorySeason);

        policyCombo.getItems().setAll(
                "SingleMatchPolicy",
                "ClassicMatchPolicy"
        );


    }

    public void displayUnconfirmedTeams() {
        ObservableList<String> list = FXCollections.observableArrayList();
        //import all unapproved team names to teamStringList from DB
        leagueService = new LeagueService();
        list.setAll(leagueService.getAllUnconfirmedTeams());
        teamsViewL.setItems(list);
        teamsViewL.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(String title, String text, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void leagueSelect2(ActionEvent actionEvent) {
        seasonCombo2.getItems().setAll(
                leagueService.getAllSeasonsFromLeague(leagueCombo2.getValue())
        );

        seasonCombo2.setDisable(false);


    }


    public void submitTeamsToSeason(ActionEvent actionEvent) {
        LinkedList<String> teamsNames = new LinkedList<>();
        ObservableList<String> list = addTeamsViewL.getSelectionModel().getSelectedItems();
        for (int i = 0; i < list.size(); i++) {
            String teamToAdd = list.get(i);
            teamsNames.add(teamToAdd);
        }

        String leagueID = leagueCombo2.getValue();
        String seasonID = seasonCombo2.getValue();
        try {
            leagueService.chooseTeamForSeason(teamsNames, leagueID, seasonID, userLable.getText());
        } catch (MissingInputException e) {
            showAlert(e.getMessage(), "Please complete this form to add a team to a season.", Alert.AlertType.WARNING);
        }
        showAlert("Success", "The teams were added to the season successfully.", Alert.AlertType.INFORMATION);
    }


}
