package org.thlim.castle.dao.impl;

import org.thlim.castle.dao.SimpleUserDAO;
import org.thlim.castle.model.SimpleUser;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 10/21/11
 * Time: 11:50 AM
 *
 */

@Component
public class DefaultSimpleUserDAO extends AbstractHibernateDAO<SimpleUser> implements SimpleUserDAO
{
    public SimpleUser findBy(String username)
    {
        return (SimpleUser) getSessionFactory().getCurrentSession().createCriteria(getClazz())
            .add(Restrictions.eq("name", username)).uniqueResult();
    }
}
