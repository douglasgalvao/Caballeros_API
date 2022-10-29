package springapi.caballeros.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import springapi.caballeros.dtos.ClienteDTO;
import springapi.caballeros.mappers.ClienteMapper;
import springapi.caballeros.models.cliente.Cliente;
import springapi.caballeros.repositories.ClienteRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();


    @Transactional
    public List<ClienteDTO> getAllClientes() {
        return clienteRepository.findAll().stream().map(ClienteMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public ClienteDTO getClienteById(UUID id) {
        return ClienteMapper.toDTO(clienteRepository.getReferenceById(id));
    }

    @Transactional
    public void saveCliente(Cliente cliente) {
        encoder.encode()
        clienteRepository.save(cliente);
    }

    @Transactional
    public String editCliente(UUID id, Cliente cliente) {
        Optional<Cliente> actualCliente = clienteRepository.findById(id);
        if (actualCliente.isEmpty()) {
            return "Error, cliente não encontrado.";
        }
        if (cliente.getEmail() != null && cliente.getEmail().length() > 0) {
            actualCliente.get().setEmail(cliente.getEmail());
        }
        if (cliente.getNome() != null && cliente.getNome().length() > 0) {
            actualCliente.get().setNome(cliente.getNome());
        }
        if (cliente.getNumero() != null && cliente.getNumero().length() > 0) {
            actualCliente.get().setNumero(cliente.getNumero());
            ;
        }
        if (cliente.getPassword() != null && cliente.getPassword().length() > 8) {
            actualCliente.get().setPassword(cliente.getPassword());
        }
        clienteRepository.save(actualCliente.get());
        return ("The Client has been edited");
    }

    @Transactional
    public String deleteCliente(UUID id) {
        Cliente client = ClienteMapper.toModel(getClienteById(id));
        clienteRepository.delete(client);
        return "The Client has been deleted.";
    }



    @Transactional
    public String contarAgendamento(UUID id) {
        Cliente cliente = clienteRepository.getReferenceById(id);
        cliente.setNumeroAgendamentos(cliente.getNumeroAgendamentos() + 1);
        clienteRepository.save(cliente);
        return ("Agendou!");
    }

    // @Transactional
    // public ClienteDTO maiorCompra(){
    //     List<ClienteDTO> clientes = getAllClientes();
    //     ClienteDTO cliente = new ClienteDTO();
    //     cliente.setNumeroAgendamentos(0);
    //     clientes.forEach((client)-> {
    //         if(client.getNumeroAgendamentos() > cliente.getNumeroAgendamentos()){
    //             cliente = client;
    //         } 
            
    //     });
    //     return ClienteDTO.builder().build();
    // }


}
