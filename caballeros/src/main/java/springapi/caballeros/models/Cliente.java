package springapi.caballeros.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import lombok.Builder;
import lombok.Data;
import springapi.caballeros.config.GenerateUUID;

@Entity
@Builder
@Data
@Table(name = "clientes")
@AllArgsConstructor
public class Cliente {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;
    private String nome;
    private String numero;
    @Column(unique = true)
    private String email;
    private int numeroAgendamentos;
    @Column(name = "password")
    private String password;
    @ManyToMany
    private List<Role> roles;

    public Cliente() {
        this.id = GenerateUUID.generateUUID();
        this.nome = null;
        this.numero = null;
        this.password = null;
        this.email = null;
        this.numeroAgendamentos = 0;
        this.roles = null;
    }

    public Cliente(String nome, String numero, String email, String password) {
        this.id = GenerateUUID.generateUUID();
        this.nome = nome;
        this.numero = numero;
        this.numeroAgendamentos = 0;
        this.email = email;
        this.password = password;
        this.roles = null;
    }

}