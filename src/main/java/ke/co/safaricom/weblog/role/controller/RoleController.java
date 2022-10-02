package ke.co.safaricom.weblog.role.controller;

import ke.co.safaricom.weblog.role.dto.RoleCreateRequest;
import ke.co.safaricom.weblog.role.entity.Role;
import ke.co.safaricom.weblog.role.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles(){
        var roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody RoleCreateRequest newRole){
        var role = roleService.addRole(newRole);
        return ResponseEntity.ok(role);
    }

}
