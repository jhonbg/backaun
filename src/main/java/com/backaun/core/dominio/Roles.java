package com.backaun.core.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Roles {
    @Id
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "permission_id")
    private Integer permissionId;

    @Column(name = "role_permissions")
    private String rolePermissions;


//    @ManyToMany
//    @JoinColumn(name = "permission_id", insertable = false, updatable = false)
//    private List<Permissions> permissions;

}
