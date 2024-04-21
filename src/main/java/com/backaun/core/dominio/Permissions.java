package com.backaun.core.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "permissions")
@NoArgsConstructor
@AllArgsConstructor
public class Permissions {
    @Id
    @Column(name = "permission_id")
    private Integer permissionId;

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "permission_description")
    private String permissionDescription;

//    @ManyToMany(mappedBy = "permissions")
//    private List<Roles> roles;
}
