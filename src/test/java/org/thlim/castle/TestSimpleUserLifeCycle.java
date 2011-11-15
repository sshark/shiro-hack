package org.thlim.castle;

import org.thlim.castle.dao.SimpleUserDAO;
import org.thlim.castle.model.SimpleUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 10/21/11
 * Time: 2:46 PM
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext.xml")
@Transactional
@TransactionConfiguration
public class TestSimpleUserLifeCycle
{
    @Autowired
    private SimpleUserDAO dao;



    @Test
    public void testCreateAndDeleteUser()
    {
        SimpleUser appleUser = new SimpleUser("apple");
        SimpleUser pcUser = new SimpleUser("pc");
        dao.makePersistent(appleUser);
        dao.makePersistent(pcUser);
        dao.delete(dao.findBy("apple"));
    }
}
