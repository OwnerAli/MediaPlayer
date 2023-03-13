package me.ali.mediaplayer.animations.domain;

import javafx.animation.FadeTransition;

@FunctionalInterface
public interface FadeAnimation {

    void createFadeAnimation(FadeTransition fadeTransition);

}
