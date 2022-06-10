package cmd;

import client.Environment;
import connection.NetPackage;

import java.util.HashMap;

public class ExitCommand implements ICommand {

    @Override
    public boolean isLocal(){
        return true;
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {

        return "exit - Завершить работу клиента";
    }

    @Override
    public void execute(Environment env, String arg, NetPackage netPackage) {
        env.getOut().writeln("Finish of working! Thanks for using!");
        env.turnOff();
    }
    public static void register(HashMap<String, ICommand> commandMap) {
        ICommand cmd = new ExitCommand();
        commandMap.put(cmd.getName(), cmd);
    }
}
