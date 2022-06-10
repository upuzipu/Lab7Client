package ioManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleManager implements IReadable,IWritable{
    private final BufferedReader cons;
    private static ConsoleManager instance;

    private ConsoleManager() {
        cons = new BufferedReader(new InputStreamReader(System.in));
    }

    public static ConsoleManager getInstance() {
        if (instance == null)
            instance = new ConsoleManager();
        return instance;
    }

    @Override
    public void write(String s) {
        System.out.print(s);
    }

    @Override
    public void writeln(String s) {
        System.out.println(s);
    }

    @Override
    public String readline() {
        try {
            return cons.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
