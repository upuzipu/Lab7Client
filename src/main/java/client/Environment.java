package client;

import cmd.ICommand;
import collection.UserToken;
import ioManager.IReadable;
import ioManager.IWritable;

import java.net.InetAddress;
import java.util.HashMap;

public class Environment {
    private HashMap<String, ICommand> commandMap;
    private IReadable in;
    private IWritable out;
    private boolean running;
    private final boolean script;
    private int clientPort;
    private Integer serverPort;
    private InetAddress serverAddress;
    private UserToken user;

    public Environment(HashMap<String, ICommand> commandMap, IReadable in, IWritable out, boolean isScript, int clientPort){
        this.commandMap = commandMap;
        this.in = in;
        this.out = out;
        this.script = isScript;
        this.clientPort = clientPort;
        this.serverPort = null;
        this.serverAddress = null;
        running = true;
        user = null;
    }

    public HashMap<String, ICommand> getCommandMap() {
        return commandMap;
    }
    public IReadable getIn(){
        return in;
    }
    public IWritable getOut(){
        return out;
    }
    public boolean isRunning(){
        return running;
    }
    public void turnOff(){
        running = false;
    }
    public boolean isScript(){
        return script;
    }

    public int getClientPort() {
        return clientPort;
    }

    public int getServerPort() {
        return serverPort;
    }
    public void setServerPort(Integer serverPort) {this.serverPort = serverPort;}
    public InetAddress getServerAddress() {
        return serverAddress;
    }
    public void setServerAddress(InetAddress serverAddress) {
        this.serverAddress = serverAddress;
    }

    public UserToken getUser() {
        return user;
    }

    public void setUser(UserToken user) {
        this.user = user;
    }
}
