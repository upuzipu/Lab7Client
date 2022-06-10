package connection;

import collection.Car;
import collection.UserToken;

import java.io.Serializable;
import java.net.SocketAddress;

public class NetPackage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cmd = "";
    private String arg = "";
    private Car car = null;
    private UserToken user = null;
    SocketAddress remote_address = null;
    public String getCmd() {
        return cmd;
    }

    public String getArg() {
        return arg;
    }

    public Car getCar() {
        return car;
    }

    public UserToken getUser() {
        return user;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public void setUser(UserToken user) {
        this.user = user;
    }

    public void setRemote_address(SocketAddress remote_address) {
        this.remote_address = remote_address;
    }

    public SocketAddress getRemote_address() {
        return remote_address;
    }
}
