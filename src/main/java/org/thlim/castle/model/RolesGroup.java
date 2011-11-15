package org.thlim.castle.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 10/17/11
 * Time: 5:58 PM
 *
 */
@Entity
public class RolesGroup implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "oid")
    private Long id;

    private String instance;

    @ManyToMany
    //@JoinTable(joinColumns = {@JoinColumn(name = "roles_group_oid")}, inverseJoinColumns = {@JoinColumn(name = "role_oid")})
    private Set<Role> roles = new HashSet<Role>();

    public RolesGroup() {}

    public RolesGroup(String instance, Set<Role> roles)
    {
        this.instance = instance;
        this.roles = roles;
    }

    public void addRole(Role role)
    {
        roles.add(role);
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Set<String> getGrantedRoles()
    {
        Set<String> grantedRoles = new HashSet<String>();
        StringBuilder roleBuffer = new StringBuilder();
        for (Role role : roles)
        {
           grantedRoles.add(roleBuffer.append(role.getName()).append(':').append(instance).toString());
           roleBuffer.setLength(0);
        }
        return grantedRoles;
    }

    public Collection<String> getGrantedPermissions()
    {
        Set<String> grantedPermissions = new HashSet<String>();
        StringBuilder permissionBuffer = new StringBuilder();
        for (Role role : roles)
        {
            for (Permission permission : role.getPermissions())
            {
                permissionBuffer.append(permission.toString()).append(':').append(instance);
                grantedPermissions.add(permissionBuffer.toString());
                permissionBuffer.setLength(0);
            }
        }
        return grantedPermissions;
    }

    public String getInstance()
    {
        return instance;
    }

    public void setInstance(String instance)
    {
        this.instance = instance;
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

    public static RolesGroup singleRoleProject(String instance, Role role)
    {
        Set<Role> singleRoleSet  = new HashSet<Role>();
        singleRoleSet.add(role);
        return new RolesGroup(instance, singleRoleSet);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolesGroup rolesGroup = (RolesGroup)o;

        if (id != null ? !id.equals(rolesGroup.id) : rolesGroup.id != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return id != null ? id.hashCode() : 0;
    }
}
