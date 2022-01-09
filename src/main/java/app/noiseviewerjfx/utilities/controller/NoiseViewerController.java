package app.noiseviewerjfx.utilities.controller;

import app.noiseviewerjfx.utilities.TextValidation;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Noise Viewer application
 */
public class NoiseViewerController implements Initializable {

    // region SPINNERS
    @FXML
    private Spinner<Integer> OCTAVE_SPINNER;
    private final SpinnerValueFactory<Integer> octaveSpinnerVF =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
    @FXML
    private Spinner<Integer> PERSISTENCE_SPINNER;
    private final SpinnerValueFactory<Integer> persistenceSpinnerVF =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
    @FXML
    private Spinner<Integer> WIDTH_SPINNER;
    private final SpinnerValueFactory<Integer> widthSpinnerVF =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(100, 1000, 100);
    @FXML
    private Spinner<Integer> HEIGHT_SPINNER;
    private final SpinnerValueFactory<Integer> heightSpinnerVF =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(100, 1000, 100);
    @FXML
    private Spinner<Integer> OPACITY_SPINNER;
    private final SpinnerValueFactory<Integer> opacitySpinnerVF =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 100);
    @FXML
    private Spinner<Integer> MASK_WIDTH_SPINNER;
    private final SpinnerValueFactory<Integer> maskWidthSpinnerVF =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 50);
    @FXML
    private Spinner<Integer> MASK_HEIGHT_SPINNER;
    private final SpinnerValueFactory<Integer> maskHeightSpinnerVF =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 50);
    @FXML
    private Spinner<Integer> MASK_STRENGTH_SPINNER;
    private final SpinnerValueFactory<Integer> maskStrengthSpinnerVF =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
    // endregion SPINNERS

    // region SLIDERS
    @FXML
    private Slider MASK_WIDTH_SLIDER;
    @FXML
    private Slider MASK_HEIGHT_SLIDER;
    @FXML
    private Slider MASK_STRENGTH_SLIDER;
    // endregion SLIDERS

    // TEXT FIELDS
    @FXML
    private TextField SEED_TEXT_FIELD;

    // PROGRESS BARS
    @FXML
    private ProgressBar OPACITY_PROGRESS_BAR;

    /*@FXML
    private CheckBox MASK_CHECKBOX;

    @FXML
    private CheckBox PERLIN_NOISE_CHECKBOX;

    @FXML
    private CheckBox TERRAIN_CHECKBOX;

    @FXML
    private Slider MASK_HEIGHT_SLIDER;

    @FXML
    private Slider MASK_STRENGTH_SLIDER;

    @FXML
    private Slider MASK_WIDTH_SLIDER;*/


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // region SPINNERS

        // =====================================
        //     VALUE VALIDATION (no units)
        // =====================================

        // octave
        OCTAVE_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerFormatter());
        // persistence
        PERSISTENCE_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerFormatter());
        // map width
        WIDTH_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerFormatter());
        // map height
        HEIGHT_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerFormatter());
        // mask strength
        MASK_STRENGTH_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerFormatter());

        // =====================================
        //           VALUE VALIDATION
        //        (spinners with units)
        // =====================================
        // need a custom converter to remove the units when getting the value)

        // mask height
        maskHeightSpinnerVF.setConverter(TextValidation.newIntegerPercentageConverter());
        MASK_HEIGHT_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerPercentageFormatter());
        // mask width
        maskWidthSpinnerVF.setConverter(TextValidation.newIntegerPercentageConverter());
        MASK_WIDTH_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerPercentageFormatter());
        // layer opacity
        opacitySpinnerVF.setConverter(TextValidation.newIntegerPercentageConverter());
        OPACITY_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerPercentageFormatter());

        // =====================================
        //              STYLING
        // =====================================
        // sets the spinner to have horizontal rather than vertical arrows

        // octave
        OCTAVE_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        // persistence
        PERSISTENCE_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        // map width
        WIDTH_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        // map height
        HEIGHT_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        // mask width
        MASK_WIDTH_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        // mask height
        MASK_HEIGHT_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        // mask strength
        MASK_STRENGTH_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        // terrain opacity
        OPACITY_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);

        // =====================================
        //           SPINNER VALUES
        // =====================================
        // sets the spinner's values to their respective value factories

        // octaves
        OCTAVE_SPINNER.setValueFactory(octaveSpinnerVF);
        // persistence
        PERSISTENCE_SPINNER.setValueFactory(persistenceSpinnerVF);
        // map width
        WIDTH_SPINNER.setValueFactory(widthSpinnerVF);
        // map height
        HEIGHT_SPINNER.setValueFactory(heightSpinnerVF);
        // mask width
        MASK_WIDTH_SPINNER.setValueFactory(maskWidthSpinnerVF);
        // mask height
        MASK_HEIGHT_SPINNER.setValueFactory(maskHeightSpinnerVF);
        // mask strength
        MASK_STRENGTH_SPINNER.setValueFactory(maskStrengthSpinnerVF);
        // terrain opacity
        OPACITY_SPINNER.setValueFactory(opacitySpinnerVF);

        // =====================================
        //              LISTENERS
        // =====================================

        // octaves.
        // selects all the spinner's text
        OCTAVE_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(OCTAVE_SPINNER));

        // persistence.
        // selects all the spinner's text
        PERSISTENCE_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(PERSISTENCE_SPINNER));

        // map width.
        // selects all the spinner's text
        WIDTH_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(WIDTH_SPINNER));

        // map height.
        // selects all the spinner's text
        HEIGHT_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(HEIGHT_SPINNER));

        // mask width.
        // selects all the spinner's text
        MASK_WIDTH_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(MASK_WIDTH_SPINNER));
        // value sync between spinner and slider
        MASK_WIDTH_SLIDER.setValue(MASK_WIDTH_SPINNER.getValue());
        // updates the slider if the user ENTERS a new value
        MASK_WIDTH_SPINNER.setOnKeyPressed(updateAssociatedSliderOnKeyPress(
                MASK_WIDTH_SLIDER, MASK_WIDTH_SPINNER, 0, "%"));
        // updates the slider if the user uses the arrow buttons
        MASK_WIDTH_SPINNER.valueProperty().addListener(updateAssociatedSlider(MASK_WIDTH_SLIDER));

        // mask height.
        // value sync between spinner and slider
        MASK_HEIGHT_SLIDER.setValue(MASK_HEIGHT_SPINNER.getValue());
        // selects all the spinner's text
        MASK_HEIGHT_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(MASK_HEIGHT_SPINNER));
        // updates the slider if the user ENTERS a new value
        MASK_HEIGHT_SPINNER.setOnKeyPressed(updateAssociatedSliderOnKeyPress
                (MASK_HEIGHT_SLIDER, MASK_HEIGHT_SPINNER, 0, "%"));
        // updates the slider if the user uses the arrow buttons
        MASK_HEIGHT_SPINNER.valueProperty().addListener(updateAssociatedSlider(MASK_HEIGHT_SLIDER));

        // mask strength.
        // value sync between spinner and slider
        MASK_STRENGTH_SLIDER.setValue(MASK_STRENGTH_SPINNER.getValue());
        // selects all the spinner's text
        MASK_STRENGTH_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(MASK_STRENGTH_SPINNER));
        // updates the slider if the user ENTERS a new value
        MASK_STRENGTH_SPINNER.setOnKeyPressed(updateAssociatedSliderOnKeyPress(
                MASK_STRENGTH_SLIDER, MASK_STRENGTH_SPINNER, 1));
        // updates the slider if the user uses the arrow buttons
        MASK_STRENGTH_SPINNER.valueProperty().addListener(updateAssociatedSlider(MASK_STRENGTH_SLIDER));

        // opacity.
        // value sync between spinner and progress bar
        OPACITY_PROGRESS_BAR.setProgress((double) OPACITY_SPINNER.getValue() / 100);
        // selects all the spinner's text
        OPACITY_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(OPACITY_SPINNER));
        // updates the progress bar if the user ENTERS a new value
        OPACITY_SPINNER.setOnKeyPressed(updateAssociatedProgressBarOnKeyPress(
                OPACITY_PROGRESS_BAR, OPACITY_SPINNER, 0, "%"));
        // updates the progress bar if the user uses the arrow buttons
        OPACITY_SPINNER.valueProperty().addListener(updateAssociatedProgressBar(OPACITY_PROGRESS_BAR));

        // endregion

        // region SLIDER

        // =====================================
        //             LISTENERS
        // =====================================

        // updates the value of the associated spinner if the slider changes value
        MASK_WIDTH_SLIDER.valueProperty().addListener(updateAssociatedSpinner(MASK_WIDTH_SPINNER));
        MASK_HEIGHT_SLIDER.valueProperty().addListener(updateAssociatedSpinner(MASK_HEIGHT_SPINNER));
        MASK_STRENGTH_SLIDER.valueProperty().addListener(updateAssociatedSpinner(MASK_STRENGTH_SPINNER));
        MASK_STRENGTH_SLIDER.setSnapToTicks(true);

        // =====================================
        //              STYLING
        // =====================================

        // changes the mouse's cursor icon when hovering over the slider
        MASK_WIDTH_SLIDER.setOnMouseEntered(changeNodeCursor(MASK_WIDTH_SLIDER, Cursor.H_RESIZE));
        MASK_HEIGHT_SLIDER.setOnMouseEntered(changeNodeCursor(MASK_HEIGHT_SLIDER, Cursor.H_RESIZE));
        MASK_STRENGTH_SLIDER.setOnMouseEntered(changeNodeCursor(MASK_STRENGTH_SLIDER, Cursor.H_RESIZE));

        // endregion

        // region TEXT FIELDS

        // =====================================
        //     VALUE VALIDATION (no units)
        // =====================================

        SEED_TEXT_FIELD.setTextFormatter(TextValidation.newIntegerFormatter());

        // endregion

        // region PROGRESS BARS

        // endregion
    }

    // =====================================
    //         LISTENERS GENERATORS
    // =====================================

    // region SPINNERS

    /**
     * Updates the value of the associated slider when a change is detected
     * @param targetSlider (Slider): the associated slider
     * @return (ChangeListener(Integer)): listener for the spinner's values
     */
    private static ChangeListener<Integer> updateAssociatedSlider(Slider targetSlider) {
        return (observableValue, oldValue, newValue) -> targetSlider.setValue(newValue);
    }

    /**
     * Updates the value of the associated slider when the user presses the ENTER key
     * @param targetSlider (Slider): the associated slider
     * @param valueSpinner (Spinner(Integer)): the spinner from which we get the value
     * @param defaultValue (int): the default value to set in case of a format error
     * @return (EventHandler(KeyEvent)): listener for key presses within the spinner
     */
    private static EventHandler<KeyEvent> updateAssociatedSliderOnKeyPress(
            Slider targetSlider, Spinner<Integer> valueSpinner, final int defaultValue) {
        return updateAssociatedSliderOnKeyPress(targetSlider, valueSpinner, defaultValue,"");
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
     * Updates the value of the associated progress bar when a change is detected
     * @param targetProgressBar (Slider): the associated progress bar
     * @return (ChangeListener(Integer)): listener for the spinner's values
     */
    private static ChangeListener<Integer> updateAssociatedProgressBar(ProgressBar targetProgressBar) {
        return (observableValue, oldValue, newValue) -> targetProgressBar.setProgress((double) newValue / 100);
    }

    /**
     * Updates the value of the associated progress bar when the user presses the ENTER key
     * @param targetProgressBar (Slider): the associated progress bar
     * @param valueSpinner (Spinner(Integer)): the spinner from which we get the value
     * @param defaultValue (int): the default value to set in case of a format error
     * @return (EventHandler(KeyEvent)): listener for key presses within the spinner
     */
    private static EventHandler<KeyEvent> updateAssociatedProgressBarOnKeyPress(
            ProgressBar targetProgressBar, Spinner<Integer> valueSpinner, final int defaultValue) {
        return updateAssociatedProgressBarOnKeyPress(targetProgressBar, valueSpinner, defaultValue, "");
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
     * Selects all the text in a spinner when it is clicked
     * @param targetSpinner (Spinner): the spinner to select the text of
     * @return (ChangeListener(Boolean)): listener for changes in the spinner's focus
     */
    private static ChangeListener<Boolean> selectAllSpinnerText(Spinner<?> targetSpinner) {
        return (observableValue, oldState, newState) -> {
            if (newState) Platform.runLater(targetSpinner.getEditor()::selectAll);
        };
    }
    // endregion

    // region SLIDERS

    /**
     * Updates the spinner associated to this slider
     * @param targetSpinner (Spinner): the associated spinner
     * @return (ChangeListener(Number)): listener for a change in the slider's value
     */
    private static ChangeListener<Number> updateAssociatedSpinner(Spinner<Integer> targetSpinner) {
        return new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                targetSpinner.getValueFactory().setValue(newValue.intValue());
            }
        };
    }

    // endregion

    /**
     * Changes the cursor upon hovering on a node
     * @param targetNode (Node): the node of we want to change the hover cursor of
     * @param newCursor (Cursor): the new cursor to apply upon hover
     * @return (EventHandler(MouseEvent)): listener for changes in the mouse's state
     */
    private static EventHandler<MouseEvent> changeNodeCursor(Node targetNode, Cursor newCursor) {
        return mouseEvent -> targetNode.setCursor(newCursor);
    }
}