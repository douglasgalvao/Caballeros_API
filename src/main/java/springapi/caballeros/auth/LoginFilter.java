package springapi.caballeros.auth;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import springapi.caballeros.dtos.ClienteDTO;
import springapi.caballeros.services.ClienteService;
import springapi.caballeros.services.LoginService;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@Order(Ordered.HIGHEST_PRECEDENCE)
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
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "*");

        if (httpRequest.getServletPath().startsWith("/login")
                || httpRequest.getServletPath().startsWith("/cliente/saveCliente")
                || httpRequest.getServletPath().startsWith("/cliente/getPermission")
                || httpRequest.getServletPath().startsWith("/cliente/setRole")
                // || httpRequest.getServletPath().startsWith("/cliente/verifyifclientexist")
                // || httpRequest.getServletPath().startsWith("/cliente/getClient")

        ) {
            chain.doFilter(httpRequest, response);
            return;
        }

        String jwt = httpRequest.getHeader("Authorization");
        System.out.println("JWT -> " + jwt);
        if (jwt == null) {
            throw new Error("Token null");
        }
        if (isTokenValid(jwt) == false) {
            throw new Error("Token inválido");
        }
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(this.jwtSecret)).build().verify(jwt);
            String idCliente = decodedJWT.getClaim("idCliente").toString();
            idCliente = idCliente.replaceAll("\"", "");
            ClienteDTO cliente = clienteService.getClienteById(UUID.fromString(idCliente));
            if (cliente == null) {
                throw new Error("The client that you try to get not exist");
            }
            chain.doFilter(request, response);
        } catch (Error e) {
            throw e;
        }
    }

    public boolean isTokenValid(String token) { // Método que verifica se o Token é válido.
        try {
            JWT.require(Algorithm.HMAC512(this.jwtSecret)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
