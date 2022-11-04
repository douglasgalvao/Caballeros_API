package springapi.caballeros.auth;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
@Order(1)
public class LoginFilter implements Filter {
    @Value("${jwt.secret}")
    String jwtSecret;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (httpRequest.getServletPath().startsWith("/login")
                || httpRequest.getServletPath().startsWith("/cliente/save") || httpRequest.getServletPath().startsWith("/cliente/exist")) {
            chain.doFilter(request, response);
            return;
        }

        Cookie token = WebUtils.getCookie(httpRequest, "token");
        if (token == null) {
            httpResponse.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // try {
            String jwt = token.getValue();

            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(jwtSecret)).build().verify(jwt);
            String[] roles = decodedJWT.getClaim("permissions").asArray(String.class);
            String idCliente = decodedJWT.getClaim("idCliente").toString();
            httpRequest.setAttribute("permissions", roles);
            httpRequest.setAttribute("idCliente", idCliente);
            chain.doFilter(request, response);

        // } catch (JWTVerificationException ex) {
        //     System.out.println(ex);
        //     httpResponse.sendError(HttpStatus.UNAUTHORIZED.value());
        //     return;
        // }

    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

}
