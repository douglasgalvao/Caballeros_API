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
import springapi.caballeros.dtos.ClienteDTO;
import springapi.caballeros.mappers.ClienteMapper;
import springapi.caballeros.models.Cliente;
import springapi.caballeros.models.Role;
import springapi.caballeros.repositories.ClienteRepository;
import springapi.caballeros.repositories.RoleRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    private RoleRepository role;

    public List<ClienteDTO> getAllClientes() {
        return clienteRepository.findAll().stream().map(ClienteMapper::toDTO).collect(Collectors.toList());
    }

    public ClienteDTO getClienteById(UUID id) {

        Boolean user = clienteRepository.findById(id).isPresent();
        if (!user) {
            throw new Error("User not found in the database");
        }
        return ClienteMapper.toDTO(clienteRepository.findById(id).get());
    }

    public void saveCliente(ClienteDTO cliente) {
        Cliente client = ClienteMapper.toModel(cliente);
        client.setPassword(encoder.encode(cliente.getPassword()));
        clienteRepository.save(client);
    }

    public String saveRole(Role role) {
        if (role.getId().toString().length() <= 0 || role.getName().length() <= 0) {
            throw new Error("You can add a empty role");
        }
        this.role.save(role);
        return "Role was saved with success";
    }

    public Role findRoleById(UUID id) {
        Optional<Role> role = this.role.findById(id);
        if (role.isEmpty()) {
            throw new Error("Role not found in database with this ID");
        }
        return role.get();
    }

    public Role findRoleByName(String name) {
        Role role = this.role.findByName(name);
        if (role.getName() == null || role.getId() == null ||
                role.getId().toString().length() <= 0 || role.getName().length() <= 0) {
            throw new Error("Role not found in database with this ID");
        }
        return role;
    }

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

    public String deleteCliente(UUID id) {
        Cliente client = ClienteMapper.toModel(getClienteById(id));
        clienteRepository.delete(client);
        return "The Client has been deleted.";
    }

    public String contarAgendamento(UUID id) {
        Cliente cliente = clienteRepository.getReferenceById(id);
        cliente.setNumeroAgendamentos(cliente.getNumeroAgendamentos() + 1);
        clienteRepository.save(cliente);
        return ("Agendou!");
    }

    // @Transactional
    // public ClienteDTO getClienteComMaiorAgendamento(){
    // List<ClienteDTO> clientes = getAllClientes();
    // ClienteDTO cliente = new ClienteDTO();
    // cliente.setNumeroAgendamentos(0);
    // clientes.forEach((client)-> {
    // if(client.getNumeroAgendamentos() > cliente.getNumeroAgendamentos()){
    // cliente = client;
    // }

    // });
    // return ClienteDTO.builder().build();
    // }

}
