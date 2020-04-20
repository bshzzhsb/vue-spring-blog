package self.hsb.blog.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import self.hsb.blog.response.Response;
import self.hsb.blog.response.ResponseFactory;
import self.hsb.blog.util.GetGitHubTrending;
import self.hsb.blog.vo.GitHubTrending;

import java.io.IOException;
import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/15 15:45
 */
@Controller
@RequestMapping("/hsblog/admin")
public class HomeController {

    @CrossOrigin
    @GetMapping("/home/githubtrending")
    @ResponseBody
    public Response githubTrending() {
        try {
            List<GitHubTrending> trendings = GetGitHubTrending.getTrending();
            return ResponseFactory.buildSuccessResponse(trendings);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseFactory.buildFailResponse("fail to get");
        }
    }
}
