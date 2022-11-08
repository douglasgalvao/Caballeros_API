// package springapi.caballeros.auth.authentication;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

// import org.springframework.lang.Nullable;
// import org.springframework.stereotype.Component;
// import org.springframework.web.servlet.HandlerInterceptor;
// import org.springframework.web.servlet.ModelAndView;
// import org.springframework.web.util.WebUtils;

// import lombok.Data;

// @Component
// @Data
// public class LoginInterceptor implements HandlerInterceptor {

//     @Override
//     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//             throws Exception {
//         System.out.println(request.getCookies().toString());
//         System.out.println(WebUtils.getCookie(request, "token"));
//         return HandlerInterceptor.super.preHandle(request, response, handler);
//     }

//     @Override
//     public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//             @Nullable ModelAndView modelAndView) throws Exception {
//         System.out.println("Testing2");
//         HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//     }

//     @Override
//     public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
//             @Nullable Exception ex) throws Exception {
//         System.out.println("Testing3");
//         HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//     }

// }
