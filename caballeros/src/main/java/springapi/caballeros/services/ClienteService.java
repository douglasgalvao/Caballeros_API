package springapi.caballeros.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import springapi.caballeros.dtos.ClienteDTO;
import springapi.caballeros.mappers.ClienteMapper;
import springapi.caballeros.models.cliente.Cliente;
import springapi.caballeros.repositories.ClienteRepository;



@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public List<ClienteDTO> getAllClientes(){return clienteRepository.findAll().stream().map(ClienteMapper::toDTO).collect(Collectors.toList());}
    @Transactional
    public ClienteDTO getClienteById(UUID id) {
        return ClienteMapper.toDTO(clienteRepository.getReferenceById(id));
    }

    @Transactional
    public void saveCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Transactional
    public String contarAgendamento(UUID id){
        Cliente cliente = clienteRepository.getReferenceById(id);
        cliente.setNumeroAgendamentos(cliente.getNumeroAgendamentos()+1);
        clienteRepository.save(cliente);
        return("Agendou!");
    }

    @Transactional
    public String editarCliente(@PathVariable UUID id,@RequestBody Cliente cliente){
        Cliente actualCliente = clienteRepository.getReferenceById(id);
        if(cliente.getEmail() != null &&cliente.getEmail().length()>0){
            actualCliente.setEmail(cliente.getEmail());
        }
        if(cliente.getNome() != null &&cliente.getNome().length()>0){
            actualCliente.setNome(cliente.getNome());
        }
        if(cliente.getNumero() != null &&cliente.getNumero().length()>0){
            actualCliente.setNome(cliente.getEmail());
        }
        if(cliente.getPassword() != null &&cliente.getEmail().length()>0){
            actualCliente.setPassword(cliente.getPassword());
        }
        clienteRepository.save(actualCliente);
        return("The Client has been edited");
    }

    @Transactional
    public String deleteCliente(UUID id){
        Cliente cliente = ClienteMapper.toModel(getClienteById(id));
        clienteRepository.delete(cliente);
        return "The Client has been deleted.";
    }

}
