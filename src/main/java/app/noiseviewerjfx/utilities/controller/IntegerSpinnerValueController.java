package app.noiseviewerjfx.utilities.controller;

import app.noiseviewerjfx.utilities.TextValidation;
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
public class IntegerSpinnerValueController {

    // the spinner the IntegerSpinnerValueController is associated to
    private final Spinner<Integer> SPINNER;
    // the value to set the spinner to in case of a formatting error
    private final int DEFAULT_VALUE;

    // the unit the spinner represents
    private final String UNIT;
    // the node associated to the spinner
    private Node associatedNode = null;

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

        // styles the spinner and adds the corresponding listeners
        styleSpinner();
        addListeners();

    }

    /**
     * Creates a new IntegerSpinnerValueController with a node associated to it which has its value synced to the spinner
     * @param linkedSpinner (Spinner): the spinner associated to the IntegerSpinnerValueController
     * @param valueFactory (SpinnerValueFactory(Integer)): the spinner's value factory
     * @param defaultValue (int): fallback value in case of a formatting error
     * @param unit (String): the unit the spinner represents, "" for no unit
     * @param associatedNode (Node): the node the spinner is paired to
     */
    public IntegerSpinnerValueController(
            Spinner<Integer> linkedSpinner, SpinnerValueFactory<Integer> valueFactory,
            final int defaultValue, String unit,
            Node associatedNode) {

        this(linkedSpinner, valueFactory, defaultValue, unit);

        // saves the associated node
        this.associatedNode = associatedNode;

        // adds the corresponding listeners to update the associated node's value
        addAssociatedNodeListeners();
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
     * Applies styling to the spinner
     */
    private void styleSpinner() {
        // sets the position of the spinner's arrows
        SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
    }

    /**
     * Adds basic listeners TO THE LISTENER ONLY
     */
    private void addListeners() {
        // selects all text when clicked on
        SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText());
    }

    /**
     * Adds necessary listeners LINKING VALUES WITH THE ASSOCIATED NODE
     */
    private void addAssociatedNodeListeners() {
        // determines the type of the associated node
        if (associatedNode instanceof Slider) {
            addSliderListeners();
        } else if (associatedNode instanceof ProgressBar) {
            addProgressBarListeners();
        }
    }

    /**
     * Adds necessary listeners to link values with a slider
     */
    private void addSliderListeners() {

        Slider associatedSlider = (Slider) associatedNode;

        // sets the starting value of the slider to be that of the spinner
        associatedSlider.setValue(SPINNER.getValue());

        // updates the slider's value whenever the user presses the ENTER key within the spinner's editor
        SPINNER.setOnKeyPressed(updateAssociatedSliderOnKeyPress(associatedSlider, SPINNER, DEFAULT_VALUE, UNIT));
        // updates the slider's value whenever the user uses the spinner's arrows
        SPINNER.valueProperty().addListener(updateAssociatedSlider(associatedSlider));
    }

    /**
     * Adds necessary listeners to link values with a progress bar
     */
    private void addProgressBarListeners() {

        ProgressBar associatedProgressBar = (ProgressBar) associatedNode;

        // sets the starting value of the progress bar to be that of the spinner
        associatedProgressBar.setProgress((double) SPINNER.getValue() / 100);

        // updates the progress bar's progress whenever the user presses the ENTER key within the spinner's editor
        SPINNER.setOnKeyPressed(updateAssociatedProgressBarOnKeyPress(associatedProgressBar, SPINNER, DEFAULT_VALUE, UNIT));
        // updates the progress bar's progress whenever the user uses the spinner's arrows
        SPINNER.valueProperty().addListener(updateAssociatedProgressBar(associatedProgressBar));

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
     * Updates the value of the associated slider when the user presses the ENTER key
     * @param targetSlider (Slider): the associated slider
     * @param valueSpinner (Spinner(Integer)): the spinner from which we get the value
     * @param defaultValue (int): the default value to set in case of a format error
     * @param unit (String): the unit displayed in the spinner, "" for no unit
     * @return (EventHandler(KeyEvent)): listener for key presses within the spinner
     */
    private static EventHandler<KeyEvent> updateAssociatedSliderOnKeyPress(
            Slider targetSlider, Spinner<Integer> valueSpinner, final int defaultValue, String unit) {
        return keyEvent -> {
            // only tries to update the Slider's text when the user presses the ENTER keu
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {

                // gets the text in the spinner's editor
                String spinnerText = valueSpinner.getEditor().getText();

                // if the spinner contains units, gets rid of the only legal occurrence
                if (!unit.equals("")) spinnerText = spinnerText.replaceFirst(unit, "");

                // checks that the spinner's text represents a valid value (ie: it doesn't have multiple units)
                // if that is not the case sets the spinner's value back to default
                if (!TextValidation.isValidIntString(spinnerText)) valueSpinner.getValueFactory().setValue(defaultValue);

                // updates the value in the associated slider to be that of the spinner
                // (can now be the default value if there was an error in formatting)
                targetSlider.setValue(valueSpinner.getValue());
            }

        };
    }

    /**
     * Updates the value of the associated slider when a change is detected
     * @param targetSlider (Slider): the associated slider
     * @return (ChangeListener(Integer)): listener for the spinner's values
     */
    private static ChangeListener<Integer> updateAssociatedSlider(Slider targetSlider) {
        return (observableValue, oldValue, newValue) -> targetSlider.setValue(newValue);
    }


    /**
     * Updates the value of the associated progress bar when the user presses the ENTER key
     * @param targetProgressBar (Slider): the associated progress bar
     * @param valueSpinner (Spinner(Integer)): the spinner from which we get the value
     * @param defaultValue (int): the default value to set in case of a format error
     * @param unit (String): the unit displayed in the spinner, "" for no unit
     * @return (EventHandler(KeyEvent)): listener for key presses within the spinner
     */
    private static EventHandler<KeyEvent> updateAssociatedProgressBarOnKeyPress(
            ProgressBar targetProgressBar, Spinner<Integer> valueSpinner, final int defaultValue, String unit) {
        return keyEvent -> {
            // only tries to update the progress bar's text when the user presses the ENTER keu
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {

                // gets the text in the spinner's editor
                String spinnerText = valueSpinner.getEditor().getText();

                // if the spinner contains units, gets rid of the only legal occurrence
                if (!unit.equals("")) spinnerText = spinnerText.replaceFirst(unit, "");

                // checks that the spinner's text represents a valid value (ie: it doesn't have multiple units)
                // if that is not the case sets the spinner's value back to default
                if (!TextValidation.isValidIntString(spinnerText)) valueSpinner.getValueFactory().setValue(defaultValue);

                // updates the value in the associated progress bar to be that of the spinner
                // (can now be the default value if there was an error in formatting)
                targetProgressBar.setProgress(valueSpinner.getValue());
            }
        };
    }

    /**
     * Updates the value of the associated progress bar when a change is detected
     * @param targetProgressBar (Slider): the associated progress bar
     * @return (ChangeListener(Integer)): listener for the spinner's values
     */
    private static ChangeListener<Integer> updateAssociatedProgressBar(ProgressBar targetProgressBar) {
        return (observableValue, oldValue, newValue) -> targetProgressBar.setProgress((double) newValue / 100);
    }

}
