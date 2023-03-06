package me.ali.mediaplayer.managers;

import me.ali.mediaplayer.files.domain.MediaFile;
import me.ali.mediaplayer.files.impl.ImageFile;
import me.ali.mediaplayer.files.impl.PlayableMediaFile;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class MediaFileManager {

    private final Set<MediaFile> mediaFileSet = new HashSet<>();

    public Set<MediaFile> getMediaFiles() {
        return mediaFileSet;
    }

    public void createAndAddMediaFile(File file) {
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        switch (fileExtension.toLowerCase()) {
            case "mp3", "wav", "wma" -> mediaFileSet.add(new PlayableMediaFile(file, fileName, "Uknown Artist"));
            case "mp4", "avi", "mov" -> mediaFileSet.add(new PlayableMediaFile(file, fileName, "Unknown Director"));
            case "jpg", "jpeg", "png" -> mediaFileSet.add(new ImageFile(file, fileName, "Unknown Photographer", "n/a"));
            default -> throw new IllegalArgumentException("Unsupported file type " + fileName + "." + fileExtension);
        }
    }

}
