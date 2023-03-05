package me.ali.mediaplayer.animations.impl;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import me.ali.mediaplayer.animations.domain.Animation;

public class FadeInAnimation extends Animation {

    public FadeInAnimation(ImageView imageView) {
        super(fadeTransition -> {
            fadeTransition.setToValue(0);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0), new KeyValue(imageView.opacityProperty(), 0)),
                    new KeyFrame(Duration.seconds(1), new KeyValue(imageView.opacityProperty(), 1)));
            timeline.play();

            return fadeTransition;
        }, imageView, 0.5);
    }

}
