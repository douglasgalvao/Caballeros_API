package springapi.caballeros.dtos;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolesUserDTO {
    private UUID idUser;
    private List<UUID> idRoles;
}
