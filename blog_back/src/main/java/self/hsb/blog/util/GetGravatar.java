package self.hsb.blog.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author SipooHe
 * @date 2020/4/28 22:17
 */
public class GetGravatar {

    public static String getGravatar(String email) {
        String emailMd5 = emailToMd5(email);
        return "https://gravatar.com/avatar/"+emailMd5+"?s=64";
    }

    public static String emailToMd5(String email) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] encodeMd5Digest = md.digest(email.getBytes());
            return convertByteToHexString(encodeMd5Digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertByteToHexString(byte[] bytes) {
        StringBuffer buf = new StringBuffer("");
        for (int offset=0; offset < bytes.length; offset++) {
            int i = bytes[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        String s = getGravatar("sibo_he@qq.com");
        System.out.println(s);
    }

}
