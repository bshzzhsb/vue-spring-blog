package self.hsb.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import self.hsb.blog.po.Message;
import self.hsb.blog.response.Response;
import self.hsb.blog.response.ResponseFactory;
import self.hsb.blog.service.MessageService;
import self.hsb.blog.util.GetGravatar;

import java.util.Map;

/**
 * @author SipooHe
 * @date 2020/4/28 16:40
 */
@Controller
@RequestMapping("/hsblog")
public class MessageShowController {

    @Autowired
    private MessageService messageService;

    @CrossOrigin
    @GetMapping("/message")
    @ResponseBody
    public Response getMessages(@PageableDefault(size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Object> map = messageService.listMessage(pageable);
        return ResponseFactory.buildSuccessResponse(map);
    }

    @CrossOrigin
    @PostMapping("/message")
    @ResponseBody
    public Response saveMessage(@RequestBody Message message) {
        message.setAvatar(GetGravatar.getGravatar(message.getEmail()));
        Message m = messageService.saveMessage(message);
        if (m == null) {
            return ResponseFactory.buildFailResponse("保存失败");
        } else {
            return ResponseFactory.buildSuccessResponse(null);
        }
    }

    @CrossOrigin
    @GetMapping("/message/count")
    @ResponseBody
    public Response countMessage() {
        Long count = messageService.countMessage();
        return ResponseFactory.buildSuccessResponse(count);
    }

}
