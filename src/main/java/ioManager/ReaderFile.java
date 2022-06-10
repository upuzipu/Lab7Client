package ioManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ReaderFile implements IReadable{
    private final List<String> lines;
    private int it;

    public ReaderFile(String path) throws IOException {
        lines = Files.readAllLines(Paths.get(path));
        it = 0;
    }

    @Override
    public String readline() {
        if (it==lines.size())
            return null;
        return lines.get(it++);
    }
}
