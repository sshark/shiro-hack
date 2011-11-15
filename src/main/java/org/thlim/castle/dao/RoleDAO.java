package org.thlim.castle.dao;

import org.thlim.castle.model.Role;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 10/21/11
 * Time: 11:53 AM
 *
 */
public interface RoleDAO extends GenericPersistentDAO<Role>
{
    Role findBy(String roleName);
}
