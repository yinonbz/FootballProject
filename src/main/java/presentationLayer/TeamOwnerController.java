package presentationLayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.util.converter.IntegerStringConverter;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;



public class TeamOwnerController implements ControllerInterface{
    @Override
    public void setUser(String usernameL) {

    }
    @FXML
    private Spinner<Integer> yearSpinner;

    @FXML
    private Label teamNameL;

    @FXML
    private Label titleL;

    @FXML
    private Pane newTeamPane;

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
        if(teamNameL.getText().equals("")){

        }
    }
}
