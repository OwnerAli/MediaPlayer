package me.ali.mediaplayer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.ali.mediaplayer.files.domain.MediaFile;
import me.ali.mediaplayer.files.impl.PlayableMediaFile;
import me.ali.mediaplayer.managers.MediaFileManager;

public class Main extends Application {

    private final MediaFileManager mediaFileManager = new MediaFileManager();

    @Override
    public void start(Stage stage) {
        // Setup border pane and give it a bg color
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.rgb(24, 24, 24),
                CornerRadii.EMPTY, Insets.EMPTY)));
        root.setPadding(new Insets(10));

        // Setup stack pane, give it a bg color and background image
        StackPane displayPane = new StackPane();
        displayPane.setBackground(new Background(new BackgroundFill(Color.rgb(24, 24, 24),
                CornerRadii.EMPTY, Insets.EMPTY)));
        displayPane.setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("/49665-200.png")
                .toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(500, 500, false, false,
                false, false))));
        root.setCenter(displayPane);

        // Setup main scene contents
        Scene scene = setupScene(root);
        setupTitleBar(root, stage);
        setupDragBoard(scene, root, displayPane);

        // Set up the stage
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image(getClass().getResource("/play.png").toExternalForm()));
        stage.setMaximized(true);
        stage.setTitle("MultiMedia Player");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Scene setupScene(BorderPane root) {
        Scene scene = new Scene(root, 500, 500);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        return scene;
    }

    private void setupTitleBar(BorderPane root, Stage stage) {
        // Created custom TitleBar (exit, minimize, and title)
        HBox buttonHBox = new HBox();

        buttonHBox.setMaxHeight(30);
        buttonHBox.setBackground(new Background(new BackgroundFill(Color.rgb(19, 19, 19),
                CornerRadii.EMPTY, Insets.EMPTY)));

        // Image view of application image that's displayed next to the title
        ImageView imageView = new ImageView(new Image(getClass().getResource("/play.png").toExternalForm()));
        imageView.setFitWidth(35);
        imageView.setFitHeight(35);
        imageView.setTranslateX(765);
        imageView.setTranslateY(-1);

        // The title of the program displayed in the title bar
        Label windowTitle = new Label("Media Player");
        windowTitle.setFont(Font.font("SERIOUS", FontWeight.BOLD, 25));
        windowTitle.setTextFill(Color.PALEVIOLETRED);
        windowTitle.setTranslateX(770);
        windowTitle.setTranslateY(-2);

        // Image to represent the exit (x) icon, so it looks cooler
        Image exitButtonImage = new Image(getClass().getResource("/remove.png").toExternalForm());
        ImageView exitButtonImageView = new ImageView(exitButtonImage);
        exitButtonImageView.setFitHeight(25);
        exitButtonImageView.setFitWidth(25);
        exitButtonImageView.setTranslateX(6);
        exitButtonImageView.setTranslateY(6);
        exitButtonImageView.setOnMouseClicked(event -> Platform.exit());

        // Spacer between the exit and minimize button
        Region spacer = new Region();
        spacer.setPrefWidth(16);

        // Image to represent the minimize (-) icon, so it looks cooler
        Image minimizeButtonImage = new Image(getClass().getResource("/minus.png").toExternalForm());
        ImageView minimizeButtonImageView = new ImageView(minimizeButtonImage);
        minimizeButtonImageView.setFitHeight(25);
        minimizeButtonImageView.setFitWidth(25);
        minimizeButtonImageView.setTranslateY(6);
        minimizeButtonImageView.setOnMouseClicked(event -> stage.setIconified(true));

        buttonHBox.getChildren().addAll(exitButtonImageView, spacer, minimizeButtonImageView, imageView, windowTitle);

        root.setTop(buttonHBox);
    }

    private void setupDragBoard(Scene scene, BorderPane root, StackPane displayPane) {
        // Event that detects when a file has been dragged over a window, in our case, we accept either
        // copied or moved files
        scene.setOnDragOver(event -> {
            if (!event.getDragboard().hasFiles()) return;
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        });

        // Event that detects when the user drops (lets go of the mouse button)
        // the dragged files (from the drag over event)
        scene.setOnDragDropped(event -> {
            if (!event.getDragboard().hasFiles()) return;

            // Get all dropped files and register them in the media file manager
            event.getDragboard()
                    .getFiles()
                    .forEach(mediaFileManager::createAndAddMediaFile);

            // A list view to display all the available files
            ListView<MediaFile> mediaFileListView = new ListView<>();

            // This allows us to change how the files are displayed in the list view
            mediaFileListView.setCellFactory(cell -> new ListCell<>() {
                @Override
                protected void updateItem(MediaFile item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) return;

                    if (item instanceof PlayableMediaFile playableMediaFile) {
                        setText(item.getTitle() + " | " + item.getType() + " | " + playableMediaFile.
                                getDuration().toSeconds());
                        return;
                    }
                    setText(item.getTitle() + " | " + item.getType() + " | " + item.getAuthor());
                    setFont(Font.font(16));
                }
            });

            // Modify the listview shape to modify it's size as well
            mediaFileListView.setShape(new Rectangle(100, 100));

            // Add all registered media files to the list view
            mediaFileListView.getItems().addAll(mediaFileManager.getMediaFiles());

            // When the list view is clicked, we display the users selection
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
    }

}