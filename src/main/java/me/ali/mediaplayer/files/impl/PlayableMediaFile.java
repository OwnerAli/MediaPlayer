package me.ali.mediaplayer.files.impl;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import me.ali.mediaplayer.animations.impl.FadeInAnimation;
import me.ali.mediaplayer.animations.impl.FadeOutAnimation;
import me.ali.mediaplayer.files.domain.MediaFile;

import java.io.File;

public class PlayableMediaFile extends MediaFile {

    private final int duration;
    private boolean playing;

    private final MediaPlayer mediaPlayer;
    private final ImageView pauseButtonImage;

    public PlayableMediaFile(File file, String title, String author, int duration) {
        super(file, title, author);
        this.duration = duration;
        this.playing = false;

        this.mediaPlayer =  new MediaPlayer(new Media(getFile().toURI().toString()));
        this.pauseButtonImage = new ImageView("C:\\Users\\altur\\Documents\\school\\CS\\mediaplayer\\src" +
                "\\main\\resources\\video-pause-button.png");
        pauseButtonImage.visibleProperty().bind(new SimpleBooleanProperty(!playing));
    }

    public void toggleMedia(Pane pane) {
        if (playing) {
            pauseMedia(pane);
            return;
        }
        playMedia();
    }

    private void playMedia() {
        mediaPlayer.play();
        playing = true;
        new FadeOutAnimation(pauseButtonImage);
    }

    private void pauseMedia(Pane pane) {
        mediaPlayer.pause();
        playing = false;
        new FadeInAnimation(pauseButtonImage);
        if (!pane.getChildren().contains(pauseButtonImage)) {
            pane.getChildren().add(pauseButtonImage);
        }
    }

    @Override
    public void display(Pane pane) {
        MediaView mediaView = new MediaView(mediaPlayer);
        pane.getChildren().add(mediaView);
        mediaPlayer.setOnEndOfMedia(() -> pane.getChildren().add(new Label("DONE!")));
    }

}
