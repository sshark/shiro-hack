package org.thlim.castle.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 10/13/11
 * Time: 11:47 AM
 *
 */

@Entity
public class Role implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="oid")
    private Long id;
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(joinColumns = {@JoinColumn(name="role_oid")}, inverseJoinColumns = {@JoinColumn(name="permission_oid")})
    private Set<Permission> permissions = new HashSet<Permission>();

    public Role() {}

    public Role(String name)
    {
        this.name = name;
    }

    public void add(Permission permission)
    {
        permissions.add(permission);
    }

    public Set<Permission> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions)
    {
        this.permissions = permissions;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role)o;

        if (name != null ? !name.equals(role.name) : role.name != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return name != null ? name.hashCode() : 0;
    }
}
