package springapi.caballeros.models.cliente;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import lombok.Builder;
import lombok.Data;
import springapi.caballeros.config.GenerateUUID;

@Entity
@Builder
@Data
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    Cliente(String nome,String numero,String email,String password){
        this.id = GenerateUUID.generateUUID();
        this.nome=nome;
        this.numero=numero;
        this.numeroAgendamentos = 0;
        this.email=email;
        this.password=password;
    }

    @Id
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;
    private String nome;
    private String numero;
    private String email;
    private int numeroAgendamentos;
    private String password;
}