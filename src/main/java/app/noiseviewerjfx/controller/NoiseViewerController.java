package app.noiseviewerjfx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

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
    @FXML
    private Spinner<Integer> OPACITY_SPINNER;
    private final SpinnerValueFactory<Integer> opacitySpinnerVF =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);

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
        opacitySpinnerVF.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer integer) {
                return integer.toString() + "%";
            }

            @Override
            public Integer fromString(String valueString) {
                String valueWithoutUnits = valueString.replaceAll("%", "").trim();
                if (valueWithoutUnits.isEmpty()) return 0;

                try {
                    return Integer.parseInt(valueWithoutUnits);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        });

        OCTAVE_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        PERSISTENCE_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        WIDTH_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        HEIGHT_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        OPACITY_SPINNER.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);

        OCTAVE_SPINNER.setValueFactory(octaveSpinnerVF);
        PERSISTENCE_SPINNER.setValueFactory(persistenceSpinnerVF);
        WIDTH_SPINNER.setValueFactory(widthSpinnerVF);
        HEIGHT_SPINNER.setValueFactory(heightSpinnerVF);
        OPACITY_SPINNER.setValueFactory(opacitySpinnerVF);
    }
}