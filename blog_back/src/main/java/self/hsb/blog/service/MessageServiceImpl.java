package self.hsb.blog.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import self.hsb.blog.dao.MessageDao;
import self.hsb.blog.po.Message;
import self.hsb.blog.util.MyBeanUtils;

import java.util.*;

/**
 * @author SipooHe
 * @date 2020/4/25 12:20
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public Map<String, Object> listMessage(Pageable pageable) {
        Page<Message> parentMessages = messageDao.findByParentMessageNull(pageable);
        List<Message> parentMessagesContent = parentMessages.getContent();
        List<Message> messages = eachMessage(parentMessagesContent);
        int totalPages = parentMessages.getTotalPages();
        int pageNumber = pageable.getPageNumber();
        Map<String, Object> map = new HashMap<>();
        map.put("messages", messages);
        map.put("totalPages", totalPages);
        map.put("pageNumber", pageNumber);
        return map;
    }

    @Override
    public Map<String, Integer> getPageInfo(Pageable pageable) {
        int totalPages = messageDao.findByParentMessageNull(pageable).getTotalPages();
        int pageNumber = pageable.getPageNumber();
        Map<String, Integer> map = new HashMap<>();
        map.put("totalPages", totalPages);
        map.put("pageNumber", pageNumber);
        return map;
    }

    @Override
    public Long countMessage() {
        return messageDao.count();
    }

    @Transactional
    @Override
    public Message saveMessage(Message message) {
        message.setChecked(false);
        Integer parentId = message.getParentMessage().getId();
        if (parentId != -1) {
            message.setParentMessage(messageDao.getOne(parentId));
        } else {
            message.setParentMessage(null);
        }
        message.setCreateTime(new Date());
        return messageDao.save(message);
    }

    @Transactional
    @Override
    public Message checkMessage(Integer id) {
        Message m = messageDao.getOne(id);
        m.setChecked(true);
        return messageDao.save(m);
    }

    @Override
    public void deleteMessage(Integer id) {
        messageDao.deleteById(id);
    }

    private List<Message> eachMessage(List<Message> parentPageContents) {
        List<Message> messagesView = new ArrayList<>();
        for (Message parentPageContent : parentPageContents) {
            Message m = new Message();
            BeanUtils.copyProperties(parentPageContent, m);
            messagesView.add(m);
        }
        combineChildren(messagesView);
        return messagesView;
    }

    private void combineChildren(List<Message> messagesView) {
        for (Message message : messagesView) {
            List<Message> replys1 = message.getReplyMessages();
            for (Message reply1 : replys1) {
                recursively(reply1);
            }
            message.setReplyMessages(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }

    private List<Message> tempReplys = new ArrayList<>();

    private void recursively(Message message) {
        message.getParentMessage().setReplyMessages(null);
        tempReplys.add(message);
        if (message.getReplyMessages().size() > 0) {
            List<Message> replys =  message.getReplyMessages();
            for (Message reply : replys) {
                recursively(reply);
            }
        }
    }
}
