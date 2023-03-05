package me.ali.mediaplayer.managers;

import me.ali.mediaplayer.files.domain.MediaFile;
import me.ali.mediaplayer.files.impl.ImageFile;
import me.ali.mediaplayer.files.impl.PlayableMediaFile;

import java.io.File;
import java.io.IOException;

public class MediaFileManager {

    public MediaFile createMultimediaFile(File file) {
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        switch (fileExtension.toLowerCase()) {
            case "mp3":
            case "wav":
            case "wma":
                return new PlayableMediaFile(file, fileName, "Unknown Artist", 0);
            case "mp4":
            case "avi":
            case "mov":
                return new PlayableMediaFile(file, fileName, "Unknown Director", 0);
            case "jpg":
            case "jpeg":
            case "png":
                return new ImageFile(file, fileName, "Unknown Photographer", "n/a");
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileExtension);
        }
    }

}
