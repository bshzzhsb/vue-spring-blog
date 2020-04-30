package self.hsb.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import self.hsb.blog.po.Blog;
import self.hsb.blog.po.Type;
import self.hsb.blog.response.Response;
import self.hsb.blog.response.ResponseFactory;
import self.hsb.blog.service.BlogService;
import self.hsb.blog.service.TypeService;
import self.hsb.blog.vo.BlogQuery;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/28 13:22
 */
@Controller
@RequestMapping("/hsblog")
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @CrossOrigin
    @GetMapping("/type")
    @ResponseBody
    public Response getTypes() {
        List<Type> types = typeService.listTypeTop();
        return ResponseFactory.buildSuccessResponse(types);
    }

    @CrossOrigin
    @GetMapping("/type/{typeId}")
    @ResponseBody
    public Response getTypeBlog(@PageableDefault(size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                                @PathVariable Integer typeId) {
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(typeId);
        Page<Blog> blogPage = blogService.listBlog(blogQuery, pageable);
        return ResponseFactory.buildSuccessResponse(blogPage);
    }

}
