package me.ali.mediaplayer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.ali.mediaplayer.files.domain.MediaFile;
import me.ali.mediaplayer.files.impl.PlayableMediaFile;
import me.ali.mediaplayer.managers.MediaFileManager;

import java.io.File;

public class Main extends Application {

    FileChooser fileChooser = new FileChooser();
    MediaFileManager mediaFileManager = new MediaFileManager();

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 500, 500);
        FileChooser.ExtensionFilter videoFilter = new FileChooser
                .ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mov");
        FileChooser.ExtensionFilter imageFilter = new FileChooser
                .ExtensionFilter("Image Files", "*.png", "*.jpg");
        fileChooser.getExtensionFilters().addAll(videoFilter, imageFilter);

        Button button = new Button("Choose File");

        button.setOnAction(actionEvent -> {
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                MediaFile multimediaFile = mediaFileManager.createMultimediaFile(selectedFile);
                multimediaFile.display(root);
                root.setOnMouseClicked(mouseEvent -> {
                    if (multimediaFile instanceof PlayableMediaFile playableMediaFile) {
                        playableMediaFile.toggleMedia(root);
                    }
                });
            }
        });

        stage.setTitle("MultiMedia Player");
        stage.setScene(scene);
        stage.show();

        root.getChildren().add(button);
    }

    public static void main(String[] args) {
        launch(args);
    }

}