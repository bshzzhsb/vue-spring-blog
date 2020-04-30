package self.hsb.blog.web.admin;

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
 * @date 2020/4/25 12:50
 */
@Controller
@RequestMapping("/hsblog/admin")
public class MessageController {

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
    @GetMapping("/message/page")
    @ResponseBody
    public Response pageMessage(@PageableDefault(size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Integer> pageInfo = messageService.getPageInfo(pageable);
        return ResponseFactory.buildSuccessResponse(pageInfo);
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
    @PostMapping("/message/{id}")
    @ResponseBody
    public Response checkMessage(@PathVariable Integer id) {
        Message message = messageService.checkMessage(id);
        if (message == null) {
            return ResponseFactory.buildFailResponse("保存失败");
        } else {
            return ResponseFactory.buildSuccessResponse(null);
        }
    }

    @CrossOrigin
    @GetMapping("/message/delete/{id}")
    @ResponseBody
    public Response deleteMessage(@PathVariable Integer id) {
        messageService.deleteMessage(id);
        return ResponseFactory.buildSuccessResponse(null);
    }

}
