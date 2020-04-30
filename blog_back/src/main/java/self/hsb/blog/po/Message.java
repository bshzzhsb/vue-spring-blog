package self.hsb.blog.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/25 11:37
 */
@Entity
@Table(name = "message")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String content;
    private String avatar;
    private String nickname;
    private String email;
    private String web;

    @Column(name = "admin_message")
    private Boolean adminMessage;

    private Boolean checked;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @OneToMany(mappedBy = "parentMessage")
    private List<Message> replyMessages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_message_id")
    private Message parentMessage;

    public Message() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Boolean getAdminMessage() {
        return adminMessage;
    }

    public void setAdminMessage(Boolean adminMessage) {
        this.adminMessage = adminMessage;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Message> getReplyMessages() {
        return replyMessages;
    }

    public void setReplyMessages(List<Message> replyMessages) {
        this.replyMessages = replyMessages;
    }

    public Message getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(Message parentMessage) {
        this.parentMessage = parentMessage;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", web='" + web + '\'' +
                ", adminMessage=" + adminMessage +
                ", checked=" + checked +
                ", createTime=" + createTime +
                ", replyMessages=" + replyMessages +
                '}';
    }
}
