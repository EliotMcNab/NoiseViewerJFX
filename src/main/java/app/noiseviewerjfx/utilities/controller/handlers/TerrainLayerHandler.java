package app.noiseviewerjfx.utilities.controller.handlers;

import app.noiseviewerjfx.utilities.controller.valueControllers.ValueController;
import javafx.scene.layout.HBox;

public class TerrainLayerHandler {

    private final ValueController NEW_LAYER;
    private final ValueController DELETE_LAYER;
    private final HBox LAYER_CONTAINER;

    public TerrainLayerHandler(
        ValueController newLayer,
        ValueController deleteLayer,
        HBox layerContainer
    ) {
        this.NEW_LAYER          = newLayer;
        this.DELETE_LAYER       = deleteLayer;
        this.LAYER_CONTAINER    = layerContainer;
    }

}
