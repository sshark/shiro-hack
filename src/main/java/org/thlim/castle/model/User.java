package org.thlim.castle.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 10/13/11
 * Time: 11:40 AM
 *
 */

@Entity
public class User implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "oid")
    private Long id;
    private String username;
    private String password;
    private String email;
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(joinColumns = {@JoinColumn(name = "user_oid")}, inverseJoinColumns = {@JoinColumn(name = "roles_group_oid")})
    private Map<String, RolesGroup> rolesGroup = new HashMap<String, RolesGroup>();

    /* For future reference
    @ManyToMany
    @JoinTable(joinColumns = {@JoinColumn(name = "user_oid")}, inverseJoinColumns = {@JoinColumn(name = "project_oid")})
    private Set<Project> projects;
    */

    // for Hibernate
    public User() {}

    public User(String username, String password, String email, String description)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.description = description;
    }

    public User(String username, String password, String email, String description, Map<String, RolesGroup> rolesGroup)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.description = description;
        this.rolesGroup = rolesGroup;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Map<String, RolesGroup> getRolesGroup()
    {
        return rolesGroup;
    }

    public void setRolesGroup(Map<String, RolesGroup> rolesGroup)
    {
        this.rolesGroup = rolesGroup;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User)o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return id != null ? id.hashCode() : 0;
    }

    public Set<String> getGrantedPermissions()
    {
        Set<String> permissionsString = new HashSet<String>();
        for (RolesGroup rolesGroup : getRolesGroup().values())
        {
            permissionsString.addAll(rolesGroup.getGrantedPermissions());
        }
        return permissionsString;
    }

    public Set<String> getGrantedRoles()
    {
        Set<String> roles = new HashSet<String>();
        for (RolesGroup rolesGroup : getRolesGroup().values())
        {
            roles.addAll(rolesGroup.getGrantedRoles());
        }
        return roles;
    }

    public void assignRolesGroupToInstance(RolesGroup rolesGroup)
    {
        getRolesGroup().put(rolesGroup.getInstance(), rolesGroup);
    }
}
