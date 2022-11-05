package springapi.caballeros.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springapi.caballeros.dtos.ClienteDTO;
import springapi.caballeros.dtos.ResponseTokenDTO;
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

    @PostMapping(value = "/getAllClientes")
    @ResponseBody
    public ResponseEntity<List<ClienteDTO>> getAllClientes(@RequestBody ResponseTokenDTO token) {
        return ResponseEntity.ok(clienteService.getAllClientes(token));
    }

    @PostMapping(value = "/getPermissionUser")
    @ResponseBody
    public ResponseEntity<List<Role>> getPermissionUser(@RequestBody ResponseTokenDTO token) {
        return ResponseEntity.ok(clienteService.getPermissionClientList(token));
    }

    @GetMapping(value = "/verifyIsClientExist/{email}")
    @ResponseBody
    public ResponseEntity<Boolean> verifyIsClientExist(@PathVariable String email) {
        return ResponseEntity.ok(clienteService.verifyIfClientExist(email));
    }

    @PostMapping(value = "/saveCliente")
    @ResponseBody
    public ResponseEntity<HttpStatus> saveCliente(@RequestBody ClienteDTO cliente) {
        clienteService.saveCliente(cliente);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteCliente/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteCliente(@PathVariable UUID id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping(value = "/editCliente/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> editCliente(@PathVariable UUID id, @RequestBody Cliente cliente) {
        clienteService.editCliente(id, cliente);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }



    @PostMapping(value = "/saveRole")
    @ResponseBody
    public ResponseEntity<HttpStatus> saveRole(@RequestBody RolesUserDTO role) {
        return ResponseEntity.ok(clienteService.saveRole(role));
    }

    @PostMapping(value = "/setRole")
    @ResponseBody
    public ResponseEntity<HttpStatus> setRole(@RequestBody Role role) {
        return ResponseEntity.ok(clienteService.settRole(role));
    }

    @GetMapping(value = "/getAllRoles")
    @ResponseBody
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(clienteService.findAllRoles());
    }

}
