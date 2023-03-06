package me.ali.mediaplayer.files.impl;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import me.ali.mediaplayer.animations.impl.FadeInAnimation;
import me.ali.mediaplayer.animations.impl.FadeOutAnimation;
import me.ali.mediaplayer.files.domain.MediaFile;

import java.io.File;

public class PlayableMediaFile extends MediaFile {

    private final Duration duration;
    private boolean playing;

    private final MediaPlayer mediaPlayer;
    private final ImageView pauseButtonImage;

    public PlayableMediaFile(File file, String title, String author) {
        super(file, title, author);
        this.playing = false;

        Media media = new Media(getFile().toURI().toString());
        this.mediaPlayer =  new MediaPlayer(media);
        this.pauseButtonImage = new ImageView("C:\\Users\\altur\\Documents\\school\\CS\\mediaplayer\\src" +
                "\\main\\resources\\video-pause-button.png");
        pauseButtonImage.visibleProperty().bind(new SimpleBooleanProperty(!playing));
        this.duration = media.getDuration();
    }

    public Duration getDuration() {
        return duration;
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
        pane.getChildren().clear();
        pane.getChildren().add(mediaView);
        mediaView.setFitHeight(pane.getHeight());
        mediaView.setFitWidth(pane.getWidth());
    }

    @Override
    public String getType() {
        return "Video/Audio";
    }

}
