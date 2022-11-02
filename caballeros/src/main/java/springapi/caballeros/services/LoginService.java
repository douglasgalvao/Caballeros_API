package springapi.caballeros.services;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import springapi.caballeros.auth.ResponseTokenDTO;
import springapi.caballeros.dtos.ClienteLoginDTO;
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

    public ResponseTokenDTO login(ClienteLoginDTO clienteLoginDTO, HttpSession httpSession) {
        Cliente cliente = clienteRepository.findByEmail(clienteLoginDTO.getEmail());
        
        if (cliente == null) {
            throw new Error("Client not found in database");
        }

        if (clienteService.getBcrypt().matches((CharSequence) clienteLoginDTO.getPassword(),
                cliente.getPassword())) {

            String jwt = JWT.create()
            .withClaim("email", cliente.getEmail())
            .withClaim("idCliente", cliente.getId().toString())
            .sign(Algorithm.HMAC512(jwtSecret));
            httpSession.setAttribute("token", jwt);
            return new ResponseTokenDTO(jwt);
        }

        throw new Error("UNAUTHORIZED");
    }
}
