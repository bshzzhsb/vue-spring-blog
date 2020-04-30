package self.hsb.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import self.hsb.blog.po.Todo;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/24 19:55
 */
public interface TodoService {

    Todo getTodoById(Integer id);

    List<Todo> listFinishedTodo();

    List<Todo> listUnfinishedTodo();

    Todo saveTodo(Todo todo);

    Todo updateTodo(Todo todo);

    Todo finishTodo(Integer id);

    void deleteTodo(Integer id);
}
