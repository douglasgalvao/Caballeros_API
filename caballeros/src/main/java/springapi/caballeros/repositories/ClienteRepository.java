package springapi.caballeros.repositories;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import springapi.caballeros.models.cliente.Cliente;
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    public Cliente findByEmail(String email);

}
