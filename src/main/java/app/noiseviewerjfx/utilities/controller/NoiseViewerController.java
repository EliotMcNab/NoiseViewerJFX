package app.noiseviewerjfx.utilities.controller;

import app.noiseviewerjfx.utilities.TextValidation;
import app.noiseviewerjfx.utilities.processing.ImageProcessing;
import app.noiseviewerjfx.utilities.processing.NoiseProcessing;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.Buffer;
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

    // region TEXT FIELDS
    @FXML
    private TextField SEED_TEXT_FIELD;
    // endregion

    // region PROGRESS BARS
    @FXML
    private ProgressBar OPACITY_PROGRESS_BAR;
    // endregion

    // region BUTTONS
    @FXML
    private Button RANDOM_OCTAVE_BUTTON;

    @FXML
    private Button RANDOM_PERSISTENCE_BUTTON;

    @FXML
    private Button RANDOM_SEED_BUTTON;

    // endregion

    // region IMAGE VIEW

    @FXML
    private ImageView WORLD_IMAGE_VIEW;

    // endregion

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

        SliderValueController maskWidthSliderVC = new SliderValueController(MASK_WIDTH_SLIDER, MASK_WIDTH_SPINNER);
        SliderValueController maskHeightSliderVC = new SliderValueController(MASK_HEIGHT_SLIDER, MASK_HEIGHT_SPINNER);
        SliderValueController maskStrengthSliderVC = new SliderValueController(MASK_STRENGTH_SLIDER, MASK_STRENGTH_SPINNER);

        // endregion

        // region TEXT FIELDS

        SEED_TEXT_FIELD.setTextFormatter(TextValidation.newIntegerFormatter());

        // endregion

        // region button

        RANDOM_OCTAVE_BUTTON.setOnMouseEntered(NodeController.changeNodeCursor(RANDOM_OCTAVE_BUTTON, Cursor.HAND));
        RANDOM_PERSISTENCE_BUTTON.setOnMouseEntered(NodeController.changeNodeCursor(RANDOM_PERSISTENCE_BUTTON, Cursor.HAND));
        RANDOM_SEED_BUTTON.setOnMouseEntered(NodeController.changeNodeCursor(RANDOM_SEED_BUTTON, Cursor.HAND));

        // endregion

        int[][] noise = NoiseProcessing.PerlinNoise.generatePerlinNoise(
                100,
                100,
                1,
                0.1f,
                234567898L
        );

        Image noiseImage = ImageProcessing.toGrayScale(noise);

        WORLD_IMAGE_VIEW.setImage(noiseImage);


    }

    // =====================================
    //         LISTENERS GENERATORS
    // =====================================

    // region SLIDERS



    // endregion
}