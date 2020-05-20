package presentationLayer;

import businessLayer.Tournament.Match.Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import serviceLayer.MatchService;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RefereeController implements ControllerInterface, Initializable {
    @FXML
    private javafx.scene.control.Label userLabel;
    @FXML
    private Pane substitutionPane;
    @FXML
    private Pane foulPane;
    @FXML
    private Pane removeEventPane;
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
    private Spinner<Integer> timeFoul;
    @FXML
    private ComboBox<String> aFoulPlayer;
    @FXML
    private ComboBox<String> iFoulPlayer;
    @FXML
    private ComboBox<Integer> matchFoul;
    @FXML
    private Spinner<Integer> timeGoal;
    @FXML
    private ComboBox<String> playerGoal;
    @FXML
    private ComboBox<String> playerAssisted;
    @FXML
    private ComboBox<Integer> matchGoalId;
    @FXML
    private CheckBox isOwnGoal;
    @FXML
    private Spinner<Integer> timeInjury;
    @FXML
    private ComboBox<String> playerInjured;
    @FXML
    private ComboBox<Integer> matchInjuryId;
    @FXML
    private Spinner<Integer> timeOffside;
    @FXML
    private ComboBox<String> playerOffside;
    @FXML
    private ComboBox<Integer> matchOffsideId;
    @FXML
    private Spinner<Integer> timeRed;
    @FXML
    private ComboBox<String> playerRed;
    @FXML
    private ComboBox<Integer> matchRedId;
    @FXML
    private Spinner<Integer> timeYellow;
    @FXML
    private ComboBox<String> playerYellow;
    @FXML
    private ComboBox<Integer> matchYellowId;
    @FXML
    private MatchService matchService = new MatchService();
    @FXML
    private Spinner<Integer> timeSubstitute;
    @FXML
    private ComboBox<String> playerOn;
    @FXML
    private ComboBox<String> playerOff;
    @FXML
    private ComboBox<Integer> matchSubstituteId;
    @FXML
    private ComboBox<Integer> matchRemoveId;
    @FXML
    private Spinner<Integer> timeRemove;
    @FXML
    private Spinner<Integer> eventRemove;
    @Override
    public void setUser(String usernameL) {
        userLabel.setText(usernameL);
        HashMap<Integer, Match> match = new HashMap<>();
        match = matchService.getAllMatch(userLabel.getText());
        if (match == null) {
            noMatchAlert();
        } else {
            ArrayList<Integer> matchId = new ArrayList<>();
            matchId.addAll(match.keySet());
            matchFoul.getItems().addAll(matchId);
            matchGoalId.getItems().addAll(matchId);
            matchInjuryId.getItems().addAll(matchId);
            matchOffsideId.getItems().addAll(matchId);
            matchRedId.getItems().addAll(matchId);
            matchYellowId.getItems().addAll(matchId);
            matchSubstituteId.getItems().addAll(matchId);
            matchRemoveId.getItems().addAll(matchId);
        }
    }

    @FXML
    public void switchFoulPane() {
        foulPane.setVisible(true);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(false);
        removeEventPane.setVisible(false);
        timeFoul.getValueFactory().setValue(0);

    }

    @FXML
    public void switchInjuryPane() {
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(true);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(false);
        removeEventPane.setVisible(false);
        timeInjury.getValueFactory().setValue(0);


    }

    @FXML
    public void switchGoalPane() {
        foulPane.setVisible(false);
        goalPane.setVisible(true);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(false);
        removeEventPane.setVisible(false);
        timeGoal.getValueFactory().setValue(0);

    }

    @FXML
    public void switchOffsidePane() {
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(true);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(false);
        removeEventPane.setVisible(false);
        timeOffside.getValueFactory().setValue(0);
    }

    @FXML
    public void switchRedCardPane() {
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(true);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(false);
        removeEventPane.setVisible(false);
        timeRed.getValueFactory().setValue(0);
    }

    @FXML
    public void switchYellowCardPane() {
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(true);
        substitutionPane.setVisible(false);
        removeEventPane.setVisible(false);
        timeYellow.getValueFactory().setValue(0);
    }

    @FXML
    public void switchSubstitutionPane() {
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(true);
        removeEventPane.setVisible(false);
        timeSubstitute.getValueFactory().setValue(0);
    }
    @FXML
    public void switchRemovePane() {
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(false);
        removeEventPane.setVisible(true);
        timeRemove.getValueFactory().setValue(0);
    }
    @FXML
    public void getAllMatches(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(userLabel.getText() + " Matches:");
        String refereeMatches = "";
        HashMap<Integer,Match> map = matchService.getAllMatch(userLabel.getText());
        if(map!=null &&map.size()>0) {
            for (Map.Entry<Integer, Match> entry : map.entrySet()) {
                refereeMatches = refereeMatches + "\n" + "Match: " + entry.getValue().toString();
            }
            alert.setContentText(refereeMatches);
        }else{
            noMatchAlert();
        }
        alert.showAndWait();
    }
    @FXML
    public void reportFoul() {
        try {
            int time = timeFoul.getValue();
            String aPlayer = aFoulPlayer.getValue();
            String iPlayer = iFoulPlayer.getValue();
            int seasonId = matchFoul.getValue();
            if (aPlayer == null || iPlayer == null || aPlayer.equals("") || iPlayer.equals("")) {
                missingAlert();
            } else if (aPlayer.split("-")[0].equals(iPlayer.split("-")[0])) {
                alertSamePlayer();
                return;
            } else if (aPlayer.split("-")[1].equals(iPlayer.split("-")[1])) {
                alertSameTeam();
                return;
            } else {
                matchService.reportFoulThroughReferee(String.valueOf(time), aPlayer.split("-")[0], iPlayer.split("-")[0], String.valueOf(seasonId), this.userLabel.getText());
                Success("report foul on: "+ iPlayer.split("-")[0]+ " by the attacker: "+aPlayer.split("-")[0] );
            }
        } catch (Exception e) {
            missingAlert();
        }
    }

    @FXML
    public void reportGoal() {
        try {
            int time = timeGoal.getValue();
            String gPlayer = playerGoal.getValue();
            String aPlayer = playerAssisted.getValue();
            int seasonId = matchGoalId.getValue();
            boolean isOwn = isOwnGoal.isSelected();
            if (gPlayer.split("-")[0].equals(aPlayer.split("-")[0])) {
                alertSamePlayer();
                return;
            } else if (!gPlayer.split("-")[1].equals(aPlayer.split("-")[1])) {
                alertDifferentTeam();
                return;
            } else {
                matchService.reportGoalThroughReferee(String.valueOf(time), gPlayer.split("-")[0], aPlayer.split("-")[0], Boolean.toString(isOwn), String.valueOf(seasonId), userLabel.getText());
                Success("report Goal to Team: "+gPlayer.split("-")[1] + "by player: "+gPlayer.split("-")[0] );
            }
        } catch (Exception e) {
            missingAlert();
        }
    }

    @FXML
    public void reportInjury() {
        try {
            int time = timeInjury.getValue();
            String player = playerInjured.getValue();
            int seasonId = matchInjuryId.getValue();
            if (player == null || player.equals("")) {
                missingAlert();
            } else {
                matchService.reportOnInjury(String.valueOf(time), player.split("-")[0], String.valueOf(seasonId), userLabel.getText());
                Success("report injury to: "+player );
            }
        } catch (Exception e) {
            missingAlert();
        }
    }

    @FXML
    public void reportOffside() {
        try {
            int time = timeOffside.getValue();
            String player = playerOffside.getValue();
            int seasonId = matchOffsideId.getValue();
            if (player == null || player.equals("")) {
                missingAlert();
            } else {
                matchService.reportOffside(String.valueOf(time), player.split("-")[0], String.valueOf(seasonId), userLabel.getText());
                Success("report Offside to: "+player );
            }
        } catch (Exception e) {
            missingAlert();
        }
    }

    @FXML
    public void reportRedCard() {
        try {
            int time = timeRed.getValue();
            String player = playerRed.getValue();
            int seasonId = matchRedId.getValue();
            if (player == null || player.equals("")) {
                missingAlert();
            } else {
                matchService.reportOnRedCard(String.valueOf(time), player.split("-")[0], String.valueOf(seasonId), userLabel.getText());
                Success("report red card to: "+player );
            }
        } catch (Exception e) {
            missingAlert();
        }
    }

    @FXML
    public void reportYellowCard() {
        try {
            int time = timeYellow.getValue();
            String player = playerYellow.getValue();
            int seasonId = matchYellowId.getValue();
            if (player == null || player.equals("")) {
                missingAlert();
            } else {
                matchService.yellowCard(String.valueOf(time), player.split("-")[0], String.valueOf(seasonId), userLabel.getText());
                Success("report yellow card to: "+player );
            }
        } catch (Exception e) {
            missingAlert();
        }
    }

    @FXML
    public void reportSubstitution() {
        try {
            int time = timeSubstitute.getValue();
            String onPlayer = playerOn.getValue();
            String offPlayer = playerOff.getValue();
            int seasonId = matchSubstituteId.getValue();
            if (onPlayer == null || offPlayer == null || onPlayer.equals("") || offPlayer.equals("")) {
                missingAlert();
            } else if (onPlayer.split("-")[0].equals(offPlayer.split("-")[0])) {
                alertSamePlayer();
                return;
            } else if (!onPlayer.split("-")[1].equals(offPlayer.split("-")[1])) {
                alertDifferentTeam();
                return;
            } else {
                matchService.reportOnSubstitute(String.valueOf(time), onPlayer.split("-")[0], offPlayer.split("-")[0], String.valueOf(seasonId), userLabel.getText());
                Success("Substitution");
            }
        } catch (Exception e) {
            missingAlert();
        }
    }
    @FXML
    public void removeCurrEvent() {
        try {
            int matchId = matchRemoveId.getValue();
            int time = timeRemove.getValue();
            int eventId = eventRemove.getValue();
            matchService.removeEventByMainReferee(String.valueOf(time), String.valueOf(matchId), userLabel.getText(), String.valueOf(eventId));
        } catch (Exception e) {
            missingAlert();
        }
    }
    @FXML
    public void fillPlayerFoul() {
        int matchId = matchFoul.getValue();
        iFoulPlayer.setDisable(false);
        aFoulPlayer.setDisable(false);
        ArrayList<String> players = matchService.getAllPlayerMatch(matchId, userLabel.getText());
        if (players != null && players.size() > 0) {
            iFoulPlayer.getItems().clear();
            aFoulPlayer.getItems().clear();
            iFoulPlayer.getItems().addAll(players);
            aFoulPlayer.getItems().addAll(players);
        } else {
            noPlayerAlert();
        }
    }

    @FXML
    public void fillPlayerSubstitute() {
        int matchId = matchSubstituteId.getValue();
        playerOn.setDisable(false);
        playerOff.setDisable(false);
        ArrayList<String> players = matchService.getAllPlayerMatch(matchId, userLabel.getText());
        if (players != null && players.size() > 0) {
            playerOn.getItems().clear();
            playerOn.getItems().addAll(players);
            playerOff.getItems().clear();
            playerOff.getItems().addAll(players);
        } else {
            noPlayerAlert();
        }
    }

    @FXML
    public void fillPlayerGoal() {
        int matchId = matchGoalId.getValue();
        playerGoal.setDisable(false);
        playerAssisted.setDisable(false);
        isOwnGoal.setDisable(false);
        ArrayList<String> players = matchService.getAllPlayerMatch(matchId, userLabel.getText());
        if (players != null && players.size() > 0) {
            playerGoal.getItems().clear();
            playerGoal.getItems().addAll(players);
            playerAssisted.getItems().clear();
            playerAssisted.getItems().addAll(players);
        } else {
            noPlayerAlert();
        }
    }
    @FXML
    public void fillRemove(){
        timeRemove.setDisable(false);
        eventRemove.setDisable(false);
    }
    @FXML
    public void fillPlayerInjury() {
        fillInfo(matchInjuryId, playerInjured);
    }

    @FXML
    public void fillPlayerOffside() {
        fillInfo(matchOffsideId, playerOffside);
    }

    @FXML
    public void fillPlayerRedCard() {
        fillInfo(matchRedId, playerRed);

    }

    @FXML
    public void fillPlayerYellowCard() {
        fillInfo(matchYellowId, playerYellow);
    }

    private void fillInfo(ComboBox<Integer> match, ComboBox<String> playerName) {
        int matchId = match.getValue();
        playerName.setDisable(false);
        playerName.getItems().removeAll();
        ArrayList<String> players = matchService.getAllPlayerMatch(matchId, userLabel.getText());
        if (players != null && players.size() > 0) {
            playerName.getItems().clear();
            playerName.getItems().addAll(players);
        } else {
            noPlayerAlert();
        }
    }
    private void Success(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(text + " was succeeded");
        alert.setHeaderText("success");
        alert.setContentText(text + " was succeeded");
        alert.showAndWait();
    }

    private void noPlayerAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("no Players assigned ");
        alert.setHeaderText("matches doesnt have any Players");
        alert.setContentText("matches doesnt have any Players.");
        alert.showAndWait();
    }

    private void alertSamePlayer() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("the same Player");
        alert.setHeaderText("the player's are the same one");
        alert.setContentText("please choose different Player.");
        alert.showAndWait();
    }

    private void alertSameTeam() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("the same Team");
        alert.setHeaderText("the team's are the same one");
        alert.setContentText("please choose different team to the players.");
        alert.showAndWait();
    }

    public void noMatchAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("no matches assigned ");
        alert.setHeaderText("Referee doesnt have any matches");
        alert.setContentText("Referee doesnt have any matches.");
        alert.showAndWait();
    }

    private void alertDifferentTeam() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Different Team");
        alert.setHeaderText("the team's are Different");
        alert.setContentText("please choose the same team to the players.");
        alert.showAndWait();
    }

    private void missingAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Empty Fields");
        alert.setHeaderText("Please fill all fields");
        alert.setContentText("Please fill all the fields in this form.");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList();
        //import all unapproved team names to teamStringList from DB
        matchService = new MatchService();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 121, 0);
        timeFoul.setValueFactory(valueFactory);
        timeGoal.setValueFactory(valueFactory);
        timeInjury.setValueFactory(valueFactory);
        timeOffside.setValueFactory(valueFactory);
        timeRed.setValueFactory(valueFactory);
        timeYellow.setValueFactory(valueFactory);
        timeSubstitute.setValueFactory(valueFactory);
        timeRemove.setValueFactory(valueFactory);
        SpinnerValueFactory<Integer> valueFactoryEvent = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        eventRemove.setValueFactory(valueFactoryEvent);
    }

}
