package cmd;

import client.Environment;
import connection.CommandPackage;
import connection.UDP;
import connection.NetPackage;
import connection.NetResponse;
import exceptions.MaxSizeBufferException;
import ioManager.RequestElement;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

public class ConnectCommand implements ICommand {

    @Override
    public boolean isLocal(){
        return true;
    }

    @Override
    public String getName() {
        return "connect";
    }

    @Override
    public String getDescription() {

        return "connect ip:port - Установить подключение к серверу";
    }

    @Override
    public void execute(Environment env, String arg, NetPackage netPackage) {
        String[] address = arg.split(":");
        if (address.length!=2) {
            env.getOut().writeln("Неверно задан адрес сервера");
            return;
        }
        String hostStr = address[0];
        String portStr = address[1];
        InetAddress host;
        int port;
        try{
            host = InetAddress.getByName(hostStr);
            port = Integer.parseInt(portStr);
            if (port<1 || port>65535)
                throw new NumberFormatException();
        }
        catch (NumberFormatException ex){
            env.getOut().writeln("Порт задан некорректно");
            return;
        }
        catch (UnknownHostException ex){
            env.getOut().writeln("Адрес задан некорректно");
            return;
        }
        env.setServerAddress(host);
        env.setServerPort(port);
        env.setUser(LoginCommand.login());
        UDP communication;
        try {
            communication = new UDP(env.getClientPort(),env.getServerPort(),env.getServerAddress());
        } catch (SocketException e) {
            env.getOut().writeln("Не удалось создать сокет для отправки пакета");
            return;
        }
        NetPackage np = new NetPackage();
        np.setCmd(getName());
        np.setUser(env.getUser());
        try {
            communication.send(np);
        } catch (MaxSizeBufferException e) {
            env.getOut().writeln("Размер пакета превышает допустимый лимит");
            communication.close();
            return;
        } catch (IOException e) {
            env.getOut().writeln("Ошибка во время отправки пакета");
            communication.close();
            return;
        }

        try {
            NetResponse netResponse = communication.receive();
            env.getOut().writeln(netResponse.getMessage());
            if (netResponse.isFinish()) {
                communication.close();
                return;
            }
            while (true) {
                CommandPackage cmd = communication.receive();

                ICommand a = new ICommand() {
                    @Override
                    public boolean isLocal(){
                        return false;
                    }

                    @Override
                    public String getName() {
                        return cmd.getName();
                    }

                    @Override
                    public String getDescription() {
                        return cmd.getDescription();
                    }

                    @Override
                    public void execute(Environment env, String arg, NetPackage netPackage) {
                        netPackage.setCmd(cmd.getName());
                        if (cmd.isHasArg())
                            netPackage.setArg(arg);
                        if (cmd.isHasObject())
                            netPackage.setCar(new RequestElement(env.getIn(), env.getOut(), true).readElement(netPackage.getUser()));
                    }
                };
                env.getCommandMap().put(a.getName(), a);
                if (cmd.isFinish())
                    break;
            }
        }
        catch (SocketTimeoutException ex){
            env.getOut().writeln("Время ожидания ответа от сервера вышло\nПолучение списка команд остановлено");
            return;
        }
        catch (IOException ex){
            env.getOut().writeln("Ошибка во время получения пакета");
            return;
        }
        catch (IllegalArgumentException ex){
            env.getOut().writeln("Полученный пакет поврежден");
            return;
        }
        finally {
            communication.close();
        }
        env.getOut().writeln("Вы подключились к серверу "+ arg);
    }
    public static void register(HashMap<String, ICommand> commandMap) {
        ICommand cmd = new ConnectCommand();
        commandMap.put(cmd.getName(), cmd);
    }
}
