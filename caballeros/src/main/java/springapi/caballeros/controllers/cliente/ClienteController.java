package springapi.caballeros.controllers.cliente;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springapi.caballeros.dtos.ClienteDTO;
import springapi.caballeros.models.cliente.Cliente;
import springapi.caballeros.services.ClienteService;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    // quem deve acessar essa rota é o barbeiro quando confima o serviço
    @PostMapping("countAppointment")
    @ResponseBody
    public ResponseEntity<String> contarAgendamento(UUID id){
        return ResponseEntity.ok(clienteService.contarAgendamento(id));
    }



    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ClienteDTO>> getAllClientes(){
        return ResponseEntity.ok(clienteService.getAllClientes());
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable UUID id) {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public ResponseEntity<HttpStatus> saveCliente(@RequestBody Cliente cliente) {
        clienteService.saveCliente(cliente);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }


    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteCliente(@PathVariable UUID id){
        clienteService.deleteCliente(id);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }


    

}
