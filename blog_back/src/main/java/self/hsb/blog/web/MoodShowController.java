package self.hsb.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import self.hsb.blog.po.Mood;
import self.hsb.blog.response.Response;
import self.hsb.blog.response.ResponseFactory;
import self.hsb.blog.service.MoodService;

/**
 * @author SipooHe
 * @date 2020/4/28 17:15
 */
@Controller
@RequestMapping("/hsblog")
public class MoodShowController {

    @Autowired
    private MoodService moodService;

    @CrossOrigin
    @GetMapping("/mood")
    @ResponseBody
    public Response getMoods(@PageableDefault(size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Mood> moodPage = moodService.listMood(pageable);
        return ResponseFactory.buildSuccessResponse(moodPage);
    }

    @CrossOrigin
    @GetMapping("/mood/count")
    @ResponseBody
    public Response countMood() {
        Long count = moodService.countMood();
        return ResponseFactory.buildSuccessResponse(count);
    }

}
