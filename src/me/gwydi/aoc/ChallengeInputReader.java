package me.gwydi.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChallengeInputReader {
    private static final String PREFIX = "src/me/gwydi/aoc/";
    private final String path;
    public ChallengeInputReader(String path) {
        this.path = PREFIX + path;
    }
    public String readAsString() {
        String result = "";
        try {
            result = Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Cannot read file");
            System.exit(1);
        }
        return result;
    }
}
