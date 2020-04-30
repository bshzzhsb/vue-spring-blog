package self.hsb.blog.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/25 11:46
 */
@Entity
@Table(name = "blog_comments")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class BlogComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String content;
    private String avatar;
    private String email;
    private String nickname;

    @Column(name = "admin_comment")
    private Boolean adminComment;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @OneToMany(mappedBy = "parentComment")
    private List<BlogComment> replyComments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private BlogComment parentComment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    public BlogComment() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(Boolean adminComment) {
        this.adminComment = adminComment;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<BlogComment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<BlogComment> replyComments) {
        this.replyComments = replyComments;
    }

    public BlogComment getParentComment() {
        return parentComment;
    }

    public void setParentComment(BlogComment parentComment) {
        this.parentComment = parentComment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BlogComment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", adminComment=" + adminComment +
                ", blog=" + blog +
                ", replyComments=" + replyComments +
                ", createTime=" + createTime +
                '}';
    }
}
