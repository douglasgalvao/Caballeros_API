package springapi.caballeros.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springapi.caballeros.dtos.ClienteDTO;
import springapi.caballeros.dtos.RolesUserDTO;
import springapi.caballeros.models.Cliente;
import springapi.caballeros.models.Role;
import springapi.caballeros.services.ClienteService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ClienteDTO>> getAllClientes(@RequestAttribute("idCliente") String idCliente,
            @RequestAttribute("permissions") List<Role> permissions) {
        return ResponseEntity.ok(clienteService.getAllClientes(idCliente,permissions));
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable UUID id) {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public ResponseEntity<HttpStatus> saveCliente(@RequestBody ClienteDTO cliente) {
        clienteService.saveCliente(cliente);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping(value = "/save/admin")
    @ResponseBody
    public ResponseEntity<HttpStatus> saveAdmin(@RequestBody ClienteDTO cliente) {
        clienteService.saveAdmin(cliente);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteCliente(@PathVariable UUID id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping(value = "/edit/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> editCliente(@PathVariable UUID id, @RequestBody Cliente cliente) {
        clienteService.editCliente(id, cliente);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    // quem deve acessar essa rota é o barbeiro quando confima o serviço
    @PostMapping("countAppointment")
    @ResponseBody
    public ResponseEntity<String> contarAgendamento(UUID id) {
        return ResponseEntity.ok(clienteService.contarAgendamento(id));
    }

    @PostMapping(value = "/save/clientRole")
    @ResponseBody
    public ResponseEntity<HttpStatus> saveRole(@RequestBody RolesUserDTO role) {
        return ResponseEntity.ok(clienteService.saveRole(role));
    }

    @PostMapping(value = "/set/role")
    @ResponseBody
    public ResponseEntity<HttpStatus> setRole(@RequestBody Role role) {
        return ResponseEntity.ok(clienteService.settRole(role));
    }

    @GetMapping(value = "/get/role")
    @ResponseBody
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(clienteService.findAllRoles());
    }

}
