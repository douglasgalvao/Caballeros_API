package springapi.caballeros.repositories;

import java.util.UUID;
import javax.management.relation.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    public Role fingByName(String nameRole);
}
