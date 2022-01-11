package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.TextValidation;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.w3c.dom.Node;

public class IntegerTextFieldValueController extends ValueController {

    private final TextField TEXT_FIELD;
    private final int DEFAULT_VALUE;
    private final String UNIT;

    private Node associatedNode;

    private String lastStringValue;

    // =================================
    //          CONSTRUCTOR
    // =================================

    /**
     * Creates a new IntegerTextFieldValueController with no node associated to it
     * @param linkedTextField (TextField): the text field associated to the IntegerTextFieldValueController
     * @param defaultValue (int): fallback value in case of invalid format
     * @param unit (String): unit shown in the text field, "" for no unit
     */
    public IntegerTextFieldValueController(TextField linkedTextField, final int defaultValue, String unit) {
        this.TEXT_FIELD = linkedTextField;
        this.DEFAULT_VALUE = defaultValue;
        this.UNIT = unit;

        TEXT_FIELD.setTextFormatter(getTextFiledFormatter());
        setValue(defaultValue);
        lastStringValue = TEXT_FIELD.getText();

        addListeners();

    }

    // =================================
    //             VALUE
    // =================================

    /**
     * Gets the value contained in the text field, taking potential units into consideration
     * @return (int): the value contained in the text field (default value if invalid format)
     */
    public double getValue() {

        // the content of the text field
        String textFieldContents = TEXT_FIELD.getText();

        // if the text field displays units
        if (!UNIT.equals("")) {
            // removes the unit from the text field's content
            textFieldContents = textFieldContents.replaceFirst(UNIT, "");
        }

        // checks that the text field's contents are in a valid form
        if (!TextValidation.isValidIntString(textFieldContents)) return DEFAULT_VALUE;

        else return Integer.parseInt(textFieldContents);
    }

    @Override
    protected void setValue(double value) {
        TEXT_FIELD.setText((int) value + UNIT);
    }

    /**
     * Determines which Text Formatter is needed for the text field's unit
     * @return
     */
    private TextFormatter<String> getTextFiledFormatter() {
        return switch (UNIT) {
            case "%" -> TextValidation.newIntegerPercentageFormatter();
            default -> TextValidation.newIntegerFormatter();
        };
    }

    // =================================
    //            LISTENERS
    // =================================

    private void addListeners() {
        TEXT_FIELD.setOnKeyPressed(update());
        TEXT_FIELD.focusedProperty().addListener(selectAllText());
    }

    /**
     * Updates the state of the text field when the user presses the ENTER key
     * @return (EventHandler(KeyEvent)): event listener for key presses in the text field
     */
    private EventHandler<KeyEvent> update() {
        return keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER) && changeDetected()){
                newState();
                updateText();
            }
        };
    }

    /**
     * Checks to see if the value in the text field has changed since the last state
     * @return (boolean): whether the value in the text field has changed
     */
    private boolean changeDetected() {

        String newText = TEXT_FIELD.getText();
        boolean hasChanged = !newText.equals(lastStringValue);

        if (hasChanged) lastStringValue = newText;

        return hasChanged;
    }

    /**
     * Updates the text in the text field when the user enter a new value
     */
    private void updateText() {
        String currentText = TEXT_FIELD.getText();

        if (!UNIT.equals("")) {
            currentText = currentText.replaceFirst(UNIT, "");
        }

        if (!TextValidation.isValidIntString(currentText)) TEXT_FIELD.setText(DEFAULT_VALUE + UNIT);
        else TEXT_FIELD.setText(currentText + UNIT);
    }

    /**
     * Selects all the text in the text field when the user clicks on it
     * @return (ChangeListener(Boolean)): event listener for a change in state of the text field
     */
    private ChangeListener<Boolean> selectAllText() {
        return (observableValue, oldState, newState) -> {
            if (newState) Platform.runLater(TEXT_FIELD::selectAll);
        };
    }

}
