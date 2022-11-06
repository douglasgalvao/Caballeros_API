package springapi.caballeros.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import springapi.caballeros.config.GenerateUUID;
import springapi.caballeros.dtos.ClienteDTO;
import springapi.caballeros.dtos.ResponseTokenDTO;
import springapi.caballeros.dtos.RolesUserDTO;
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
  private RoleRepository roleRepository;
  @Value("${jwt.secret}")
  String jwtSecret;

  public List<ClienteDTO> getAllClientes(ResponseTokenDTO jwt) {
    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(this.jwtSecret)).build().verify(jwt.getToken());
    String idCliente = decodedJWT.getClaim("idCliente").toString();
    idCliente = idCliente.replaceAll("\"", "");
    ClienteDTO cliente = getClienteById(UUID.fromString(idCliente));
    if (cliente == null) {
      throw new Error("You have to be logged to access this route");
    }
    Optional<Role> roleADM = cliente.getRole().stream().filter(e -> e.getName().equals("ADMIN")).findAny();
    if (roleADM.isEmpty()) {
      throw new Error("This role just can be accessed by userADM role");
    }
    return clienteRepository.findAll().stream().map(ClienteMapper::toDTO).collect(Collectors.toList());
  }

  public PasswordEncoder getBcrypt() {
    return this.encoder;
  }

  public ClienteDTO getClienteById(UUID id) {
    Boolean user = clienteRepository.findById(id).isPresent();
    if (!user) {
      throw new Error("User not found in the database");
    }
    return ClienteMapper.toDTO(clienteRepository.findById(id).get());
  }

  public List<Role> getPermissionClientList(ResponseTokenDTO jwt) {
    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(this.jwtSecret)).build().verify(jwt.getToken());
    String idCliente = decodedJWT.getClaim("idCliente").toString();
    idCliente = idCliente.replaceAll("\"", "");
    System.out.println(idCliente);
    ClienteDTO cliente = getClienteById(UUID.fromString(idCliente));
    if (cliente == null) {
      throw new Error("The client that you try to get not exist");
    }
    return cliente.getRole();
  }

  public Boolean verifyIfClientExist(ResponseTokenDTO jwt) {
    try{
      DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(this.jwtSecret)).build().verify(jwt.getToken());
      String idCliente = decodedJWT.getClaim("idCliente").toString();
      idCliente = idCliente.replaceAll("\"", "");
      ClienteDTO cliente = getClienteById(UUID.fromString(idCliente));
      if (cliente != null) {
        return true;
      }
      return false;
    }catch(Error e){return false;}

  }

  public void saveCliente(ClienteDTO cliente) {
    List<Role> userRoles = new ArrayList<Role>();
    userRoles.add(findRoleByName("USER"));
    cliente.setRole(userRoles);
    Cliente client = ClienteMapper.toModel(cliente);
    client.setPassword(encoder.encode(cliente.getPassword()));
    clienteRepository.save(client);
  }

  public Role findRoleById(UUID id) {
    Optional<Role> role = this.roleRepository.findById(id);
    if (role.isEmpty()) {
      throw new Error("Role not found in database with this ID");
    }
    return role.get();
  }

  public Role findRoleByName(String name) {
    Role role = this.roleRepository.findByName(name);
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

  public List<Role> getAllRoles() {
    return roleRepository.findAll();
  }

  public HttpStatus saveRole(RolesUserDTO role) {
    if (role.getIdUser() == null || role.getIdUser().toString().length() <= 0) {
      throw new Error("The inputs can't be empty");
    }

    Optional<Cliente> user = clienteRepository.findById(role.getIdUser());
    if (user.isEmpty()) {
      throw new Error("User not found in the database");
    }
    List<Role> roles = new ArrayList<>();

    roles = role.getIdRoles().stream().map(e -> {
      return new Role(e, roleRepository.findById(e).get().getName());
    }).collect(Collectors.toList());

    user.get().setRoles(roles);

    clienteRepository.save(user.get());

    return HttpStatus.OK;
  }

  public HttpStatus settRole(Role role) {
    if (role.getName() == null || role.getName().length() <= 0) {
      throw new Error("You have to a name valid!");
    }
    role.setId(GenerateUUID.generateUUID());
    System.out.println(role);
    this.roleRepository.save(role);
    return HttpStatus.OK;
  }

  public List<Role> findAllRoles() {
    return this.roleRepository.findAll();
  }
}
