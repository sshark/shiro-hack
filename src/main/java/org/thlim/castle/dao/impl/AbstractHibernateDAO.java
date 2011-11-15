package org.thlim.castle.dao.impl;

import java.lang.reflect.ParameterizedType;

import org.thlim.castle.dao.GenericPersistentDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 10/21/11
 * Time: 11:32 AM
 *
 */

public class AbstractHibernateDAO<T> implements GenericPersistentDAO<T>
{
    @Autowired
    private SessionFactory sessionFactory;

    private Class<T> clazz;

    public AbstractHibernateDAO()
    {
        this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    public Class<T> getClazz()
    {
        return clazz;
    }

    public T findBy(Long oid)
    {
        return (T) sessionFactory.getCurrentSession().get(clazz, oid);
    }

    public void makePersistent(T instance)
    {
        sessionFactory.getCurrentSession().save(instance);
        //getHibernateTemplate().save(instance);
    }

    public void deleteBy(Long oid)
    {
        delete(findBy(oid));
    }

    public void delete(T instance)
    {
        sessionFactory.getCurrentSession().delete(instance);
    }

    /*
    public void deleteAll()
    {
        sessionFactory.getCurrentSession().deleteAll(sessionFactory.getCurrentSession().load(clazz));
    }
    */

    public void update(T instance)
    {
        sessionFactory.getCurrentSession().update(instance);
    }

    /*
    public Collection<T> findAll()
    {
        return sessionFactory.getCurrentSession().load().loadAll(clazz);
    }
    */

    public void flush()
    {
        sessionFactory.getCurrentSession().flush();
    }

    public void clear()
    {
        sessionFactory.getCurrentSession().clear();
    }
}
