package self.hsb.blog.web.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import self.hsb.blog.response.Response;
import self.hsb.blog.response.ResponseFactory;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author SipooHe
 * @date 2020/4/18 15:28
 */
@Controller
public class CommonController {

    @Value("${image.path}")
    private String filePath;

    @Value("${file.url}")
    private String fileUrl;

    @CrossOrigin
    @PostMapping("/hsblog/admin/upload")
    @ResponseBody
    public Response imgUpload(MultipartFile file) {
        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            String suffixName = filename.substring(filename.lastIndexOf("."));
            filename = UUID.randomUUID() + suffixName;
            File dest = new File(filePath + filename);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String filename1 = fileUrl + dest.getName();
            return ResponseFactory.buildSuccessResponse(filename1);
        } else {
            return ResponseFactory.buildFailResponse("上传失败");
        }
    }
}
