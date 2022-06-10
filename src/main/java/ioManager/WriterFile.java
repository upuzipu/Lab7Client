package ioManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WriterFile implements IWritable{
    private final File file;
    static Logger LOGGER = Logger.getLogger(WriterFile.class.getName());

    public WriterFile(String path) throws IOException {
        file = new File(path);
        file.createNewFile();
    }


    @Override
    public void write(String text){
        try (FileOutputStream out = new FileOutputStream(file);
        BufferedOutputStream fw = new BufferedOutputStream(out)) {
            byte[] t = text.getBytes();
            fw.write(t);
        }
        catch (IOException ex){
            LOGGER.log(Level.WARNING,"Ошибка записи в файл");
        }

    }

    @Override
    public void writeln(String text){
        write(text+"\n");
    }
}
