package springapi.caballeros.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springapi.caballeros.auth.data.ClienteDetails;
import springapi.caballeros.models.cliente.Cliente;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    public static final long TOKEN_EXPIRED = 60 * 1000 * 60 * 30; // 30 minutes
    public static final String TOKEN_SENHA = "2e63d3ea-ae15-47ef-87e4-a67da043105e";

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Cliente cliente = new ObjectMapper().readValue(request.getInputStream(), Cliente.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(cliente.getEmail(),cliente.getPassword(),new ArrayList<>()));
        } catch (AuthenticationException | IOException e) {
            throw new RuntimeException("Fail to authenticate the user", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        ClienteDetails clienteDetails =(ClienteDetails) authResult.getPrincipal();
        if(clienteDetails.getCliente().isPresent()){
            String token = JWT
        }
    }
}
