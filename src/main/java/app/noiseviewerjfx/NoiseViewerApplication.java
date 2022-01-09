package app.noiseviewerjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NoiseViewerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(NoiseViewerApplication.class.getResource("NoiseViewer-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Noise Viewer");
        stage.setScene(scene);

        // displays the stage
        stage.show();

        // sets the app's minimal size to be it's starting size
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }

    public static void launchApplication(String[] args) {
        launch();
    }
}