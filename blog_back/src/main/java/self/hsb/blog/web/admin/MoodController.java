package self.hsb.blog.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import self.hsb.blog.po.Mood;
import self.hsb.blog.response.Response;
import self.hsb.blog.response.ResponseFactory;
import self.hsb.blog.service.MoodService;
import self.hsb.blog.service.UserService;

/**
 * @author SipooHe
 * @date 2020/4/22 17:16
 */
@Controller
@RequestMapping("/hsblog/admin")
public class MoodController {

    @Autowired
    private MoodService moodService;
    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping("/mood")
    @ResponseBody
    public Response getMoods(@PageableDefault(size = 6, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Mood> moodPage = moodService.listMood(pageable);
        return ResponseFactory.buildSuccessResponse(moodPage);
    }

    @CrossOrigin
    @GetMapping("/mood/{id}")
    @ResponseBody
    public Response getMood(@PathVariable Integer id) {
        Mood mood = moodService.getMoodById(id);
        return ResponseFactory.buildSuccessResponse(mood);
    }

    @CrossOrigin
    @PostMapping("/mood")
    @ResponseBody
    public Response saveMood(@RequestBody Mood mood) {
        mood.setUser(userService.getUserByUsername("hsblock"));
        Mood m;
        if (mood.getId() == null) {
            m = moodService.saveMood(mood);
        } else {
            m = moodService.updateMood(mood);
        }
        if (m == null) {
            return ResponseFactory.buildFailResponse("保存失败");
        } else {
            return ResponseFactory.buildSuccessResponse(null);
        }
    }

    @CrossOrigin
    @PostMapping("/mood/delete/{id}")
    @ResponseBody
    public Response deleteMood(@PathVariable Integer id) {
        moodService.deleteMood(id);
        return ResponseFactory.buildSuccessResponse(null);
    }

}
