package self.hsb.blog.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * @author SipooHe
 * @date 2020/4/21 13:15
 */
@Entity
@Table(name = "mood")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;

    private Boolean commentable;

    private Boolean published;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private Integer likes;

    public Mood() {
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

    public Boolean getCommentable() {
        return commentable;
    }

    public void setCommentable(Boolean commentable) {
        this.commentable = commentable;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Mood{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", commentable=" + commentable +
                ", published=" + published +
                ", user=" + user +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", likes=" + likes +
                '}';
    }
}
