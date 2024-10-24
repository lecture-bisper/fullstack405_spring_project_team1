package bitc.fullstack405.publicwc.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String userId = (session != null) ? (String) session.getAttribute("userId") : null;

        // 로그인하지 않은 사용자는 로그인 페이지로 리다이렉트
        if (userId == null && request.getRequestURI().startsWith("/users/mypage")) {
            response.sendRedirect(request.getContextPath() + "/users/login");
            return false;
        }
        return true;
    }
}