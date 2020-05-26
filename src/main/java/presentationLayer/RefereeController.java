package presentationLayer;

import businessLayer.Exceptions.NotApprovedException;
import businessLayer.Tournament.Match.Match;
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
import serviceLayer.LeagueService;
import serviceLayer.MatchService;
import serviceLayer.SystemService;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RefereeController implements ControllerInterface, Initializable, Observer {

    private String userName;

    private LeagueService leagueService;

    private SystemService systemService;

    private ArrayList<TitledPane> notificationPanesCollection;

    @FXML
    private Accordion notificationsPane;
    @FXML
    private Label userLabel;
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
    private Pane namePane;
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
    private ComboBox<String> eventRemove;
    @FXML
    private TextField nameField;
    @FXML
    private Pane matchPane;
    @FXML
    private ComboBox<Integer> matchDisplay;
    @FXML
    private Label titleL;

    @Override
    public void setUser(String usernameL) {
        matchService = new MatchService();
        systemService = new SystemService();
        ObservableList<String> list = FXCollections.observableArrayList();
        userLabel.setText(usernameL);
        leagueService = new LeagueService();
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

        ArrayList<String> matchId = new ArrayList<>();
        matchId = matchService.getAllRefereeMatch(userLabel.getText());
        if (matchId == null) {
            noMatchAlert();
        } else {
            ArrayList<Integer> match = new ArrayList<>();
            for (String s:matchId) {
                match.add(Integer.parseInt(s));
            }
           // matchId.addAll(matchService.getAllRefereeMatch());
            matchFoul.getItems().addAll(match);
            matchGoalId.getItems().addAll(match);
            matchInjuryId.getItems().addAll(match);
            matchOffsideId.getItems().addAll(match);
            matchRedId.getItems().addAll(match);
            matchYellowId.getItems().addAll(match);
            matchSubstituteId.getItems().addAll(match);
            matchRemoveId.getItems().addAll(match);
            matchDisplay.getItems().addAll(match);
        }
    }

    @FXML
    public void switchMatchPane() {
        titleL.setText("Display Match Details");
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(false);
        removeEventPane.setVisible(false);
        namePane.setVisible(false);
        matchPane.setVisible(true);
    }

    @FXML
    public void switchFoulPane() {
        titleL.setText("Report Foul");
        foulPane.setVisible(true);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(false);
        removeEventPane.setVisible(false);
        namePane.setVisible(false);
        matchPane.setVisible(false);

        timeFoul.getValueFactory().setValue(0);

    }

    @FXML
    public void switchInjuryPane() {
        titleL.setText("Report Injury");
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(true);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(false);
        removeEventPane.setVisible(false);
        namePane.setVisible(false);
        matchPane.setVisible(false);
        timeInjury.getValueFactory().setValue(0);


    }

    @FXML
    public void switchGoalPane() {
        titleL.setText("Report Goal");
        foulPane.setVisible(false);
        goalPane.setVisible(true);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(false);
        removeEventPane.setVisible(false);
        namePane.setVisible(false);
        matchPane.setVisible(false);
        timeGoal.getValueFactory().setValue(0);

    }

    @FXML
    public void switchOffsidePane() {
        titleL.setText("Report Offside");
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(true);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(false);
        removeEventPane.setVisible(false);
        namePane.setVisible(false);
        matchPane.setVisible(false);
        timeOffside.getValueFactory().setValue(0);
    }

    @FXML
    public void switchRedCardPane() {
        titleL.setText("Report Red Card");
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(true);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(false);
        removeEventPane.setVisible(false);
        namePane.setVisible(false);
        matchPane.setVisible(false);
        timeRed.getValueFactory().setValue(0);
    }

    @FXML
    public void switchYellowCardPane() {
        titleL.setText("Report Yellow Card");
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(true);
        substitutionPane.setVisible(false);
        removeEventPane.setVisible(false);
        namePane.setVisible(false);
        matchPane.setVisible(false);
        timeYellow.getValueFactory().setValue(0);
    }

    @FXML
    public void switchSubstitutionPane() {
        titleL.setText("Report Substitution");
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(true);
        removeEventPane.setVisible(false);
        namePane.setVisible(false);
        matchPane.setVisible(false);
        timeSubstitute.getValueFactory().setValue(0);
    }

    @FXML
    public void switchRemovePane() {
        titleL.setText("Remove Event");
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(false);
        namePane.setVisible(false);
        removeEventPane.setVisible(true);
        matchPane.setVisible(false);
        timeRemove.getValueFactory().setValue(0);
    }

    public void switchNamePane() {
        titleL.setText("Edit Name");
        namePane.setVisible(true);
        foulPane.setVisible(false);
        goalPane.setVisible(false);
        injuryPane.setVisible(false);
        offsidePane.setVisible(false);
        redCardPane.setVisible(false);
        yellowCardPane.setVisible(false);
        substitutionPane.setVisible(false);
        removeEventPane.setVisible(false);
        matchPane.setVisible(false);
    }

    @FXML
    public void getAllMatches() {
        try {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(userLabel.getText() + " Matches:");
        /*
        String refereeMatches = "";
        HashMap<Integer,Match> map = matchService.getAllRefereeMatch(userLabel.getText());
        if(map!=null &&map.size()>0) {
            for (Map.Entry<Integer, Match> entry : map.entrySet()) {
                refereeMatches = refereeMatches + "\n" + "Match: " + entry.getValue().toString();
            }
            */
            int matchId =matchDisplay.getValue();
            String refereeMatch = matchService.getDetailsOnMatch(matchId);
            alert.setContentText(refereeMatch);
            alert.showAndWait();
        }catch (Exception e){
            noMatchAlert();
        }
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
                success("report foul on: " + iPlayer.split("-")[0] + " by the attacker: " + aPlayer.split("-")[0]);
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
                success("report Goal to Team: " + gPlayer.split("-")[1] + "by player: " + gPlayer.split("-")[0]);
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
                success("report injury to: " + player);
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
                success("report Offside to: " + player);
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
                success("report red card to: " + player);
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
                success("report yellow card to: " + player);
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
                success("Substitution");
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
            String eventId = eventRemove.getValue();
            matchService.removeEventByMainReferee(String.valueOf(time), String.valueOf(matchId), userLabel.getText(), eventId);
        } catch (NotApprovedException e) {
            showAlert("Access Denied", e.getMessage(), Alert.AlertType.WARNING);
        } catch (Exception e) {
            missingAlert();
        }
    }
    
    @FXML
    public void fillPlayerFoul() {
        int matchId = matchFoul.getValue();
        iFoulPlayer.setDisable(false);
        aFoulPlayer.setDisable(false);
        ArrayList<String> players = matchService.getAllPlayerOfMatch(matchId);
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
        ArrayList<String> players = matchService.getAllPlayerOfMatch(matchId);
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
    public void changeName() {
        try {
            String name = nameField.getText();
            if (name != null && !name.equals("")) {
                systemService.updateRefereeName(name, this.userLabel.getText());
                success("changing name to " + name);
            } else {
                missingAlert();
            }
        } catch (Exception e) {
            missingAlert();
        }
    }

    @FXML
    public void fillPlayerGoal() {
        int matchId = matchGoalId.getValue();
        playerGoal.setDisable(false);
        playerAssisted.setDisable(false);
        isOwnGoal.setDisable(false);
        ArrayList<String> players = matchService.getAllPlayerOfMatch(matchId);
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
    public void fillRemove() {
        timeRemove.setDisable(false);
        eventRemove.getItems().addAll(systemService.getEventByMatch(String.valueOf(matchRemoveId.getValue())));
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
        ArrayList<String> players = matchService.getAllPlayerOfMatch(matchId);
        if (players != null && players.size() > 0) {
            playerName.getItems().clear();
            playerName.getItems().addAll(players);
        } else {
            noPlayerAlert();
        }
    }

    private void success(String text) {
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

        //import all unapproved team names to teamStringList from DB
        matchService = new MatchService();
        systemService = new SystemService();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 121, 0);
        timeFoul.setValueFactory(valueFactory);
        timeGoal.setValueFactory(valueFactory);
        timeInjury.setValueFactory(valueFactory);
        timeOffside.setValueFactory(valueFactory);
        timeRed.setValueFactory(valueFactory);
        timeYellow.setValueFactory(valueFactory);
        timeSubstitute.setValueFactory(valueFactory);
        timeRemove.setValueFactory(valueFactory);
        //SpinnerValueFactory<Integer> valueFactoryEvent = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
       // eventRemove.setValueFactory(valueFactoryEvent);
    }

    @Override
    public void update(Observable o, Object arg) {
        LinkedList<String> message = ((LinkedList<String>) arg);
        notificationPanesCollection = new ArrayList<>();
        AnchorPane newPanelContent = new AnchorPane();
        newPanelContent.getChildren().add(new Label(message.get(1)));
        TitledPane pane = new TitledPane(message.get(0), newPanelContent);
        notificationsPane.getPanes().add(pane);
    }


    private void showAlert(String title, String text, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
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
