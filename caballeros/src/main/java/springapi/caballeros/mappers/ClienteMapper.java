package springapi.caballeros.mappers;

import lombok.Builder;
import lombok.Data;
import springapi.caballeros.dtos.ClienteDTO;
import springapi.caballeros.models.cliente.Cliente;

@Data
@Builder
public class ClienteMapper {
    public static Cliente toModel(ClienteDTO clienteDTO) {
        return Cliente.builder()

                .build();
    }

    public static ClienteDTO toDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .email(cliente.getEmail())
                .numero(cliente.getNumero())
                .numeroAgendamentos(cliente.getNumeroAgendamentos())
                .password(null)
                .build();
    }
}
