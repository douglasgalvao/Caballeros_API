package springapi.caballeros.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "roles")
@NoArgsConstructor
public class Role {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column(unique = true)
    private String name;

    public Role(UUID id) {
        this.id = id;
    }

    public Role(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

}
