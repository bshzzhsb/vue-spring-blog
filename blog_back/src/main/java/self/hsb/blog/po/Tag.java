package self.hsb.blog.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/16 21:08
 */
@Entity
@Table(name = "tag")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer", "blogs"})
public class Tag {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    private String name;

    @ManyToMany
    @JoinTable(name = "blog_tags", joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "blog_id")})
    private List<Blog> blogs = new ArrayList<>();

    public Tag() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
