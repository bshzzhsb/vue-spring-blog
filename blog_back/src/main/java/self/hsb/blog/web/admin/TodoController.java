package self.hsb.blog.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import self.hsb.blog.po.Todo;
import self.hsb.blog.response.Response;
import self.hsb.blog.response.ResponseFactory;
import self.hsb.blog.service.TodoService;
import self.hsb.blog.service.UserService;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/24 20:05
 */
@Controller
@RequestMapping("/hsblog/admin")
public class TodoController {

    @Autowired
    private TodoService todoService;
    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping("/todo")
    @ResponseBody
    public Response getTodos() {
        List<Todo> todos = todoService.listUnfinishedTodo();
        return ResponseFactory.buildSuccessResponse(todos);
    }

    @CrossOrigin
    @GetMapping("/todo/finish")
    @ResponseBody
    public Response getFinishedTodos() {
        List<Todo> todoPage = todoService.listFinishedTodo();
        return ResponseFactory.buildSuccessResponse(todoPage);
    }

    @CrossOrigin
    @GetMapping("/todo/{id}")
    @ResponseBody
    public Response getTodoById(@PathVariable Integer id) {
        Todo todo = todoService.getTodoById(id);
        return ResponseFactory.buildSuccessResponse(todo);
    }

    @CrossOrigin
    @PostMapping("/todo")
    @ResponseBody
    public Response saveTodo(@RequestBody Todo todo) {
        todo.setUser(userService.getUserByUsername("hsblock"));
        Todo t;
        if (todo.getId() == null) {
            t = todoService.saveTodo(todo);
        } else {
            t = todoService.updateTodo(todo);
        }
        if (t == null) {
            return ResponseFactory.buildFailResponse("保存失败");
        } else {
            return ResponseFactory.buildSuccessResponse(null);
        }
    }

    @CrossOrigin
    @PostMapping("/todo/finish/{id}")
    @ResponseBody
    public Response finishTodo(@PathVariable Integer id) {
        todoService.finishTodo(id);
        return ResponseFactory.buildSuccessResponse(null);
    }

    @CrossOrigin
    @PostMapping("/todo/delete/{id}")
    @ResponseBody
    public Response deleteTodo(@PathVariable Integer id) {
        todoService.deleteTodo(id);
        return ResponseFactory.buildSuccessResponse(null);
    }

}
