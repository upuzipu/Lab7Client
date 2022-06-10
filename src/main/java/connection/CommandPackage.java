package connection;

import java.io.Serializable;

public class CommandPackage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private boolean hasArg;
    private boolean hasObject;
    private boolean finish;

    public CommandPackage(String name, String description, boolean hasArg, boolean hasObject, boolean finish){
        this.name = name;
        this.description = description;
        this.hasArg = hasArg;
        this.hasObject = hasObject;
        this.finish = finish;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isHasArg() {
        return hasArg;
    }

    public boolean isHasObject() {
        return hasObject;
    }

    public boolean isFinish() {
        return finish;
    }
}
