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
                if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                    return true;
                }
                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("loggedIn") != null) {
                    return true; // logget ind
                }
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().write("Not logged in");
                return false;
            }
        }).addPathPatterns("/activities/**", "/reservations/**"); // Her skal vi fikse det med Auth @RequestMapping, er usikker om den finder den gennem /auth requestmapping.
    }
}