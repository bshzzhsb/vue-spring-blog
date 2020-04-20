package self.hsb.blog.response;

/**
 * @author SipooHe
 * @date 2020/4/12 16:38
 */
public class Response {

    private int status;
    private String message;
    private Object object;

    public Response(int status, String message, Object object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
