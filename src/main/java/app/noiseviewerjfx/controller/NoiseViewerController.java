package app.noiseviewerjfx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class NoiseViewerController implements Initializable {
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
            new SpinnerValueFactory.IntegerSpinnerValueFactory(100, 1000, 1);
    @FXML
    private Spinner<Integer> HEIGHT_SPINNER;
    private final SpinnerValueFactory<Integer> heightSpinnerVF =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(100, 1000, 1);

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
        OCTAVE_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        PERSISTENCE_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        WIDTH_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        HEIGHT_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);

        OCTAVE_SPINNER.setValueFactory(octaveSpinnerVF);
        PERSISTENCE_SPINNER.setValueFactory(persistenceSpinnerVF);
        WIDTH_SPINNER.setValueFactory(widthSpinnerVF);
        HEIGHT_SPINNER.setValueFactory(heightSpinnerVF);
    }
}