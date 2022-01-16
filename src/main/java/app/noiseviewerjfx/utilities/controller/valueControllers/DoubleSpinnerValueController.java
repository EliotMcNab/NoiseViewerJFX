package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.TextValidation;
import app.noiseviewerjfx.utilities.controller.valueControllers.associative.Associable;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.w3c.dom.Text;

public class DoubleSpinnerValueController extends ValueController implements Associable {

    // the spinner the IntegerSpinnerValueController is associated to
    private final Spinner<Double> SPINNER;
    // the value to set the spinner to in case of a formatting error
    private final double DEFAULT_VALUE;

    // the unit the spinner represents
    private final String UNIT;
    private double lastValue;

    private int precision;

    /**
     * Creates a new IntegerSpinnerValueController with no node associated to it
     * @param linkedSpinner (Spinner): the spinner associated to the IntegerSpinnerValueController
     * @param valueFactory (SpinnerValueFactory(Integer)): the spinner's value factory
     * @param defaultValue (int): fallback value in case of a formatting error
     * @param unit (String): the unit the spinner represents, "" for no unit
     */
    public DoubleSpinnerValueController(
            Spinner<Double> linkedSpinner, SpinnerValueFactory<Double> valueFactory,
            final double defaultValue, String unit) {

        this(linkedSpinner, valueFactory, defaultValue, unit, 5);
    }

    public DoubleSpinnerValueController(
            Spinner<Double> linkedSpinner, SpinnerValueFactory<Double> valueFactory,
            final double defaultValue, String unit, int precision) {

        this.precision = precision;

        // saves info related to the spinner
        this.SPINNER = linkedSpinner;
        this.DEFAULT_VALUE = defaultValue;
        this.UNIT = unit;

        // if the spinner has a unit associated to it...
        if (!unit.equals("")) {
            // ...sets the correct converter
            valueFactory.setConverter(TextValidation.newDoubleUnitConverter(unit));
        } else {
            valueFactory.setConverter(TextValidation.newDoubleConverter(precision));
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
        SPINNER.getValueFactory().setValue(newValue);
        newState();
        return true;
    }

    public void setPrecision(int newPrecision) {
        this.precision = precision;
    }

    /**
     * Determines which formatter to apply to the spinner based on its unit
     * @param unit (String): the spinner's unit, "" for no unit
     * @return (TextFormatter(String)): a text formatter supporting the spinner's unit
     */
    private TextFormatter<String> getAssociatedSpinnerFormatter(String unit) {
        if (!unit.equals("")) return TextValidation.newDoubleUnitFormatter(unit);
        else return TextValidation.newDoubleFormatter();
    }

    /**
     * Checks to see if the value in the spinner has changed since the last state
     * @return (boolean): whether the spinner's value has changed
     */
    private boolean changeDetected() {

        double newValue = SPINNER.getValue();
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

    private ChangeListener<Double> updateOnValueChange() {
        return (observableValue, oldState, newState) -> newState();
    }

    /**
     * Updates the state of the spinner whenever the user confirms the value they have just entered
     * @return (EventHandler(KeyEvent)): event handler for key presses in the Spinner
     */
    private EventHandler<KeyEvent> updateOnKeyPress() {
        return keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER) && changeDetected()) {
                newState();
            }
        };
    }

}
