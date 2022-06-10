package cmd;

import client.Client;
import client.Environment;
import connection.NetPackage;
import ioManager.EmptyOut;
import ioManager.IReadable;
import ioManager.ReaderFile;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;

public class ExecuteScriptCommand implements ICommand {

    @Override
    public boolean isLocal(){
        return true;
    }
    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {

        return "execute_script - Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    @Override
    public void execute(Environment env, String arg, NetPackage netPackage) {
        if (!arg.isEmpty()){
            IReadable readerFile;
            try{
                readerFile = new ReaderFile(arg);
            }
            catch (IOException ex){
                System.err.println("Файл скрипта не может быть прочитан");
                return;
            }
           Environment envScript = new Environment(env.getCommandMap(),
                   readerFile,
                   env.getOut(),
                   true,
                   env.getClientPort());
            envScript.setServerAddress(env.getServerAddress());
            envScript.setServerPort(env.getServerPort());
            envScript.getCommandMap().remove(getName());
            Client clientScript = new Client(envScript);
            try{clientScript.init();}
            catch(Exception ex){
                env.getOut().writeln("Ошибка во время выполнения скрипта");
            }
            env.getOut().writeln("Чтение скрипта завершено");
            }
        else {
            System.err.println("Аргумент должен содержать путь к файлу");
        }

    }

    public static void register(HashMap<String, ICommand> commandMap) {
        ICommand cmd = new ExecuteScriptCommand();
        commandMap.put(cmd.getName(), cmd);
    }
}
