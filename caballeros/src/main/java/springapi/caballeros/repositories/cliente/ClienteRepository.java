package springapi.caballeros.repositories.cliente;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springapi.caballeros.models.cliente.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,UUID>{}
