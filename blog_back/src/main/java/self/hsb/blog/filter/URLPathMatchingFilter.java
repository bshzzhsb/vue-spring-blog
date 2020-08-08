package self.hsb.blog.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author SipooHe
 * @date 2020/4/25 20:32
 */
public class URLPathMatchingFilter extends PathMatchingFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 对 admin 资源的访问请求预处理
     */
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // 放行 options 方法
        if (HttpMethod.OPTIONS.toString().equals(httpServletRequest.getMethod())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }
        String requestAPI = getPathWithinApplication(request);
        Subject subject = SecurityUtils.getSubject();
        if ("/hsblog/admin/login".equals(requestAPI)) {
            return true;
        }
        if (!subject.isAuthenticated()) {
            logger.info("未认证的访客访问被驳回！");
            return false;
        }
        return true;
    }
}
