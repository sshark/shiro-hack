package org.thlim.castle;

import org.thlim.castle.dao.UserDAO;
import org.thlim.castle.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 10/18/11
 * Time: 4:22 PM
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext.xml")
@Transactional
public class TestAuthentication
{
    private User alice;
    private User bob;
    private User cathy;
    private User dennis;
    private User elaine;

    @Autowired
    private UserDAO userDAO;

    @Before
    public void setupUsers()
    {
        alice = new User("alice", new Sha256Hash("alice").toHex(), "alice@cloud.com", "Alice is agency owner.");
        bob = new User("bob", new Sha256Hash("bob").toHex(), "bob@cloud.com", "Bob is agency administrator.");
        cathy = new User("cathy", new Sha256Hash("cathy").toHex(), "cathy@cloud.com", "Cathy is project owner.");
        dennis = new User("dennis", new Sha256Hash("dennis").toHex(), "dennis@cloud.com", "Dennis is project administrator.");
        elaine = new User("elaine", new Sha256Hash("elaine").toHex(), "elaine@cloud.com", "Elaine is project administrator.");

        userDAO.makePersistent(alice);
        userDAO.makePersistent(bob);
        userDAO.makePersistent(cathy);
        userDAO.makePersistent(dennis);
        userDAO.makePersistent(elaine);

        //Assert.assertEquals(5, userDAO.findAll().size());
    }

    @After
    public void removeUsers()
    {
        userDAO.delete(alice);
        userDAO.delete(bob);
        userDAO.delete(cathy);
        userDAO.delete(dennis);
        userDAO.delete(elaine);

        //Assert.assertEquals(0, userDAO.findAll().size());
    }

    @Test
    public void testLogin()
    {
        SecurityUtils.getSubject().login(new UsernamePasswordToken("alice", "alice"));
        SecurityUtils.getSubject().login(new UsernamePasswordToken("bob", "bob"));
        SecurityUtils.getSubject().login(new UsernamePasswordToken("cathy", "cathy"));
        SecurityUtils.getSubject().login(new UsernamePasswordToken("dennis", "dennis"));
        SecurityUtils.getSubject().login(new UsernamePasswordToken("elaine", "elaine"));
    }

    @Test(expected = AuthenticationException.class)
    public void testFailedLogin()
    {
        SecurityUtils.getSubject().login(new UsernamePasswordToken("bob", "bob#"));
    }
}
