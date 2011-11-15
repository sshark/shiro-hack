package org.thlim.castle.dao;

import java.util.Set;

import org.thlim.castle.model.User;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 10/13/11
 * Time: 2:08 PM
 *
 */
public interface UserDAO extends GenericPersistentDAO<User>
{
    User findBy(String username, String password);
    User findBy(String username);
    Set<User> findUsersLike(String username);
}
