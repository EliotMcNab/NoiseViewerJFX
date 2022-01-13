package app.noiseviewerjfx.utilities.controller;

import app.noiseviewerjfx.utilities.controller.valueControllers.*;
import app.noiseviewerjfx.utilities.controller.valueControllers.associative.*;
import app.noiseviewerjfx.utilities.io.input.Keyboard;
import app.noiseviewerjfx.utilities.tasks.PeriodicTask;
import app.noiseviewerjfx.utilities.tasks.UpdateManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Noise Viewer application
 */
public class NoiseViewerController implements Initializable {

    @FXML
    private AnchorPane BACKGROUND;

    @FXML
    private ScrollPane DISPLAY_SCROLL_PANE;

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

        AssociativeIntegerSpinner octaveSpinner = new AssociativeIntegerSpinner(
                OCTAVE_SPINNER, octaveSpinnerVF, 1, ""
        );
        AssociativeDoubleSpinner persistenceSpinner = new AssociativeDoubleSpinner(
                PERSISTENCE_SPINNER, persistenceSpinnerVF, 0.1, ""
        );
        AssociativeIntegerSpinner maskWidthSpinner = new AssociativeIntegerSpinner(
                MASK_WIDTH_SPINNER, maskWidthSpinnerVF, 50, "%"
        );
        AssociativeIntegerSpinner maskHeightSpinner = new AssociativeIntegerSpinner(
                MASK_HEIGHT_SPINNER, maskHeightSpinnerVF, 50, "%"
        );
        AssociativeIntegerSpinner maskStrengthSpinner = new AssociativeIntegerSpinner(
                MASK_STRENGTH_SPINNER, maskStrengthSpinnerVF, 1, ""
        );
        AssociativeIntegerSpinner opacitySpinner = new AssociativeIntegerSpinner(
                OPACITY_SPINNER, opacitySpinnerVF, 100, "%"
        );


        // endregion

        // region SLIDER

        AssociativeSlider maskWidthSlider     = new AssociativeSlider(MASK_WIDTH_SLIDER);
        AssociativeSlider maskHeightSlider    = new AssociativeSlider(MASK_HEIGHT_SLIDER);
        AssociativeSlider maskStrengthSlider  = new AssociativeSlider(MASK_STRENGTH_SLIDER);

        // endregion

        // region TEXT FIELDS

        AssociativeIntegerTextField seedTextField = new AssociativeIntegerTextField(SEED_TEXT_FIELD, 0, "");

        // endregion

        // region PROGRESS BAR

        AssociativeProgressBar opacityProgressBar = new AssociativeProgressBar(OPACITY_PROGRESS_BAR);

        // endregion

        // region button

        RandomIntegerButtonValueController randomOctaveButton = new RandomIntegerButtonValueController(
                RANDOM_OCTAVE_BUTTON, 1, 10);
        RandomDoubleButtonValueController randomPersistenceButton = new RandomDoubleButtonValueController(
                RANDOM_PERSISTENCE_BUTTON, 0.1, 1);
        RandomIntegerButtonValueController randomSeedButton = new RandomIntegerButtonValueController(
                RANDOM_SEED_BUTTON, 0, Integer.MAX_VALUE);

        // endregion

        // region NOISE DISPLAY

        /*NoiseDisplayHandler noiseDisplay = new NoiseDisplayHandler(
                WORLD_IMAGE_VIEW,
                octaveSpinner,
                persistenceSpinner,
                seedTextField,
                mapWidthSpinnerVC,
                mapHeightSpinnerVC,
                maskWidthSpinner,
                maskHeightSpinner,
                maskStrengthSpinner
        );*/

        // endregion

        // region UPDATABLE

        octaveSpinner.setAssociateNode(randomOctaveButton);
        persistenceSpinner.setAssociateNode(randomPersistenceButton);
        seedTextField.setAssociateNode(randomSeedButton);
        maskWidthSlider.setAssociateNode(maskWidthSpinner);
        maskWidthSpinner.setAssociateNode(maskWidthSlider);
        maskHeightSlider.setAssociateNode(maskHeightSpinner);
        maskHeightSpinner.setAssociateNode(maskHeightSlider);
        maskStrengthSlider.setAssociateNode(maskStrengthSpinner);
        maskStrengthSpinner.setAssociateNode(maskStrengthSlider);
        opacityProgressBar.setAssociateNode(opacitySpinner);

        noiseUpdateManager.registerAll(
                octaveSpinner,
                persistenceSpinner,
                seedTextField,
                maskWidthSlider,
                maskWidthSpinner,
                maskHeightSlider,
                maskHeightSpinner,
                maskStrengthSlider,
                maskStrengthSpinner,
                opacityProgressBar/*,
                noiseDisplay*/
        );

        // endregion

        // region TESTING

        PeriodicTask periodicTask = new PeriodicTask(10) {
            @Override
            public void run() {
                noiseUpdateManager.update();
            }
        };
        periodicTask.start();

        ZoomController testZoomController = new ZoomController(
                DISPLAY_SCROLL_PANE,
                BACKGROUND,
                WORLD_IMAGE_VIEW
        );

        Platform.runLater(() -> DISPLAY_SCROLL_PANE.requestFocus());

        /*int[][] noise = NoiseProcessing.PerlinNoise.generatePerlinNoise(
                100,
                100,
                4,
                0.4f,
                234567898L
        );

        Image noiseImage = ImageProcessing.toGrayScale(noise);

        WORLD_IMAGE_VIEW.setImage(noiseImage);*/

        // endregion

    }

}