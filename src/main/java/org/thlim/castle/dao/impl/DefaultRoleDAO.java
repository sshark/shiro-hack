package org.thlim.castle.dao.impl;

import org.thlim.castle.dao.RoleDAO;
import org.thlim.castle.model.Role;
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
public class DefaultRoleDAO extends AbstractHibernateDAO<Role> implements RoleDAO
{


    @Override
    public Role findBy(String roleName)
    {
        /*
        return (Role) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(Role.class)
            .add(Restrictions.eq("name", roleName))));
        */

        return (Role) getSessionFactory().getCurrentSession().createCriteria(Role.class).add(Restrictions.eq("name", roleName)).uniqueResult();
    }
}
