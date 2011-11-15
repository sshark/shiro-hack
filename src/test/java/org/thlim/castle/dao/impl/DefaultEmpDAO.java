package org.thlim.castle.dao.impl;

import org.thlim.castle.dao.EmpDAO;
import org.thlim.castle.model.Employee;
import org.springframework.stereotype.Component;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 11/10/11
 * Time: 5:30 PM
 *
 */

@Component
public class DefaultEmpDAO extends AbstractHibernateDAO<Employee> implements EmpDAO
{
}
