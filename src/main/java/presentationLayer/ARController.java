package presentationLayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import serviceLayer.LeagueService;
import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import java.util.LinkedList;


public class ARController implements ControllerInterface,Initializable {
    private LeagueService leagueService = new LeagueService();
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
    private Pane addTeamsPane;
    @FXML
    private javafx.scene.control.Label userLable;
    @FXML
    private javafx.scene.control.TextField  leagueIdField;
    @FXML
    private Spinner<Integer> winSpinner;
    @FXML
    private Spinner<Integer> loseSpinner;
    @FXML
    private Spinner<Integer> tieSpinner;
    @FXML
    private Spinner<Integer> seasonSpinner;
    @FXML
    private Spinner<Integer> leagueTeamsSpinner;
    @FXML
    private Spinner<Integer> seasonTeamsSpinner;
    @FXML
    private ComboBox<String> leagueCombo;
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
    public void switchApprove(){
        titleL.setText("Approve Teams");
        createSeasonPane.setVisible(false);
        createLeaguePane.setVisible(false);
        approveTeamPane.setVisible(true);
        activatePolicyPane.setVisible(false);
        addTeamsPane.setVisible(false);


    }
    @FXML
    public void switchCreate(){

    }
    @FXML
    public void switchLeaguePane(){
        titleL.setText("Create League");
        createLeaguePane.setVisible(true);
        activatePolicyPane.setVisible(true);
        createSeasonPane.setVisible(false);
        approveTeamPane.setVisible(false);
        addTeamsPane.setVisible(false);

    }
    @FXML
    public void switchActivatePolicyPane(){
        titleL.setText("Activate Match Policy");
        activatePolicyPane.setVisible(true);
        createSeasonPane.setVisible(false);
        approveTeamPane.setVisible(false);
        createLeaguePane.setVisible(false);
        addTeamsPane.setVisible(false);
        leagueTeamsSpinner.getValueFactory().setValue(0);
        seasonTeamsSpinner.getValueFactory().setValue(0);
    }
    public void activatePolicy(){
        int leagueId = leagueTeamsSpinner.getValue();
        int seasonId = seasonTeamsSpinner.getValue();
        //leagueService.activateMatchPolicyForSeason(leagueId,seasonId,userLable.getText());
    }
    public void clickApprove(ActionEvent actionEvent) {
        ObservableList<String> list = teamsViewL.getSelectionModel().getSelectedItems();
        //approve team
    }
    @FXML
    public void chooseTeam(){
        LinkedList<String> list = (LinkedList<String>) addTeamsViewL.getSelectionModel().getSelectedItems();
        int leagueId = leagueTeamsSpinner.getValue();
        int seasonId = seasonTeamsSpinner.getValue();
        String user = userLable.getText();
        //leagueService.chooseTeamForSeason(list,"leagueId","seasonId",user);
    }
    @FXML
    public void switchSeasonPane(){
        titleL.setText("Create Season");
        addTeamsPane.setVisible(false);
        createLeaguePane.setVisible(false);
        approveTeamPane.setVisible(false);
        activatePolicyPane.setVisible(false);
        createSeasonPane.setVisible(true);
    }

    @FXML
    public void switchAddTeamPane(){
        titleL.setText("Add Teams To season");
        addTeamsPane.setVisible(true);
        createLeaguePane.setVisible(false);
        approveTeamPane.setVisible(false);
        createSeasonPane.setVisible(false);
        activatePolicyPane.setVisible(false);

    }
    @FXML
    public void createLeague(){
        String league = leagueIdField.getText();
        String arName = userLable.getText();
        if(!league.equals("") && !arName.equals("")) {
            leagueService.addLeagueThroughRepresentative(league, arName);
        }else {
            missingAlert();
        }
    }

    public void missingAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Empty Fields");
        alert.setHeaderText("Please fill all fields");
        alert.setContentText("Please fill all the fields in this form.");
        alert.showAndWait();
    }
    @FXML
    public void createSeason(){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate start = startingDate.getValue();
        LocalDate end = endingDate.getValue();
        int win = winSpinner.getValue();
        int lose = loseSpinner.getValue();
        int tie = tieSpinner.getValue();
        int season = seasonSpinner.getValue();
        String league = leagueCombo.getValue();
        String policy = policyCombo.getValue();
        if(start==null||end==null||league.equals("")||league==null||policy.equals("")||policy==null||userLable.getText().equals("")){
            missingAlert();
        }else{
            Date sDate = Date.from(start.atStartOfDay(defaultZoneId).toInstant());
            Date eDate = Date.from(end.atStartOfDay(defaultZoneId).toInstant());
            leagueService.addSeasonThroughRepresentative(league,season,sDate,eDate,win,lose,tie,policy,userLable.getText());
        }
    }

    @Override
    public void setUser(String usernameL) {
        userLable.setText("Welcome " + usernameL);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList();
        LinkedList<String> teamStringList = new LinkedList<>();
        //import all unapproved team names to teamStringList from DB
        list.addAll(teamStringList);
        teamsViewL.setItems(list);
        teamsViewL.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        SpinnerValueFactory<Integer> valueFactoryWin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
        SpinnerValueFactory<Integer> valueFactoryLose = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
        SpinnerValueFactory<Integer> valueFactoryTie = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
        SpinnerValueFactory<Integer> valueFactorySeason = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        SpinnerValueFactory<Integer> valueFactorySeasonTeams = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        SpinnerValueFactory<Integer> valueFactorySeasonLeague = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        seasonTeamsSpinner.setValueFactory(valueFactorySeasonTeams);
        leagueTeamsSpinner.setValueFactory(valueFactorySeasonLeague);
        winSpinner.setValueFactory(valueFactoryWin);
        loseSpinner.setValueFactory(valueFactoryLose);
        tieSpinner.setValueFactory(valueFactoryTie);
        seasonSpinner.setValueFactory(valueFactorySeason);

        policyCombo.getItems().addAll(
                "SingleMatchPolicy",
                "ClassicMatchPolicy"
        );


        leagueCombo.getItems().addAll(
          "machbiHifa"
        );
    }
}
