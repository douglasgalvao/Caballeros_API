package springapi.caballeros.services;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import springapi.caballeros.dtos.ClienteLoginDTO;
import springapi.caballeros.dtos.ResponseTokenDTO;
import springapi.caballeros.models.Cliente;
import springapi.caballeros.repositories.ClienteRepository;

@Service
public class LoginService {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ClienteService clienteService;
    @Value("${jwt.secret}")
    private String jwtSecret;

    private String jwt;

    public ResponseTokenDTO login(ClienteLoginDTO clienteLoginDTO, ServletResponse httpServletResponse) {
        Cliente cliente = clienteRepository.findByEmail(clienteLoginDTO.getEmail());
        if (cliente == null) {
            throw new Error("Client not found in database");
        }

        if (clienteService.getBcrypt().matches((CharSequence) clienteLoginDTO.getPassword(),
                cliente.getPassword())) {

            String jwt = JWT.create()
                    .withClaim("idCliente", cliente.getId().toString())
                    .sign(Algorithm.HMAC512(jwtSecret));

            this.jwt = jwt.toString();
            Cookie cok = new Cookie("token", jwt);
            cok.setMaxAge(60 * 30 * 30);
            ((HttpServletResponse) httpServletResponse).addCookie(cok);
            return new ResponseTokenDTO(jwt);
        }

        throw new Error("UNAUTHORIZED");
    }

    public String getJwt() {
        return this.jwt;
    }
}
