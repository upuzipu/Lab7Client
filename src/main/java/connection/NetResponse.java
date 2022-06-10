package connection;

import java.io.Serializable;

public class NetResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private boolean finish;

    public NetResponse(String message,boolean finish){
        this.message = message;
        this.finish = finish;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public String getMessage() {
        return message;
    }

    public boolean isFinish() {
        return finish;
    }
}

