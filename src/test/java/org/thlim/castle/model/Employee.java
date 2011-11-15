package org.thlim.castle.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 11/10/11
 * Time: 5:27 PM
 *
 */

@Entity
@Table
public class Employee
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    /*
    @ManyToOne
    public Organization org;
    */

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    /*
    public Organization getOrg()
    {
        return org;
    }

    public void setOrg(Organization org)
    {
        this.org = org;
    }
    */

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
