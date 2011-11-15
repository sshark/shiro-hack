package org.thlim.castle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 10/21/11
 * Time: 2:45 PM
 *
 */
@Entity
public class SimpleUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "oid")
    private Long oid;

    private String name;

    public SimpleUser()
    {
    }

    public SimpleUser(String name)
    {
        this.name = name;
    }

    public Long getOid()
    {
        return oid;
    }

    public void setOid(Long oid)
    {
        this.oid = oid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
