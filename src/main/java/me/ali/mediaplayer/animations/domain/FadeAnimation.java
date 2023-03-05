package me.ali.mediaplayer.animations.domain;

import javafx.animation.FadeTransition;

@FunctionalInterface
public interface FadeAnimation {

    FadeTransition createFadeAnimation(FadeTransition fadeTransition);

}
