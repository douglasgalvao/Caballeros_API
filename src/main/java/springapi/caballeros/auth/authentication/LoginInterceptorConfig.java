// package springapi.caballeros.auth.authentication;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.HandlerInterceptor;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

// @Configuration
// public class LoginInterceptorConfig extends WebMvcConfigurationSupport {

//     @Override
//     protected void addCorsMappings(CorsRegistry registry) {
//         super.addCorsMappings(registry);
//     }

//     @Override
//     protected void addInterceptors(InterceptorRegistry registry) {
//         registry.addInterceptor(new LoginInterceptor()).excludePathPatterns(
//                 "/login", "/cliente/save", "/cliente/getPermission", "/cliente/exist");
//     }

// }
