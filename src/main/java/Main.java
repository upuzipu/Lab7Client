import client.Client;
import client.Environment;
import cmd.*;
import ioManager.ConsoleManager;
import ioManager.IReadable;
import ioManager.IWritable;

import java.util.HashMap;

public class Main {

    public static void main(String[] args){
        IReadable in = ConsoleManager.getInstance();
        IWritable out = ConsoleManager.getInstance();
        HashMap<String, ICommand> commandMap = new HashMap<String, ICommand>();
        ExitCommand.register(commandMap);
        HelpCommand.register(commandMap);
        ExecuteScriptCommand.register(commandMap);
        ConnectCommand.register(commandMap);
        DisconnectCommand.register(commandMap);

        Environment env = new Environment(commandMap,in, out, false, 60121);

        Client client = new Client(env);
        client.init();

    }
}
