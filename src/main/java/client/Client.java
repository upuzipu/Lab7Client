package client;

import connection.UDP;
import connection.NetPackage;
import connection.NetResponse;
import exceptions.MaxSizeBufferException;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class Client {
    private Environment env;
    public Client(Environment env) {
        this.env = env;
    }

    public void init() {
        while (env.isRunning()) {
            String s = env.getIn().readline();
            if (s == null)
                if (env.isScript())
                    break;
                else
                    continue;

            String cmd = "";
            String arg = "";

            String[] sArr = s.split("\\s");
            if (sArr.length == 1)
                cmd = sArr[0];
            if (sArr.length > 1) {
                cmd = sArr[0];
                arg = sArr[1];
            }

            NetPackage netPackage = new NetPackage();
            netPackage.setUser(env.getUser());
            if (env.getCommandMap().containsKey(cmd)) {
                env.getCommandMap().get(cmd).execute(env, arg, netPackage);
                if (!env.getCommandMap().get(cmd).isLocal()){
                    UDP communication = null;
                    try {
                        communication = new UDP(env.getClientPort(),env.getServerPort(), env.getServerAddress());
                        communication.send(netPackage);
                    } catch (MaxSizeBufferException e) {
                        System.err.println("Размер пакета слишком велик\nПакет не был отправлен");
                        communication.close();
                        continue;
                    } catch (IOException e) {
                        System.err.println("Ошибка во время отправки");
                        if (communication != null)
                            communication.close();
                        continue;
                    }
                    try {
                        while (true) {
                            NetResponse response = communication.receive();
                            if (response == null) {
                                System.err.println("Ошибка во время получения пакета");
                                break;
                            }
                            env.getOut().write(response.getMessage());
                            if (response.isFinish())
                                break;
                        }
                    } catch (SocketTimeoutException e) {
                        System.err.println("Время ожидания ответа от сервера вышло");
                        break;
                    } catch (IllegalArgumentException ex) {
                        System.err.println("Пакет поврежден");
                    } catch (IOException ex) {
                        System.err.println("Ошибка во время получения пакета");
                    } finally {
                        communication.close();
                    }
                }
            }
            else {
                env.getOut().writeln("Command not found (type \"help\" to get information about available commands)");
            }
        }
    }
}

