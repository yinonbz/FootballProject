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
    private ComboBox<String> aFoulPlayer;
    @FXML
    private ComboBox<String> iFoulPlayer;
    @FXML
    private ComboBox<Integer> matchFoul;
    @FXML
    private TextField timeGoal;
    @FXML
    private ComboBox<String> playerGoal;
    @FXML
    private ComboBox<String> playerAssisted;
    @FXML
    private ComboBox<Integer> matchGoalId;
    @FXML
    private CheckBox isOwnGoal;
    @FXML
    private TextField timeInjury;
    @FXML
    private ComboBox<String> playerInjured;
    @FXML
    private ComboBox<Integer> matchInjuryId;
    @FXML
    private TextField timeOffside;
    @FXML
    private ComboBox<String> playerOffside;
    @FXML
    private ComboBox<Integer> matchOffsideId;
    @FXML
    private TextField timeRed;
    @FXML
    private ComboBox<String> playerRed;
    @FXML
    private ComboBox<Integer> matchRedId;
    @FXML
    private TextField timeYellow;
    @FXML
    private ComboBox<String> playerYellow;
    @FXML
    private ComboBox<Integer> matchYellowId;
    @FXML
    private MatchService matchService = new MatchService();

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

    }

    @FXML
    public void switchInjuryPane() {
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(true);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);

    }

    @FXML
    public void switchGoalPane() {
        foulPane.setVisible(false);
        goalPane.setVisible(true);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
    }

    @FXML
    public void switchOffsidePane() {
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(true);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
    }

    @FXML
    public void switchRedCardPane() {
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(true);
        yellowCardPane.setVisible(false);
    }

    @FXML
    public void switchYellowCardPane() {
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(true);
    }

    @FXML
    public void reportFoul() {
        String time = timeFoul.getText();
        String aPlayer = aFoulPlayer.getValue();
        String iPlayer = iFoulPlayer.getValue();
        int seasonId = matchFoul.getValue();
        if (aPlayer.split("-")[0].equals(iPlayer.split("-")[0])) {
            alertSamePlayer();
            return;
        } else if (aPlayer.split("-")[1].equals(iPlayer.split("-")[1])) {
            alertSameTeam();
            return;
        } else {
            matchService.reportFoulThroughReferee(time, aPlayer.split("-")[0], iPlayer.split("-")[0], String.valueOf(seasonId), this.userLabel.getText());
        }
    }

    @FXML
    public void reportGoal() {
        String time = timeGoal.getText();
        String gPlayer = playerGoal.getValue();
        String aPlayer = playerAssisted.getValue();
        int seasonId = matchGoalId.getValue();
        boolean isOwn = isOwnGoal.isSelected();
        if (gPlayer.split("-")[0].equals(aPlayer.split("-")[0])) {
            alertSamePlayer();
            return;
        } else if (!gPlayer.split("-")[1].equals(aPlayer.split("-")[1])) {
            ablerDifferentTeam();
            return;
        } else {
            matchService.reportGoalThroughReferee(time, gPlayer.split("-")[0], aPlayer.split("-")[0], Boolean.toString(isOwn), String.valueOf(seasonId), userLabel.getText());
        }
    }

    @FXML
    public void reportInjury() {
        String time = timeInjury.getText();
        String player = playerInjured.getValue();
        int seasonId = matchInjuryId.getValue();
        matchService.reportOnInjury(time, player.split("-")[0], String.valueOf(seasonId), userLabel.getText());
    }

    @FXML
    public void reportOffside() {
        String time = timeOffside.getText();
        String player = playerOffside.getValue();
        int seasonId = matchOffsideId.getValue();
        matchService.reportOffside(time, player.split("-")[0], String.valueOf(seasonId), userLabel.getText());
    }

    @FXML
    public void reportRedCard() {
        String time = timeRed.getText();
        String player = playerRed.getValue();
        int seasonId = matchRedId.getValue();
        matchService.reportOnRedCard(time, player.split("-")[0], String.valueOf(seasonId), userLabel.getText());
    }

    @FXML
    public void reportYellowCard() {
        String time = timeYellow.getText();
        String player = playerYellow.getValue();
        int seasonId = matchYellowId.getValue();
        matchService.yellowCard(time, player, String.valueOf(seasonId), userLabel.getText());

    }

    @FXML
    public void reportSubstitution() {

    }

    @FXML
    public void fillPlayerFoul() {
        int matchId = matchFoul.getValue();
        iFoulPlayer.setVisible(true);
        aFoulPlayer.setVisible(true);
        ArrayList<String> players = matchService.getAllPlayerMatch(matchId, userLabel.getText());
        if (players != null && players.size() > 0) {
            iFoulPlayer.getItems().removeAll();
            aFoulPlayer.getItems().removeAll();
            iFoulPlayer.getItems().addAll(players);
            aFoulPlayer.getItems().addAll(players);
        } else {
            noPlayerAlert();
        }
    }

    @FXML
    public void fillPlayerGoal() {
        int matchId = matchGoalId.getValue();
        playerGoal.setVisible(true);
        playerAssisted.setVisible(true);
        isOwnGoal.setVisible(true);
        ArrayList<String> players = matchService.getAllPlayerMatch(matchId, userLabel.getText());
        if (players != null && players.size() > 0) {
            playerGoal.getItems().addAll(players);
            playerAssisted.getItems().removeAll();
            playerAssisted.getItems().addAll(players);
        } else {
            noPlayerAlert();
        }
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
        playerName.setVisible(true);
        playerName.getItems().removeAll();
        ArrayList<String> players = matchService.getAllPlayerMatch(matchId, userLabel.getText());
        if (players != null && players.size() > 0) {
            playerName.getItems().addAll(players);
        } else {
            noPlayerAlert();
        }
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

    private void ablerDifferentTeam() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Different Team");
        alert.setHeaderText("the team's are Different");
        alert.setContentText("please choose the same team to the players.");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList();
        //import all unapproved team names to teamStringList from DB
        matchService = new MatchService();
    }

}
