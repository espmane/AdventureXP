package gruppe3.adventurexp.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {
                if (isPublic(request)) {
                    return true;
                }
                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("loggedIn") != null) {
                    return true;
                }
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().write("Not logged in");
                return false;
            }
        }).addPathPatterns("/activities/**", "/reservations/**", "/schedules/**");
    }

    // Det kunder må uden login
    private boolean isPublic(HttpServletRequest request) {
        String method = request.getMethod();
        String path = request.getRequestURI();

        if ("OPTIONS".equalsIgnoreCase(method)) return true;
        if ("GET".equalsIgnoreCase(method) && path.startsWith("/activities/")) return true;
        if ("GET".equalsIgnoreCase(method) && (path.equals("/activities") || path.startsWith("/activities/"))) return true;
        if ("POST".equalsIgnoreCase(method) && (path.equals("/reservations/save") || path.equals("/reservations/company"))) return true;
        return false;
    }
}