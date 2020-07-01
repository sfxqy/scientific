package cn.hsernos.intercept;

import cn.hsernos.common.beans.User;
import cn.hsernos.common.exceptions.NoPermissionException;
import cn.hsernos.common.exceptions.UnloginException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NeedInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = ((HttpServletRequest) httpServletRequest).getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new UnloginException("未登录");
        } else if (user.getPower() != 4) {
            throw new NoPermissionException("没有权限");
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}