//package wheres saved
package springapi.caballeros.dtos;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private UUID id;
    private String nome;
    private List<String> role;
    private String numero;
    private String email;
    private int numeroAgendamentos;
    private String password;
}