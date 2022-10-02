package ke.co.safaricom.weblog.role.service;

import ke.co.safaricom.weblog.role.dto.RoleCreateRequest;
import ke.co.safaricom.weblog.role.entity.Role;
import ke.co.safaricom.weblog.role.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles(){
        var role = roleRepository.findAll();
        return role;
    }
    public Role addRole(RoleCreateRequest newRole){
        var role = new Role();
        role.setName(newRole.getName());
        return roleRepository.save(role);
    }
}
