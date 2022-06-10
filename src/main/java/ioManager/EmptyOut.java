package ioManager;

public class EmptyOut implements IWritable{

    public EmptyOut(){}
    @Override
    public void write(String s) {
    }

    @Override
    public void writeln(String s) {
    }
}
