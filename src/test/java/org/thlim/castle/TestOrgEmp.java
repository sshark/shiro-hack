package org.thlim.castle;

import java.util.ArrayList;
import java.util.List;

import org.thlim.castle.dao.EmpDAO;
import org.thlim.castle.dao.OrgDAO;
import org.thlim.castle.model.Employee;
import org.thlim.castle.model.Organization;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 11/10/11
 * Time: 5:28 PM
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext.xml")
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class TestOrgEmp
{
    @Autowired
    private OrgDAO orgDAO;

    @Autowired
    private EmpDAO empDAO;

    @Before
    public void createOrgWithEmp()
    {
        Employee emp = new Employee();
        emp.setName("alice");


        Organization newOrg = new Organization();
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(emp);
        newOrg.setEmployees(employees);
        orgDAO.makePersistent(newOrg);
        empDAO.makePersistent(emp);
    }

    @Test
    public void loadOrgWithEmp()
    {
        // assumes there is always an organization record created with ID 1. This is used instead of using a variable
        // to store the latest ID is because of Hibernate cache. After the 1st run, it will load the record from the
        // database instead (presumption)
        Organization org = orgDAO.findBy(1l);
        Assert.assertEquals(1, org.getEmployees().size());
        Assert.assertEquals(1l, org.getEmployees().get(0).getId().longValue());
    }
}
