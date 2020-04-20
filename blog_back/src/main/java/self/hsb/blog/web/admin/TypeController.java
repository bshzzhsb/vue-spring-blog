package self.hsb.blog.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import self.hsb.blog.po.Type;
import self.hsb.blog.response.Response;
import self.hsb.blog.response.ResponseFactory;
import self.hsb.blog.service.TypeService;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/17 22:36
 */
@Controller
@RequestMapping("/hsblog/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @CrossOrigin
    @GetMapping("/type")
    @ResponseBody
    public Response getTypes() {
        List<Type> types = typeService.listType();
        return ResponseFactory.buildSuccessResponse(types);
    }

    @CrossOrigin
    @PostMapping("/type")
    @ResponseBody
    public Response saveType(@RequestBody Type type) {
        Type t;
        if (type.getId() == null) {
            t = typeService.saveType(type);
        } else {
            t = typeService.updateType(type);
        }
        if (t == null) {
            return ResponseFactory.buildFailResponse("保存失败");
        } else {
            return ResponseFactory.buildSuccessResponse(null);
        }
    }

    @CrossOrigin
    @PostMapping("/type/delete/{id}")
    @ResponseBody
    public Response deleteType(@PathVariable Integer id) {
        typeService.deleteType(id);
        return ResponseFactory.buildSuccessResponse(null);
    }

}
