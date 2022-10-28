package springapi.caballeros.models.cliente;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Cliente {
    @Id
    private UUID id;
    private String nome;
    private String numero;
    private String email;
    private int numeroAgendamentos;
}