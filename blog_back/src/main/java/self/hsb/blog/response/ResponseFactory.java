package self.hsb.blog.response;

/**
 * @author SipooHe
 * @date 2020/4/12 16:39
 */
public class ResponseFactory {

    public static Response buildSuccessResponse(Object object) {
        return buildResponse(ResponseStatus.SUCCESS, "success", object);
    }

    public static Response buildFailResponse(String message) {
        return buildResponse(ResponseStatus.FAIL, message, null);
    }

    private static Response buildResponse(ResponseStatus responseStatus, String message, Object object) {
        return buildResponse(responseStatus.status, message, object);
    }

    public static Response buildResponse(int responseStatus, String message, Object object) {
        return new Response(responseStatus, message, object);
    }
}
