package me.ali.mediaplayer.files.domain;

import javafx.scene.layout.Pane;

import java.io.File;

public abstract class MediaFile {

    private final File file;
    private final String title;
    private final String author;

    public MediaFile(File file, String title, String author) {
        this.file = file;
        this.title = title;
        this.author = author;
    }

    public abstract void display(Pane pane);

    public abstract String getType();

    public File getFile() {
        return file;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

}