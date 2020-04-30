package self.hsb.blog.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/23 20:06
 */
@Entity
@Table(name = "mood_comment")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class MoodComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String content;
    private String nickname;
    private String avatar;
    private String email;
    private String web;

    @ManyToOne
    private Mood mood;

    @OneToMany(mappedBy = "parentMoodComment")
    private List<MoodComment> replyMoodComments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_mood_comment_id")
    private MoodComment parentMoodComment;

    @Column(name = "admin_mood_comment")
    private Boolean adminMoodComment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    public MoodComment() {
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public List<MoodComment> getReplyMoodComments() {
        return replyMoodComments;
    }

    public void setReplyMoodComments(List<MoodComment> replyMoodComments) {
        this.replyMoodComments = replyMoodComments;
    }

    public MoodComment getParentMoodComment() {
        return parentMoodComment;
    }

    public void setParentMoodComment(MoodComment parentMoodComment) {
        this.parentMoodComment = parentMoodComment;
    }

    public Boolean getAdminMoodComment() {
        return adminMoodComment;
    }

    public void setAdminMoodComment(Boolean adminMoodComment) {
        this.adminMoodComment = adminMoodComment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "MoodComment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", web='" + web + '\'' +
                ", mood=" + mood +
                ", replyMoodComments=" + replyMoodComments +
                ", adminMoodComment=" + adminMoodComment +
                ", createTime=" + createTime +
                '}';
    }
}
