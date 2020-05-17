package presentationLayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import serviceLayer.LeagueService;
import serviceLayer.MatchService;

import java.net.URL;
import java.util.ResourceBundle;

public class RefereeController implements ControllerInterface, Initializable {
    @FXML
    private javafx.scene.control.Label userLabel;
    @FXML
    private Pane substitutionPane;
    @FXML
    private Pane foulPane;
    @FXML
    private Pane goalPane;
    @FXML
    private Pane injuryPane;
    @FXML
    private Pane offsidePane;
    @FXML
    private Pane redCardPane;
    @FXML
    private Pane yellowCardPane;
    @FXML
    private TextField timeFoul;
    @FXML
    private TextField aFoulPlayer;
    @FXML
    private TextField iFoulPlayer;
    @FXML
    private Spinner<Integer> seasonFoulSpinner;
    @FXML
    private TextField timeGoal;
    @FXML
    private TextField playerGoal;
    @FXML
    private TextField playerAssisted;
    @FXML
    private Spinner<Integer> seasonGoalId;
    @FXML
    private CheckBox isOwnGoal;
    @FXML
    private TextField timeInjury;
    @FXML
    private TextField playerInjured;
    @FXML
    private Spinner<Integer> seasonInjuryId;
    @FXML
    private TextField timeOffside;
    @FXML
    private TextField playerOffside;
    @FXML
    private Spinner<Integer> seasonOffsideId;
    @FXML
    private TextField timeRed;
    @FXML
    private TextField playerRed;
    @FXML
    private Spinner<Integer> seasonRedId;
    @FXML
    private TextField timeYellow;
    @FXML
    private TextField playerYellow;
    @FXML
    private Spinner<Integer> seasonYellowId;
    @FXML
    private MatchService matchService = new MatchService();

    @Override
    public void setUser(String usernameL) {
        userLabel.setText(usernameL);
    }
    @FXML
    public void switchFoulPane(){
        foulPane.setVisible(true);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        seasonFoulSpinner.getValueFactory().setValue(0);

    }
    @FXML
    public void switchInjuryPane(){
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(true);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        seasonInjuryId.getValueFactory().setValue(0);

    }
    @FXML
    public void switchGoalPane(){
        foulPane.setVisible(false);
        goalPane.setVisible(true);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        seasonGoalId.getValueFactory().setValue(0);
    }
    @FXML
    public void switchOffsidePane(){
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(true);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        seasonOffsideId.getValueFactory().setValue(0);
    }
    @FXML
    public void switchRedCardPane(){
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(true);
        yellowCardPane.setVisible(false);
        seasonRedId.getValueFactory().setValue(0);
    }
    @FXML
    public void switchYellowCardPane(){
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(true);
        seasonYellowId.getValueFactory().setValue(0);
    }
    @FXML
    public void reportFoul() {
        String time = timeFoul.getText();
        String aPlayer = aFoulPlayer.getText();
        String iPlayer = iFoulPlayer.getText();
        int seasonId = seasonFoulSpinner.getValue();
        matchService.reportFoulThroughReferee(time, aPlayer, iPlayer, String.valueOf(seasonId), this.userLabel.getText());
    }

    @FXML
    public void reportInjury() {
        String time = timeInjury.getText();
        String player = playerInjured.getText();
        int seasonId = seasonInjuryId.getValue();
        matchService.reportOnInjury(time, player, String.valueOf(seasonId), userLabel.getText());
    }

    @FXML
    public void reportGoal() {
        String time = timeGoal.getText();
        String gPlayer = playerGoal.getText();
        String aPlayer = playerAssisted.getText();
        int seasonId = seasonGoalId.getValue();
        boolean isOwn = isOwnGoal.isSelected();
        matchService.reportGoalThroughReferee(time, gPlayer, aPlayer, Boolean.toString(isOwn), String.valueOf(seasonId), userLabel.getText());
    }

    @FXML
    public void reportOffside() {
        String time = timeOffside.getText();
        String player = playerOffside.getText();
        int seasonId = seasonOffsideId.getValue();
        matchService.reportOffside(time, player, String.valueOf(seasonId), userLabel.getText());
    }

    @FXML
    public void reportRedCard() {
        String time =  timeRed.getText();
        String player = playerRed.getText();
        int seasonId = seasonRedId.getValue();
        matchService.reportOnRedCard(time, player, String.valueOf(seasonId), userLabel.getText());
    }

    @FXML
    public void reportYellowCard() {
        String time = timeYellow.getText();
        String player = playerYellow.getText();
        int seasonId = seasonYellowId.getValue();
        matchService.yellowCard(time, player, String.valueOf(seasonId), userLabel.getText());

    }

    @FXML
    public void reportSubstitution() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList();
        //import all unapproved team names to teamStringList from DB
        matchService = new MatchService();


        SpinnerValueFactory<Integer> valueFactorySeason = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0);
        seasonFoulSpinner.setValueFactory(valueFactorySeason);
        seasonGoalId.setValueFactory(valueFactorySeason);
        seasonInjuryId.setValueFactory(valueFactorySeason);
        seasonOffsideId.setValueFactory(valueFactorySeason);
        seasonRedId.setValueFactory(valueFactorySeason);
        seasonYellowId.setValueFactory(valueFactorySeason);

    }
}
