package me.ali.mediaplayer.animations.domain;

import javafx.animation.FadeTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public abstract class Animation {

    protected Animation(FadeAnimation fadeAnimation, ImageView imageView, double animationDurationInSeconds) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(animationDurationInSeconds), imageView);
        fadeAnimation.createFadeAnimation(fadeTransition);
    }
    
}
