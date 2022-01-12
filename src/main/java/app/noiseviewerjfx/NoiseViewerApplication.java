package app.noiseviewerjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class NoiseViewerApplication extends Application {

    private Scene mainScene;
    private Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(NoiseViewerApplication.class.getResource("NoiseViewer-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        mainStage = stage;

        Image appIcon = new Image(getClass().getResourceAsStream("/app/noiseviewerjfx/icons/terrain.png"));
        mainStage.getIcons().add(appIcon);

        mainStage.setTitle("Noise Viewer");
        mainStage.setScene(scene);

        // displays the stage
        mainStage.show();

        // sets the app's minimal size to be it's starting size
        mainStage.setMinHeight(mainStage.getHeight());
        mainStage.setMinWidth(mainStage.getWidth());
    }

    public static void launchApplication(String[] args) {
        launch();
    }
}