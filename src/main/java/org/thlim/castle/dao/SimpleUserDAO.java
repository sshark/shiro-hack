package org.thlim.castle.dao;

import org.thlim.castle.model.SimpleUser;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 22/10/11
 * Time: 7:48 PM
 *
 */
public interface SimpleUserDAO extends GenericPersistentDAO<SimpleUser>
{
    public SimpleUser findBy(String username);
}
