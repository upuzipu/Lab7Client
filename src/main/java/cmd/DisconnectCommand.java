package cmd;

import client.Environment;
import connection.NetPackage;

import java.util.HashMap;

public class DisconnectCommand implements ICommand {

    @Override
    public boolean isLocal(){
        return true;
    }

    @Override
    public String getName() {
        return "disconnect";
    }

    @Override
    public String getDescription() {

        return "disconnect - Прекращает взаимодействие с сервером";
    }

    @Override
    public void execute(Environment env, String arg, NetPackage netPackage) {
        env.setServerAddress(null);
        env.setServerPort(null);
        env.setUser(null);
        env.getCommandMap().entrySet().removeIf((s)->!s.getValue().isLocal());
        env.getOut().writeln("Вы отключились от сервера");
    }
    public static void register(HashMap<String, ICommand> commandMap) {
        ICommand cmd = new DisconnectCommand();
        commandMap.put(cmd.getName(), cmd);
    }
}
