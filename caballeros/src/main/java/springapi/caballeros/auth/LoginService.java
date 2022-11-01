package springapi.caballeros.auth;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import springapi.caballeros.dtos.ClienteLoginDTO;
import springapi.caballeros.models.Cliente;
import springapi.caballeros.repositories.ClienteRepository;
import springapi.caballeros.services.ClienteService;

@Service
public class LoginService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ClienteService clienteService;



    public HttpStatus login(ClienteLoginDTO clienteLoginDTO, HttpSession httpSession){
        System.out.println(clienteLoginDTO.getEmail());
        System.out.println(clienteLoginDTO.getPassword());
        Cliente cliente = clienteRepository.findByEmail(clienteLoginDTO.getEmail());
        
        // System.out.println(cliente.getPassword());
        // if (cliente == null) {
        //     throw new Error("Client not found in database");
        // }
        if(clienteLoginDTO.getPassword() == cliente.getPassword()){
            httpSession.setAttribute("Token", cliente.getId());
            return HttpStatus.OK;
        }
        return HttpStatus.UNAUTHORIZED;
    }

    // getBcrypt().encode(clienteLoginDTO.getEmail())
}
