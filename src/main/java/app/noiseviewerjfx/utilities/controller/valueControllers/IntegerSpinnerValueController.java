package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.TextValidation;
import app.noiseviewerjfx.utilities.controller.valueControllers.associative.Associable;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

/**
 * Handles value changes for spinners
 */
public class IntegerSpinnerValueController extends ValueController implements Associable {

    // the spinner the IntegerSpinnerValueController is associated to
    private final Spinner<Integer> SPINNER;
    // the value to set the spinner to in case of a formatting error
    private final int DEFAULT_VALUE;

    // the unit the spinner represents
    private final String UNIT;
    // the node associated to the spinner
    private Node associatedNode = null;
    private int lastValue;

    /**
     * Creates a new IntegerSpinnerValueController with no node associated to it
     * @param linkedSpinner (Spinner): the spinner associated to the IntegerSpinnerValueController
     * @param valueFactory (SpinnerValueFactory(Integer)): the spinner's value factory
     * @param defaultValue (int): fallback value in case of a formatting error
     * @param unit (String): the unit the spinner represents, "" for no unit
     */
    public IntegerSpinnerValueController(
            Spinner<Integer> linkedSpinner, SpinnerValueFactory<Integer> valueFactory,
            final int defaultValue, String unit) {

        // saves info related to the spinner
        this.SPINNER = linkedSpinner;
        this.DEFAULT_VALUE = defaultValue;
        this.UNIT = unit;

        // if the spinner has a unit associated to it...
        if (!unit.equals("")) {
            // ...sets the correct converter
            valueFactory.setConverter(getAssociatedSpinnerConverter(unit));
        }

        // determines how the spinner displays text based on its units
        SPINNER.getEditor().setTextFormatter(getAssociatedSpinnerFormatter(unit));
        SPINNER.setValueFactory(valueFactory);

        lastValue = SPINNER.getValue();

        // styles the spinner and adds the corresponding listeners
        styleSpinner();
        addListeners();

    }

    // =================================
    //              VALUE
    // =================================

    public double getValue() {
        return SPINNER.getValue();
    }

    protected boolean setValue(double newValue) {
        SPINNER.getValueFactory().setValue((int) newValue);
        return true;
    }

    /**
     * Determines which formatter to apply to the spinner based on its unit
     * @param unit (String): the spinner's unit, "" for no unit
     * @return (TextFormatter(String)): a text formatter supporting the spinner's unit
     */
    private TextFormatter<String> getAssociatedSpinnerFormatter(String unit) {
        return switch (unit) {
            case "%" -> TextValidation.newIntegerPercentageFormatter();
            default -> TextValidation.newIntegerFormatter();
        };
    }

    /**
     * Determines which converter to apply to the spinner based on its unit
     * @param unit (String): the spinner's unit, "" for no unit
     * @return (StringConverter(Integer)): a string converter supporting the spinner's unit
     */
    private StringConverter<Integer> getAssociatedSpinnerConverter(String unit) {
        return switch (unit) {
            case "%" -> TextValidation.newIntegerPercentageConverter();
            default -> null;
        };
    }

    /**
     * Checks to see if the value in the spinner has changed since the last state
     * @return (boolean): whether the spinner's value has changed
     */
    private boolean changeDetected() {

        int newValue = SPINNER.getValue();
        boolean hasChanged = newValue != lastValue;

        if (hasChanged) lastValue = newValue;

        return hasChanged;

    }

    // =================================
    //             STYLING
    // =================================

    /**
     * Applies styling to the spinner
     */
    private void styleSpinner() {
        // sets the position of the spinner's arrows
        SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
    }

    // =================================
    //           LISTENERS
    // =================================

    /**
     * Adds basic listeners TO THE LISTENER ONLY
     */
    private void addListeners() {
        // selects all text when clicked on
        SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText());
        SPINNER.getEditor().setOnKeyPressed(updateOnKeyPress());
        SPINNER.valueProperty().addListener(updateOnValueChange());
    }

    /**
     * Selects all the text in a spinner when it is clicked
     * @return (ChangeListener(Boolean)): listener for changes in the spinner's focus
     */
    private ChangeListener<Boolean> selectAllSpinnerText() {
        return (observableValue, oldState, newState) -> {
            if (newState) Platform.runLater(SPINNER.getEditor()::selectAll);
        };
    }

    /**
     * Updates the state of the spinner whenever the user confirms the value they have just entered
     * @return (EventHandler(KeyEvent)): event handler for key presses in the Spinner
     */
    private EventHandler<KeyEvent> updateOnKeyPress() {
        return keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER) && changeDetected()) newState();
        };
    }

    private ChangeListener<Integer> updateOnValueChange() {
        return (observableValue, integer, t1) -> newState();
    }

}
