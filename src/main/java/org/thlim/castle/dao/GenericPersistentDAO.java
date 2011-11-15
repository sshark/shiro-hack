package org.thlim.castle.dao;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 10/13/11
 * Time: 2:08 PM
 *
 */
public interface GenericPersistentDAO<T>
{
    void makePersistent(T instance);
    T findBy(Long oid);
    //Collection<T> findAll();
    void update(T instance);
    void deleteBy(Long oid);
    void delete(T instance);
    //void deleteAll();
    void flush();
    void clear();
}
