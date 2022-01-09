package app.noiseviewerjfx.utilities.io;

public enum Directories {

    EXPORT("src/export/"),
    IMAGES("src/export/images/"),
    ICONS("/com/example/learnfx/icons/"),
    FXML_LOCATION("\"hello-view.fxml\"");

    private final String path;

    Directories(String path) {
        this.path = path;
    }

    public String pathTo() {
        return path;
    }

}
