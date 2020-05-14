package presentationLayer;

import javafx.fxml.FXML;
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

public class ARController implements ControllerInterface,Initializable {
    private LeagueService leagueService = new LeagueService();
    @FXML
    private Pane approveOrCreatePane;
    @FXML
    private Pane arHomeMenuPane;
    @FXML
    private Pane createTeamPane;
    @FXML
    private Pane approveTeamPane;
    @FXML
    private Pane createLeaguePane;
    @FXML
    private Pane createSeasonPane;
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
    private ComboBox<String> leagueCombo;
    @FXML
    private ComboBox<String> policyCombo;
    @FXML
    private DatePicker startingDate;
    @FXML
    private DatePicker endingDate;
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
        createLeaguePane.setVisible(false);
        createSeasonPane.setVisible(false);


    }
    @FXML
    public void switchLeaguePane(){
        createLeaguePane.setVisible(true);
        createTeamPane.setVisible(false);
        approveTeamPane.setVisible(false);
        createSeasonPane.setVisible(false);

    }
    @FXML
    public void switchSeasonPane(){
        createSeasonPane.setVisible(true);
        createLeaguePane.setVisible(false);
        createTeamPane.setVisible(false);
        approveTeamPane.setVisible(false);
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
        userLable.setText(usernameL);
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> valueFactoryWin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
        SpinnerValueFactory<Integer> valueFactoryLose = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
        SpinnerValueFactory<Integer> valueFactoryTie = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
        SpinnerValueFactory<Integer> valueFactorySeason = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);

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
