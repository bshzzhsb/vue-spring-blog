package self.hsb.blog.response;

/**
 * @author SipooHe
 * @date 2020/4/12 16:39
 */
public enum  ResponseStatus {

    SUCCESS(200),
    FAIL(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    public int status;

    ResponseStatus(int status) {
        this.status = status;
    }
}
