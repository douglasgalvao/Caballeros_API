package springapi.caballeros.services;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import springapi.caballeros.dtos.ClienteLoginDTO;
import springapi.caballeros.dtos.ResponseTokenDTO;
import springapi.caballeros.models.Cliente;
import springapi.caballeros.repositories.ClienteRepository;

@Component
public class LoginService {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ClienteService clienteService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private String jwt;

    public ResponseTokenDTO login(ClienteLoginDTO clienteLoginDTO, ServletRequest request, ServletResponse response) {
        Cliente cliente = clienteRepository.findByEmail(clienteLoginDTO.getEmail());
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (cliente == null) {
            throw new Error("Client not found in database");
        }

        if (clienteService.getBcrypt().matches((CharSequence) clienteLoginDTO.getPassword(),
                cliente.getPassword())) {

            String jwt = JWT.create()
                    .withClaim("idCliente", cliente.getId().toString())
                    .sign(Algorithm.HMAC512(jwtSecret));
                    
            httpServletResponse.setHeader("token", jwt);
            httpServletRequest.setAttribute("token", jwt);
            httpServletResponse.addCookie(new Cookie("token", jwt));
            this.jwt = jwt.toString();
            return new ResponseTokenDTO(jwt);
        }

        throw new Error("UNAUTHORIZED");
    }

    public String getJwt() {
        return this.jwt;
    }
}
