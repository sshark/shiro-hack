package org.thlim.castle.dao.impl;

import java.util.HashSet;
import java.util.Set;

import org.thlim.castle.dao.UserDAO;
import org.thlim.castle.model.User;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 10/13/11
 * Time: 2:10 PM
 *
 */

@Component
public class DefaultUserDAO extends AbstractHibernateDAO<User> implements UserDAO
{
    /**
     * Used for login purpose.
     *
     * @param username
     * @param password
     * @return User if username is found or null if password is wrong or user not registered
     */
    @Override
    public User findBy(String username, String password)
    {
        /*
        return (User) DataAccessUtils.uniqueResult(getHibernateTemplate()
            .findByCriteria(DetachedCriteria.forClass(User.class)
                .add(Restrictions.eq("username", username))
                .add(Restrictions.eq("password", new Sha256Hash(password).toString()))));
        */


        return (User) getSessionFactory().getCurrentSession().createCriteria(User.class)
            .add(Restrictions.eq("username", username))
            .add(Restrictions.eq("password", new Sha256Hash(password).toString()))
            .uniqueResult();
    }

    @Override
    public User findBy(String username)
    {
        return (User) getSessionFactory().getCurrentSession().createCriteria(User.class)
            .add(Restrictions.eq("username", username))
            .uniqueResult();
    }

    @Override
    public Set<User> findUsersLike(String username)
    {
        return new HashSet<User>(getSessionFactory().getCurrentSession().createCriteria(User.class)
            .add(Restrictions.like("username", username)).list());
    }

}
