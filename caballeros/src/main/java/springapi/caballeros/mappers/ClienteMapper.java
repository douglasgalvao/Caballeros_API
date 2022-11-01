package springapi.caballeros.mappers;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import springapi.caballeros.dtos.ClienteDTO;
import springapi.caballeros.models.Cliente;

@Data
@Builder
public class ClienteMapper {
    public static Cliente toModel(ClienteDTO clienteDTO) {
        if (clienteDTO.getRole() != null) {
            return Cliente.builder()
                    .id(clienteDTO.getId())
                    .password(clienteDTO.getPassword())
                    .nome(clienteDTO.getNome())
                    .email(clienteDTO.getEmail())
                    .numero(clienteDTO.getNumero())
                    .numeroAgendamentos(clienteDTO.getNumeroAgendamentos())
                    .roles(clienteDTO.getRole())
                    .build();
        }
        return Cliente.builder()
                .id(clienteDTO.getId())
                .password(clienteDTO.getPassword())
                .nome(clienteDTO.getNome())
                .email(clienteDTO.getEmail())
                .numero(clienteDTO.getNumero())
                .numeroAgendamentos(clienteDTO.getNumeroAgendamentos())
                .roles(null)
                .build();
    }

    public static ClienteDTO toDTO(Cliente cliente) {

        if (cliente.getRoles() != null) {
            return ClienteDTO.builder()
                    .id(cliente.getId())
                    .nome(cliente.getNome())
                    .email(cliente.getEmail())
                    .numero(cliente.getNumero())
                    .numeroAgendamentos(cliente.getNumeroAgendamentos())
                    .password(null)
                    .role(cliente.getRoles())
                    .build();
        }
        return ClienteDTO.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .email(cliente.getEmail())
                .numero(cliente.getNumero())
                .numeroAgendamentos(cliente.getNumeroAgendamentos())
                .password(null)
                .role(null)
                .build();

    }


}
