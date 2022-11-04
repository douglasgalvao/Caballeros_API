package springapi.caballeros.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
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

    public ResponseTokenDTO login(ClienteLoginDTO clienteLoginDTO, HttpServletResponse httpServletResponse) {
        Cliente cliente = clienteRepository.findByEmail(clienteLoginDTO.getEmail());

        if (cliente == null) {
            throw new Error("Client not found in database");
        }

        if (clienteService.getBcrypt().matches((CharSequence) clienteLoginDTO.getPassword(),
                cliente.getPassword())) {

            String jwt = JWT.create()
                    .withClaim("idCliente", cliente.getId().toString())
                    .sign(Algorithm.HMAC512(jwtSecret));
            Cookie cookie = new Cookie("token", jwt);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 30);
            cookie.setHttpOnly(true);
            httpServletResponse.addCookie(cookie);
            return new ResponseTokenDTO(jwt);
        }

        throw new Error("UNAUTHORIZED");
    }
}
