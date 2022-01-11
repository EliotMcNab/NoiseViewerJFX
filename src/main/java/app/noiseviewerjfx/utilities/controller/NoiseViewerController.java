package app.noiseviewerjfx.utilities.controller;

import app.noiseviewerjfx.utilities.controller.valueControllers.*;
import app.noiseviewerjfx.utilities.tasks.PeriodicTask;
import app.noiseviewerjfx.utilities.processing.ImageProcessing;
import app.noiseviewerjfx.utilities.processing.NoiseProcessing;
import app.noiseviewerjfx.utilities.tasks.UpdateManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private Spinner<Double> PERSISTENCE_SPINNER;
    private final SpinnerValueFactory<Double> persistenceSpinnerVF =
            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1, 1, 0.1, 0.1);
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

        UpdateManager noiseUpdateManager = new UpdateManager();

        // region SPINNERS

        IntegerSpinnerValueController mapWidthSpinnerVC = new IntegerSpinnerValueController(
                MAP_WIDTH_SPINNER, mapWidthSpinnerVF, 100, ""
        );
        IntegerSpinnerValueController mapHeightSpinnerVC = new IntegerSpinnerValueController(
                MAP_HEIGHT_SPINNER, mapHeightSpinnerVF, 100, ""
        );

        UpdatableIntegerSpinner octaveSpinner = new UpdatableIntegerSpinner(
                OCTAVE_SPINNER, octaveSpinnerVF, 1, ""
        );
        UpdatableDoubleSpinner persistenceSpinner = new UpdatableDoubleSpinner(
                PERSISTENCE_SPINNER, persistenceSpinnerVF, 0.1, ""
        );
        UpdatableIntegerSpinner maskWidthSpinner = new UpdatableIntegerSpinner(
                MASK_WIDTH_SPINNER, maskWidthSpinnerVF, 50, "%"
        );
        UpdatableIntegerSpinner maskHeightSpinner = new UpdatableIntegerSpinner(
                MASK_HEIGHT_SPINNER, maskHeightSpinnerVF, 50, "%"
        );
        UpdatableIntegerSpinner maskStrengthSpinner = new UpdatableIntegerSpinner(
                MASK_STRENGTH_SPINNER, maskStrengthSpinnerVF, 1, ""
        );
        UpdatableIntegerSpinner opacitySpinner = new UpdatableIntegerSpinner(
                OPACITY_SPINNER, opacitySpinnerVF, 100, "%"
        );


        // endregion

        // region SLIDER

        UpdatableSlider maskWidthSlider     = new UpdatableSlider(MASK_WIDTH_SLIDER);
        UpdatableSlider maskHeightSlider    = new UpdatableSlider(MASK_HEIGHT_SLIDER);
        UpdatableSlider maskStrengthSlider  = new UpdatableSlider(MASK_STRENGTH_SLIDER);

        // endregion

        // region TEXT FIELDS

        UpdatableIntegerTextField seedTextField = new UpdatableIntegerTextField(SEED_TEXT_FIELD, 0, "");

        // endregion

        // region PROGRESS BAR

        UpdatableProgressBar opacityProgressBar = new UpdatableProgressBar(OPACITY_PROGRESS_BAR);

        // endregion

        // region button

        RandomIntegerButtonValueController randomOctaveButton = new RandomIntegerButtonValueController(
                RANDOM_OCTAVE_BUTTON, 1, 10);
        RandomDoubleButtonValueController randomPersistenceButton = new RandomDoubleButtonValueController(
                RANDOM_PERSISTENCE_BUTTON, 0.1, 1);
        RandomIntegerButtonValueController randomSeedButton = new RandomIntegerButtonValueController(
                RANDOM_SEED_BUTTON, 0, Integer.MAX_VALUE);

        // endregion

        // region UPDATABLE

        noiseUpdateManager.registerUpdatable(octaveSpinner, randomOctaveButton);
        noiseUpdateManager.registerUpdatable(persistenceSpinner, randomPersistenceButton);
        noiseUpdateManager.registerUpdatable(seedTextField, randomSeedButton);
        noiseUpdateManager.registerUpdatable(maskWidthSlider, maskWidthSpinner);
        noiseUpdateManager.registerUpdatable(maskWidthSpinner, maskWidthSlider);
        noiseUpdateManager.registerUpdatable(maskHeightSlider, maskHeightSpinner);
        noiseUpdateManager.registerUpdatable(maskHeightSpinner, maskHeightSlider);
        noiseUpdateManager.registerUpdatable(maskStrengthSlider, maskStrengthSpinner);
        noiseUpdateManager.registerUpdatable(maskStrengthSpinner, maskStrengthSlider);
        noiseUpdateManager.registerUpdatable(opacityProgressBar, opacitySpinner);

        // endregion

        // region TESTING

        PeriodicTask periodicTask = new PeriodicTask(10) {
            @Override
            public void run() {
                noiseUpdateManager.update();
            }
        };
        periodicTask.start();

        int[][] noise = NoiseProcessing.PerlinNoise.generatePerlinNoise(
                100,
                100,
                4,
                0.4f,
                234567898L
        );

        Image noiseImage = ImageProcessing.toGrayScale(noise);

        WORLD_IMAGE_VIEW.setImage(noiseImage);

        // endregion

    }

}