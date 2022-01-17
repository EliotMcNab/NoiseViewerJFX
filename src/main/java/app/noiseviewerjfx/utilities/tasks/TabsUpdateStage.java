package app.noiseviewerjfx.utilities.tasks;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.HashSet;
import java.util.Set;

public class TabsUpdateStage extends UpdateStage {

    private final TabPane TAB_PANE;
    private final Set<String> TAB_TITLES = new HashSet<>();

    public TabsUpdateStage(TabPane tabPane) {
        this.TAB_PANE = tabPane;

        getTabTitles();
        addListeners();
    }

    private void getTabTitles() {
        for (Tab tab : TAB_PANE.getTabs()) {
            TAB_TITLES.add(tab.getText());
        }
    }

    private void addListeners() {
        TAB_PANE.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldTab, newTab) -> {
            deactivateScene(TAB_PANE.getTabs().get(oldTab.intValue()).getText());
            activateScene(TAB_PANE.getTabs().get(newTab.intValue()).getText());
        });
    }

    @Override
    public boolean addScene(String key, UpdateScene scene) {
        if (!TAB_TITLES.contains(key)) {
            System.out.printf("ERROR: could not add scene %s to TabsUpdateStage %s " +
                    "because key %s does not match an existing tab pane title",
                    scene, this, key);
            return false;
        }
        super.addScene(key, scene);
        return true;
    }

    public boolean setDefault(String key) {
        if (!TAB_TITLES.contains(key)) {
            System.out.printf("ERROR: could not set %s to be default tab as it is not a valid tab title", key);
            return false;
        }
        activateScene(key);
        return true;
    }
}
