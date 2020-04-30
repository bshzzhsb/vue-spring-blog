package self.hsb.blog.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import self.hsb.blog.dao.TodoDao;
import self.hsb.blog.po.Todo;
import self.hsb.blog.util.MyBeanUtils;

import java.util.Date;
import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/24 19:55
 */
@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoDao todoDao;

    @Override
    public Todo getTodoById(Integer id) {
        return todoDao.getOne(id);
    }

    @Override
    public List<Todo> listFinishedTodo() {
        Sort sort = Sort.by(Sort.Direction.DESC, "finishTime");
        return todoDao.findByFinishedTrue(sort);
    }

    @Override
    public List<Todo> listUnfinishedTodo() {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        return todoDao.findByFinishedFalse(sort);
    }

    @Transactional
    @Override
    public Todo saveTodo(Todo todo) {
        todo.setCreateTime(new Date());
        todo.setUpdateTime(new Date());
        todo.setFinished(false);
        return todoDao.save(todo);
    }

    @Transactional
    @Override
    public Todo updateTodo(Todo todo) {
        Todo t = todoDao.getOne(todo.getId());
        BeanUtils.copyProperties(todo, t, MyBeanUtils.getNullPropertyNames(todo));
        todo.setUpdateTime(new Date());
        return todoDao.save(todo);
    }

    @Override
    public Todo finishTodo(Integer id) {
        Todo todo = todoDao.getOne(id);
        todo.setFinishTime(new Date());
        todo.setFinished(true);
        return todoDao.save(todo);
    }

    @Override
    public void deleteTodo(Integer id) {
        todoDao.deleteById(id);
    }
}
