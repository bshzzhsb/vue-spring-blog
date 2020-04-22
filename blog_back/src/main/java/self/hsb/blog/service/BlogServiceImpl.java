package self.hsb.blog.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import self.hsb.blog.dao.BlogDao;
import self.hsb.blog.po.Blog;
import self.hsb.blog.po.Type;
import self.hsb.blog.util.MyBeanUtils;
import self.hsb.blog.vo.BlogQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/17 11:25
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogDao.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(BlogQuery blogQuery, Pageable pageable) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (blogQuery.getTitle() != null && !"".equals(blogQuery.getTitle())) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%"+blogQuery.getTitle()+"%"));
                }
                if (blogQuery.getTypeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blogQuery.getTypeId()));
                }
                if (blogQuery.getRecommend() != null && blogQuery.getRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blogQuery.getRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogDao.findByQuery(query, pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        return blogDao.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Blog blog) {
        Blog b = blogDao.getOne(blog.getId());
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogDao.save(b);
    }

    @Override
    public Blog getBlogById(Integer id) {
        return blogDao.getOne(id);
    }

    @Override
    public void deleteBlog(Integer id) {
        blogDao.deleteById(id);
    }

}
