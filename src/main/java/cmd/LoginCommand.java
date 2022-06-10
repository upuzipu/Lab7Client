package cmd;

import collection.UserToken;
import ioManager.ConsoleManager;

public class LoginCommand {
    public static UserToken login(){
        UserToken user = null;
        while (true) {
            System.out.print("Enter login:");
            String login = ConsoleManager.getInstance().readline();
            System.out.print("Enter password:");
            String password = ConsoleManager.getInstance().readline();
            user = new UserToken(login, password);
            break;
        }
        return user;
    }
}
