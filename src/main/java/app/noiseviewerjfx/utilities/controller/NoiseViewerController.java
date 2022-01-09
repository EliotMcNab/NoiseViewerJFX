package app.noiseviewerjfx.utilities.controller;

import app.noiseviewerjfx.utilities.text.TextValidation;
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

    @FXML
    private TextField SEED_TEXT_FIELD;

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

        // value validation
        OCTAVE_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerFormatter());
        PERSISTENCE_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerFormatter());
        WIDTH_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerFormatter());
        HEIGHT_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerFormatter());

        maskHeightSpinnerVF.setConverter(TextValidation.newIntegerPercentageConverter());
        MASK_HEIGHT_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerPercentageFormatter());
        maskWidthSpinnerVF.setConverter(TextValidation.newIntegerPercentageConverter());
        MASK_WIDTH_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerPercentageFormatter());
        MASK_STRENGTH_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerFormatter());

        opacitySpinnerVF.setConverter(TextValidation.newIntegerPercentageConverter());
        OPACITY_SPINNER.getEditor().setTextFormatter(TextValidation.newIntegerPercentageFormatter());

        // styling
        OCTAVE_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        PERSISTENCE_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        WIDTH_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        HEIGHT_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);

        MASK_WIDTH_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        MASK_HEIGHT_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        MASK_STRENGTH_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);

        OPACITY_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);

        // spinner values
        OCTAVE_SPINNER.setValueFactory(octaveSpinnerVF);
        PERSISTENCE_SPINNER.setValueFactory(persistenceSpinnerVF);
        WIDTH_SPINNER.setValueFactory(widthSpinnerVF);
        HEIGHT_SPINNER.setValueFactory(heightSpinnerVF);

        MASK_WIDTH_SPINNER.setValueFactory(maskWidthSpinnerVF);
        MASK_HEIGHT_SPINNER.setValueFactory(maskHeightSpinnerVF);
        MASK_STRENGTH_SPINNER.setValueFactory(maskStrengthSpinnerVF);

        OPACITY_SPINNER.setValueFactory(opacitySpinnerVF);

        // listeners

        // octaves
        OCTAVE_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(OCTAVE_SPINNER));

        // persistence
        PERSISTENCE_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(PERSISTENCE_SPINNER));


        // width
        WIDTH_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(WIDTH_SPINNER));

        // height
        HEIGHT_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(HEIGHT_SPINNER));

        // mask width
        MASK_WIDTH_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(MASK_WIDTH_SPINNER));
        // value sync between spinner and slider
        MASK_WIDTH_SLIDER.setValue(MASK_WIDTH_SPINNER.getValue());
        MASK_WIDTH_SPINNER.setOnKeyPressed(updateAssociatedSliderOnKeyPress(MASK_WIDTH_SLIDER, MASK_WIDTH_SPINNER));
        MASK_WIDTH_SPINNER.valueProperty().addListener(updateAssociatedSlider(MASK_WIDTH_SLIDER));

        // mask height
        MASK_HEIGHT_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(MASK_HEIGHT_SPINNER));
        // value sync between spinner and slider
        MASK_HEIGHT_SLIDER.setValue(MASK_HEIGHT_SPINNER.getValue());
        MASK_HEIGHT_SPINNER.setOnKeyPressed(updateAssociatedSliderOnKeyPress(MASK_HEIGHT_SLIDER, MASK_HEIGHT_SPINNER));
        MASK_HEIGHT_SPINNER.valueProperty().addListener(updateAssociatedSlider(MASK_HEIGHT_SLIDER));

        // mask strength
        MASK_STRENGTH_SLIDER.setValue(MASK_STRENGTH_SPINNER.getValue());
        MASK_STRENGTH_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(MASK_STRENGTH_SPINNER));
        MASK_STRENGTH_SPINNER.setOnKeyPressed(updateAssociatedSliderOnKeyPress(MASK_STRENGTH_SLIDER, MASK_STRENGTH_SPINNER));

        // opacity
        OPACITY_PROGRESS_BAR.setProgress((double) OPACITY_SPINNER.getValue() / 100);
        OPACITY_SPINNER.getEditor().focusedProperty().addListener(selectAllSpinnerText(OPACITY_SPINNER));
        OPACITY_SPINNER.setOnKeyPressed(updateAssociatedProgressBarOnKeyPress(OPACITY_PROGRESS_BAR, OPACITY_SPINNER));

        // endregion

        // region SLIDER

        MASK_WIDTH_SLIDER.valueProperty().addListener(updateAssociatedSpinner(MASK_WIDTH_SPINNER));
        MASK_HEIGHT_SLIDER.valueProperty().addListener(updateAssociatedSpinner(MASK_HEIGHT_SPINNER));
        MASK_STRENGTH_SLIDER.valueProperty().addListener(updateAssociatedSpinner(MASK_STRENGTH_SPINNER));
        MASK_STRENGTH_SLIDER.setSnapToTicks(true);

        MASK_WIDTH_SLIDER.setOnMouseEntered(changeNodeCursor(MASK_WIDTH_SLIDER, Cursor.H_RESIZE));
        MASK_HEIGHT_SLIDER.setOnMouseEntered(changeNodeCursor(MASK_HEIGHT_SLIDER, Cursor.H_RESIZE));
        MASK_STRENGTH_SLIDER.setOnMouseEntered(changeNodeCursor(MASK_STRENGTH_SLIDER, Cursor.H_RESIZE));

        // endregion

        // region TEXT FIELDS

        SEED_TEXT_FIELD.setTextFormatter(TextValidation.newIntegerFormatter());

        // endregion

        // region PROGRESS BARS

        // endregion
    }

    // =====================================
    //         LISTENERS GENERATORS
    // =====================================

    // region SPINNERS
    private static ChangeListener<Integer> updateAssociatedSlider(Slider targetSlider) {
        return (observableValue, oldValue, newValue) -> targetSlider.setValue(newValue);
    }

    private static EventHandler<KeyEvent> updateAssociatedSliderOnKeyPress(Slider targetSlider, Spinner<Integer> valueSpinner) {
        return keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) targetSlider.setValue(valueSpinner.getValue());
        };
    }

    private static EventHandler<KeyEvent> updateAssociatedProgressBarOnKeyPress(ProgressBar targetProgressBar, Spinner<Integer> valueSpinner) {
        return keyEvent -> {
            System.out.println("here");
            if (keyEvent.getCode().equals(KeyCode.ENTER)) targetProgressBar.setProgress((double) valueSpinner.getValue() / 100);
        };
    }

    private static ChangeListener<Boolean> selectAllSpinnerText(Spinner<?> targetSpinner) {
        return (observableValue, oldState, newState) -> {
            if (newState) Platform.runLater(targetSpinner.getEditor()::selectAll);
        };
    }
    // endregion

    // region SLIDERS
    private static ChangeListener<Number> updateAssociatedSpinner(Spinner<Integer> targetSpinner) {
        return new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                targetSpinner.getValueFactory().setValue(newValue.intValue());
            }
        };
    }

    private static EventHandler<MouseEvent> changeNodeCursor(Node targetNode, Cursor newCursor) {
        return mouseEvent -> targetNode.setCursor(newCursor);
    }

    // endregion
}