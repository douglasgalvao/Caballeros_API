package springapi.caballeros.auth;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import springapi.caballeros.dtos.ClienteDTO;
import springapi.caballeros.services.ClienteService;
import springapi.caballeros.services.LoginService;

@Component
@Order(1)
public class LoginFilter implements Filter {
    @Value("${jwt.secret}")
    String jwtSecret;

    @Autowired
    ClienteService clienteService;
    @Autowired
    LoginService loginService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getServletPath().startsWith("/login")
                || httpRequest.getServletPath().startsWith("/cliente/save")
                || httpRequest.getServletPath().startsWith("/cliente/getPermission")
                || httpRequest.getServletPath().startsWith("/cliente/exist")) {
            chain.doFilter(request, response);
            return;
        }
        
        Cookie cookie = WebUtils.getCookie(httpRequest, "token");
        System.out.println(cookie);
        if(cookie==null){
            throw new Error("There's no token setted");
        }
        String jwtCookie = cookie.getValue();
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(this.jwtSecret)).build().verify(jwtCookie);
        String idCliente = decodedJWT.getClaim("idCliente").toString();
        idCliente = idCliente.replaceAll("\"", "");
        ClienteDTO cliente = clienteService.getClienteById(UUID.fromString(idCliente));
        if (cliente == null) {
            throw new Error("The client that you try to get not exist");
        }

        chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

}
