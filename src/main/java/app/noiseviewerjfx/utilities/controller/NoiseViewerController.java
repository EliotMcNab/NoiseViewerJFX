package app.noiseviewerjfx.utilities.controller;

import app.noiseviewerjfx.utilities.controller.valueControllers.*;
import app.noiseviewerjfx.utilities.controller.valueControllers.associative.*;
import app.noiseviewerjfx.utilities.controller.valueControllers.associative.switches.SwitchCheckBox;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.MaskValueController;
import app.noiseviewerjfx.utilities.controller.valueControllers.settings.NoiseValueController;
import app.noiseviewerjfx.utilities.tasks.UpdateManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

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
    @FXML
    private Spinner<Integer> MASK_OPACITY_SPINNER;
    private final SpinnerValueFactory<Integer> maskOpacitySpinnerVF =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 100);
    @FXML
    private Spinner<Integer> TERRAIN_LAYER_OPACITY_SPINNER;
    private final SpinnerValueFactory<Integer> terrainLayerOpacitySpinnerVF =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 100);
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
    private ProgressBar MASK_OPACITY_PROGRESS_BAR;

    @FXML
    private ProgressBar TERRAIN_LAYER_OPACITY_PROGRESS_BAR;
    // endregion

    // region BUTTONS
    @FXML
    private Button RANDOM_OCTAVE_BUTTON;

    @FXML
    private Button RANDOM_PERSISTENCE_BUTTON;

    @FXML
    private Button RANDOM_SEED_BUTTON;

    // endregion

    // region ICON BUTTONS

    @FXML
    private Button NOISE_RESET_BUTTON;

    @FXML
    private Button MASK_VISIBILITY_BUTTON;

    @FXML
    private Button TERRAIN_VISIBILITY_BUTTON;

    @FXML
    private Button TERRAIN_LAYER_VISIBILITY_BUTTON;

    @FXML
    private Button TERRAIN_LAYER_LOCK_BUTTON;

    @FXML
    private Button MASK_RESET_BUTTON;

    // endregion

    // region ICONS

    @FXML
    private FontIcon NOISE_RESET_ICON;

    @FXML
    private FontIcon MASK_VISIBILITY_ICON;

    @FXML
    private FontIcon MASK_RESET_ICON;

    @FXML
    private FontIcon TERRAIN_VISIBILITY_ICON;

    @FXML
    private FontIcon TERRAIN_LAYER_VISIBILITY_ICON;

    @FXML
    private FontIcon TERRAIN_LAYER_LOCK_ICON;

    // endregion

    // region CHECKBOX

    @FXML
    private CheckBox APPLY_MASK_CHECKBOX;

    @FXML
    private CheckBox CIRCLE_MASK_CHECKBOX;

    @FXML
    private CheckBox RECTANGLE_MASK_CHECKBOX;

    // endregion

    // region IMAGE VIEW

    @FXML
    private ImageView WORLD_IMAGE_VIEW;

    // endregion

    // region HBOX

    @FXML
    private HBox WORLD_CONTAINER;

    // endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MASK_WIDTH_SLIDER.setValue(50);
        MASK_HEIGHT_SLIDER.setValue(50);

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
                PERSISTENCE_SPINNER, persistenceSpinnerVF, 0.1, "", 1
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
        AssociativeIntegerSpinner maskOpacitySpinner = new AssociativeIntegerSpinner(
                MASK_OPACITY_SPINNER, maskOpacitySpinnerVF, 100, "%"
        );
        AssociativeIntegerSpinner terrainLayerOpacitySpinner = new AssociativeIntegerSpinner(
                TERRAIN_LAYER_OPACITY_SPINNER, terrainLayerOpacitySpinnerVF, 100, "%"
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

        AssociativeDraggableProgressBar terrainLayerOpacityProgressBar = new AssociativeDraggableProgressBar(TERRAIN_LAYER_OPACITY_PROGRESS_BAR);

        AssociativeDraggableProgressBar maskOpacityProgressBar = new AssociativeDraggableProgressBar(MASK_OPACITY_PROGRESS_BAR);

        // endregion

        // region BUTTON

        RandomIntegerButtonValueController randomOctaveButton = new RandomIntegerButtonValueController(
                RANDOM_OCTAVE_BUTTON, 1, 10);
        RandomDoubleButtonValueController randomPersistenceButton = new RandomDoubleButtonValueController(
                RANDOM_PERSISTENCE_BUTTON, 0.1, 1);
        RandomIntegerButtonValueController randomSeedButton = new RandomIntegerButtonValueController(
                RANDOM_SEED_BUTTON, 0, Integer.MAX_VALUE);

        // endregion

        // region ICON BUTTONS

        IconButtonValueController noiseResetButton = new IconButtonValueController(
                NOISE_RESET_BUTTON,
                NOISE_RESET_ICON,
                "fas-sync-alt",
                "fas-sync-alt",
                true
        );

        IconButtonValueController maskVisibilityButton = new IconButtonValueController(
                MASK_VISIBILITY_BUTTON,
                MASK_VISIBILITY_ICON,
                "far-eye-slash",
                "far-eye"
        );

        IconButtonValueController maskResetButton = new IconButtonValueController(
                MASK_RESET_BUTTON,
                MASK_RESET_ICON,
                "fas-sync-alt",
                "fas-sync-alt",
                true
        );

        IconButtonValueController terrainVisibilityButton = new IconButtonValueController(
                TERRAIN_VISIBILITY_BUTTON,
                TERRAIN_VISIBILITY_ICON,
                "far-eye-slash",
                "far-eye"
        );

        IconButtonValueController terrainLayerVisibilityButton = new IconButtonValueController(
                TERRAIN_LAYER_VISIBILITY_BUTTON,
                TERRAIN_LAYER_VISIBILITY_ICON,
                "far-eye-slash",
                "far-eye"
        );

        IconButtonValueController terrainLayerLockButton = new IconButtonValueController(
                TERRAIN_LAYER_LOCK_BUTTON,
                TERRAIN_LAYER_LOCK_ICON,
                "fas-lock",
                "fas-lock-open"

        );

        // endregion

        // region CHECKBOX

        SwitchCheckBox circleCheckBox               = new SwitchCheckBox(CIRCLE_MASK_CHECKBOX);
        SwitchCheckBox rectangleCheckBox            = new SwitchCheckBox(RECTANGLE_MASK_CHECKBOX);
        CheckBoxValueController applyMaskCheckBox   = new CheckBoxValueController(APPLY_MASK_CHECKBOX);

        // endregion

        // region NOISE DISPLAY

        NoiseValueController noiseValueController = new NoiseValueController(
                noiseResetButton,
                octaveSpinner,
                persistenceSpinner,
                seedTextField,
                mapWidthSpinnerVC,
                mapHeightSpinnerVC
        );

        MaskValueController maskValueController = new MaskValueController(
                maskResetButton,
                maskVisibilityButton,
                applyMaskCheckBox,
                maskStrengthSpinner,
                circleCheckBox,
                rectangleCheckBox,
                maskOpacityProgressBar,
                maskWidthSlider,
                maskHeightSlider
        );

        NoiseDisplayHandler noiseDisplay = new NoiseDisplayHandler(
                WORLD_IMAGE_VIEW,
                noiseValueController,
                maskValueController
        );

        // endregion

        // region UPDATABLE

        octaveSpinner.addAssociatedNode(randomOctaveButton);
        persistenceSpinner.addAssociatedNode(randomPersistenceButton);
        seedTextField.addAssociatedNode(randomSeedButton);
        maskWidthSlider.addAssociatedNode(maskWidthSpinner);
        maskWidthSpinner.addAssociatedNode(maskWidthSlider);
        maskHeightSlider.addAssociatedNode(maskHeightSpinner);
        maskHeightSpinner.addAssociatedNode(maskHeightSlider);
        maskStrengthSlider.addAssociatedNode(maskStrengthSpinner);
        maskStrengthSpinner.addAssociatedNode(maskStrengthSlider);
        rectangleCheckBox.addAssociatedNode(circleCheckBox);
        circleCheckBox.addAllAssociatedNodes(rectangleCheckBox);
        maskOpacitySpinner.addAssociatedNode(maskOpacityProgressBar);
        maskOpacityProgressBar.addAssociatedNode(maskOpacitySpinner);
        terrainLayerOpacitySpinner.addAssociatedNode(terrainLayerOpacityProgressBar);
        terrainLayerOpacityProgressBar.addAssociatedNode(terrainLayerOpacitySpinner);

        UpdateManager noiseUpdateManager = new UpdateManager();
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
                maskOpacityProgressBar,
                maskOpacitySpinner,
                terrainLayerOpacityProgressBar,
                terrainLayerOpacitySpinner,
                rectangleCheckBox,
                circleCheckBox,
                noiseDisplay,
                noiseValueController,
                maskValueController
        );
        noiseUpdateManager.startUpdating();

        // endregion

        // region TESTING

        ZoomController testZoomController = new ZoomController(
                DISPLAY_SCROLL_PANE,
                BACKGROUND,
                WORLD_CONTAINER
        );

        // endregion

    }

}