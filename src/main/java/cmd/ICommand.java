package cmd;

import client.Environment;
import connection.NetPackage;

public interface ICommand {
    String getName();
    String getDescription();
    boolean isLocal();
    void execute(Environment env, String arg, NetPackage netPackage);
}
