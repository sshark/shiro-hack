package org.thlim.castle.dao.impl;

import org.thlim.castle.dao.PermissionDAO;
import org.thlim.castle.model.Permission;
import org.springframework.stereotype.Component;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 10/21/11
 * Time: 11:50 AM
 *
 */

@Component
public class DefaultPermissionDAO extends AbstractHibernateDAO<Permission> implements PermissionDAO
{

}
