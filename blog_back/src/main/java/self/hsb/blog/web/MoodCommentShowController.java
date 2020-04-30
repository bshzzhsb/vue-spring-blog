package self.hsb.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import self.hsb.blog.po.MoodComment;
import self.hsb.blog.response.Response;
import self.hsb.blog.response.ResponseFactory;
import self.hsb.blog.service.MoodCommentService;
import self.hsb.blog.service.MoodService;
import self.hsb.blog.util.GetGravatar;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/23 20:19
 */
@Controller
@RequestMapping("/hsblog")
public class MoodCommentShowController {

    @Autowired
    private MoodCommentService moodCommentService;
    @Autowired
    private MoodService moodService;

    @CrossOrigin
    @GetMapping("/mood/{moodId}/comments")
    @ResponseBody
    public Response getMoodComments(@PathVariable Integer moodId) {
        List<MoodComment> moodComments = moodCommentService.listMoodCommentByMoodId(moodId);
        return ResponseFactory.buildSuccessResponse(moodComments);
    }

    @CrossOrigin
    @PostMapping("/mood/comments")
    @ResponseBody
    public Response saveMoodComment(@RequestBody MoodComment moodComment) {
        moodComment.setAvatar(GetGravatar.getGravatar(moodComment.getEmail()));
        Integer moodId = moodComment.getMood().getId();
        moodComment.setMood(moodService.getMoodById(moodId));
        moodCommentService.saveMoodComment(moodComment);
        return ResponseFactory.buildSuccessResponse(null);
    }

}
