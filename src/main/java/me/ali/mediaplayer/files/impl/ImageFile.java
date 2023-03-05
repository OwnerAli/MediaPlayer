package me.ali.mediaplayer.files.impl;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import me.ali.mediaplayer.files.domain.MediaFile;

import java.io.File;

public class ImageFile extends MediaFile {

    private final String altText;

    public ImageFile(File file, String title, String author, String altText) {
        super(file, title, author);
        this.altText = altText;
    }

    @Override
    public void display(Pane pane) {
        Image image = new Image(getFile().toURI().toString());
        pane.getChildren().add(new ImageView(image));
    }

}
