package me.ali.mediaplayer;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.ali.mediaplayer.files.domain.MediaFile;
import me.ali.mediaplayer.files.impl.PlayableMediaFile;
import me.ali.mediaplayer.managers.MediaFileManager;

public class Main extends Application {
    FileChooser fileChooser = new FileChooser();
    MediaFileManager mediaFileManager = new MediaFileManager();

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        StackPane displayPane = new StackPane();

        root.setPadding(new Insets(10));

        root.setCenter(displayPane);

        Scene scene = new Scene(root, 500, 500);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        scene.setOnDragOver(event -> {
            if (!event.getDragboard().hasFiles()) return;
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        });

        scene.setOnDragDropped(event -> {
            if (!event.getDragboard().hasFiles()) return;
            event.getDragboard()
                    .getFiles()
                    .forEach(file -> mediaFileManager.createAndAddMediaFile(file));
            ListView<MediaFile> mediaFileListView = new ListView<>();
            mediaFileListView.setCellFactory(cell -> new ListCell<>() {
                @Override
                protected void updateItem(MediaFile item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        if (item instanceof PlayableMediaFile playableMediaFile) {
                            setText(item.getTitle() + " | " + item.getType() + " | " + playableMediaFile.getDuration().toSeconds());
                            return;
                        }
                        setText(item.getTitle() + " | " + item.getType() + " | " + item.getAuthor());
                        setFont(Font.font(16));
                    }
                }
            });

            mediaFileListView.setShape(new Rectangle(100, 100));
            mediaFileListView.getItems().addAll(mediaFileManager.getMediaFiles());
            mediaFileListView.setOnMouseClicked(click -> {
                ObservableList<MediaFile> selectedItems = mediaFileListView.getSelectionModel().getSelectedItems();
                selectedItems.forEach(mediaFile -> {
                    mediaFile.display(displayPane);
                    if (mediaFile instanceof PlayableMediaFile playableMediaFile) {
                        playableMediaFile.toggleMedia(displayPane);
                    }
                });
            });

            mediaFileManager.getMediaFiles()
                    .forEach(mediaFile -> root.setRight(mediaFileListView));
        });

        stage.setMaximized(true);
        stage.setTitle("MultiMedia Player");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    //        FileChooser.ExtensionFilter videoFilter = new FileChooser
//                .ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mov");
//        FileChooser.ExtensionFilter imageFilter = new FileChooser
//                .ExtensionFilter("Image Files", "*.png", "*.jpg");
//        fileChooser.getExtensionFilters().addAll(videoFilter, imageFilter);
//
//        Button button = new Button("Choose File");
//
//        button.setOnAction(actionEvent -> {
//            File selectedFile = fileChooser.showOpenDialog(stage);
//
//            if (selectedFile != null) {
//                MediaFile multimediaFile = mediaFileManager.createMultimediaFile(selectedFile);
//                multimediaFile.display(root);
//                root.setOnMouseClicked(mouseEvent -> {
//                    if (multimediaFile instanceof PlayableMediaFile playableMediaFile) {
//                        playableMediaFile.toggleMedia(root);
//                    }
//                });
//            }
//        });
//
//        Label label = new Label("DROP ME");
//        label.setVisible(false);
//        root.setTop(label);
//
//        scene.setOnDragOver(event -> {
//            if (!event.getDragboard().hasFiles()) return;
//            label.setVisible(true);
//            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//        });
//        scene.setOnDragExited(event -> label.setVisible(false));
//        scene.setOnDragDropped(event -> {
//            if (!event.getDragboard().hasFiles()) return;
//            label.setText("FILE!");
//        });

}