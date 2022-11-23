package dev.mateusneres.game.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    public static final String FILE_PATH = "2048.json";

    public static File getDataFile() {
        return new File(String.valueOf(Paths.get(FILE_PATH)));
    }

    public static String readFileAsString() throws Exception {
        return new String(Files.readAllBytes(Paths.get(FILE_PATH)));
    }

    public static boolean isEmpty() {
        return new File(String.valueOf(Paths.get(FILE_PATH))).length() == 0;
    }

    public static boolean existsDataFile() {
        return new File(String.valueOf(Paths.get(FILE_PATH))).exists();
    }

}
