package de.warhog.fpvlaptracker.util;

import de.warhog.fpvlaptracker.configuration.ApplicationConfig;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public enum AudioFile {
    REGISTER("register.wav"),
    LAP("lap.wav"),
    UNREGISTER("unregister.wav"),
    FINISHED("finished.wav"),
    INVALID_LAP("invalidlap.wav"),
    PARTICIPANT_ENDED("participantended.wav");

    private static final Logger LOG = LoggerFactory.getLogger(AudioFile.class);
    private static String audioLanguage = "en";

    public static void setAudioLanguage(String language) {
        audioLanguage = language;
    }

    @Component
    public static class AudioLanguageInjector {

        @Autowired
        private ApplicationConfig applicationConfig;

        @PostConstruct
        public void postConstruct() {
            AudioFile.setAudioLanguage(applicationConfig.getAudioLanguage());
        }

    }

    private final String filename;

    AudioFile(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return "audio/" + audioLanguage + "/" + filename;
    }

    public Path getPath() {
        return Paths.get(getFilename());
    }

    public File getFile() {
        return getPath().toFile();
    }

    public void testIfExisting() {
        if (!Files.exists(getPath())) {
            throw new RuntimeException("audio file " + getPath() + " not found");
        }
    }

}
