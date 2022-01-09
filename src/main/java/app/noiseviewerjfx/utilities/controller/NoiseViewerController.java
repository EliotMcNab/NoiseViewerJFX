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
    private Spinner<Integer> MAP_WIDTH_SPINNER;
    private final SpinnerValueFactory<Integer> mapWidthSpinnerVF =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(100, 1000, 100);
    @FXML
    private Spinner<Integer> MAP_HEIGHT_SPINNER;
    private final SpinnerValueFactory<Integer> mapHeightSpinnerVF =
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

    // BUTTONS
    @FXML
    private Button RANDOM_OCTAVE_BUTTON;

    @FXML
    private Button RANDOM_PERSISTENCE_BUTTON;

    @FXML
    private Button RANDOM_SEED_BUTTON;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // region SPINNERS

        IntegerSpinnerValueController octaveSpinnerVC = new IntegerSpinnerValueController(
                OCTAVE_SPINNER, octaveSpinnerVF, 1, ""
        );
        IntegerSpinnerValueController persistenceSpinnerVC = new IntegerSpinnerValueController(
                PERSISTENCE_SPINNER, persistenceSpinnerVF, 1, ""
        );
        IntegerSpinnerValueController mapWidthSpinnerVC = new IntegerSpinnerValueController(
                MAP_WIDTH_SPINNER, mapWidthSpinnerVF, 100, ""
        );
        IntegerSpinnerValueController mapHeightSpinnerVC = new IntegerSpinnerValueController(
                MAP_HEIGHT_SPINNER, mapHeightSpinnerVF, 100, ""
        );
        IntegerSpinnerValueController maskWidthSpinnerVC = new IntegerSpinnerValueController(
                MASK_WIDTH_SPINNER, maskWidthSpinnerVF, 50, "%", MASK_WIDTH_SLIDER
        );
        IntegerSpinnerValueController maskHeightSpinnerVC = new IntegerSpinnerValueController(
                MASK_HEIGHT_SPINNER, maskHeightSpinnerVF, 50, "%", MASK_HEIGHT_SLIDER
        );
        IntegerSpinnerValueController maskStrengthSpinnerVC = new IntegerSpinnerValueController(
                MASK_STRENGTH_SPINNER, maskStrengthSpinnerVF, 1, "", MASK_STRENGTH_SLIDER
        );
        IntegerSpinnerValueController opacitySpinnerVC = new IntegerSpinnerValueController(
                OPACITY_SPINNER, opacitySpinnerVF, 100, "%", OPACITY_PROGRESS_BAR
        );

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

        RANDOM_OCTAVE_BUTTON.setOnMouseEntered(changeNodeCursor(RANDOM_OCTAVE_BUTTON, Cursor.HAND));
        RANDOM_PERSISTENCE_BUTTON.setOnMouseEntered(changeNodeCursor(RANDOM_PERSISTENCE_BUTTON, Cursor.HAND));
        RANDOM_SEED_BUTTON.setOnMouseEntered(changeNodeCursor(RANDOM_SEED_BUTTON, Cursor.HAND));

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