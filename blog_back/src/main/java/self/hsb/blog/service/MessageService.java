package self.hsb.blog.service;

import org.springframework.data.domain.Pageable;
import self.hsb.blog.po.Message;

import java.util.List;
import java.util.Map;

/**
 * @author SipooHe
 * @date 2020/4/25 12:20
 */
public interface MessageService {

    Map<String, Object>  listMessage(Pageable pageable);

    Map<String, Integer> getPageInfo(Pageable pageable);

    void deleteMessage(Integer id);

    Long countMessage();

    Message saveMessage(Message message);

    Message checkMessage(Integer id);

}
