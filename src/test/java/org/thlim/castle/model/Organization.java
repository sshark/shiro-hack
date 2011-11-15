package org.thlim.castle.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 11/10/11
 * Time: 5:25 PM
 *
 */

@Entity
public class Organization
{
    @OneToMany
    @JoinColumn(name = "org_id")
    private List<Employee> employees;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public List<Employee> getEmployees()
    {
        return employees;
    }

    public void setEmployees(List<Employee> employees)
    {
        this.employees = employees;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
