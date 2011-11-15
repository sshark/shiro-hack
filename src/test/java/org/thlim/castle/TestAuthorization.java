package org.thlim.castle;

import org.thlim.castle.dao.PermissionDAO;
import org.thlim.castle.dao.RoleDAO;
import org.thlim.castle.dao.UserDAO;
import org.thlim.castle.model.Permission;
import org.thlim.castle.model.Role;
import org.thlim.castle.model.RolesGroup;
import org.thlim.castle.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 10/21/11
 * Time: 4:22 PM
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext.xml")
@Transactional

//uncomment @TransactionConfiguration to see transactional data in database.
//@TransactionConfiguration(defaultRollback = false)
public class TestAuthorization
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TestAuthorization.class);

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private PermissionDAO permissionDAO;

    @Autowired
    private UserDAO userDAO;


    private final String DOD = "DOD";

    // projects Alpha and Beta are DOD projects
    private final String PROJECT_ALPHA = "PROJECT ALPHA";
    private final String PROJECT_BETA = "PROJECT BETA";

    private final String PLATFORM_ADMIN = "Platform Admin";
    private final String AGENCY_ADMIN = "Agency Admin";
    private final String AGENCY_OWNER = "Agency Owner";
    private final String PROJECT_ADMIN = "Project Admin";
    private final String PROJECT_OWNER = "Project Owner";

    @Before
    public void setupRolesAndPermissions()
    {
        Permission platformAll = new Permission("*");
        permissionDAO.makePersistent(platformAll);

        Permission agencyEdit = new Permission("agency:edit");
        Permission agencyView = new Permission("agency:view");
        Permission agencyAssignAdmin = new Permission("agency:assign_admin");
        permissionDAO.makePersistent(agencyEdit);
        permissionDAO.makePersistent(agencyView);
        permissionDAO.makePersistent(agencyAssignAdmin);

        Permission projectAll = new Permission("project:*");
        Permission projectView = new Permission("project:view");
        Permission projectEdit = new Permission("project:edit");
        Permission projectAssignAdmin = new Permission("project:assign_admin");
        Permission projectAssignUser = new Permission("project:assign_user");
        Permission projectSetBudget = new Permission("project:set_budget");
        permissionDAO.makePersistent(projectAll);
        permissionDAO.makePersistent(projectView);
        permissionDAO.makePersistent(projectEdit);
        permissionDAO.makePersistent(projectAssignAdmin);
        permissionDAO.makePersistent(projectAssignUser);
        permissionDAO.makePersistent(projectSetBudget);

        Permission vmApprove = new Permission("vm:approve");
        Permission vmStart = new Permission("vm:start");
        Permission vmStop = new Permission("vm:stop");
        permissionDAO.makePersistent(vmApprove);
        permissionDAO.makePersistent(vmStart);
        permissionDAO.makePersistent(vmStop);

        Role platformAdmin = new Role(PLATFORM_ADMIN);
        platformAdmin.add(platformAll);
        roleDAO.makePersistent(platformAdmin);

        Role agencyOwner = new Role(AGENCY_OWNER);
        agencyOwner.add(agencyAssignAdmin);
        agencyOwner.add(agencyView);
        agencyOwner.add(agencyEdit);
        roleDAO.makePersistent(agencyOwner);

        Role agencyAdmin = new Role(AGENCY_ADMIN);
        agencyAdmin.add(agencyView);
        roleDAO.makePersistent(agencyAdmin);

        Role projectOwner = new Role(PROJECT_OWNER);
        projectOwner.add(projectView);
        projectOwner.add(projectAssignAdmin);
        projectOwner.add(projectAssignUser);
        projectOwner.add(projectEdit);
        projectOwner.add(projectSetBudget);
        roleDAO.makePersistent(projectOwner);

        Role projectAdmin = new Role(PROJECT_ADMIN);
        projectAdmin.add(projectAssignUser);
        projectAdmin.add(projectView);
        roleDAO.makePersistent(projectAdmin);
    }

    @Test
    public void testAssignedRolesAndPermissions()
    {
        User alice = new User("alice", new Sha256Hash("alice").toHex(), "alice@cloud.com", "alice is agency owner.");
        User bob = new User("bob", new Sha256Hash("bob").toHex(), "bob@cloud.com", "Bob is agency administrator.");
        User cathy = new User("cathy", new Sha256Hash("cathy").toHex(), "cathy@cloud.com", "Cathy is project owner.");

        Role projectAdmin = roleDAO.findBy(PROJECT_ADMIN);
        Role projectOwner = roleDAO.findBy(PROJECT_OWNER);
        Role agencyAdmin = roleDAO.findBy(AGENCY_ADMIN);
        Role agencyOwner = roleDAO.findBy(AGENCY_OWNER);
        Role platformAdmin = roleDAO.findBy(PLATFORM_ADMIN);

        RolesGroup aliceProjectAlphaRolesGroup = RolesGroup.singleRoleProject(PROJECT_ALPHA, projectAdmin);
        RolesGroup aliceProjectBetaRolesGroup = RolesGroup.singleRoleProject(PROJECT_BETA, projectOwner);
        alice.assignRolesGroupToInstance(aliceProjectAlphaRolesGroup);
        alice.assignRolesGroupToInstance(aliceProjectBetaRolesGroup);

        RolesGroup bobDODRolesGroup = RolesGroup.singleRoleProject(DOD, agencyAdmin);
        RolesGroup bobProjectAlphaRolesGroup = RolesGroup.singleRoleProject(PROJECT_ALPHA, projectOwner);
        RolesGroup bobProjectBetaRolesGroup = RolesGroup.singleRoleProject(PROJECT_BETA, projectOwner);
        bob.assignRolesGroupToInstance(bobDODRolesGroup);
        bob.assignRolesGroupToInstance(bobProjectAlphaRolesGroup);
        bob.assignRolesGroupToInstance(bobProjectBetaRolesGroup);

        RolesGroup cathyRolesGroup = RolesGroup.singleRoleProject("*", platformAdmin);
        cathy.assignRolesGroupToInstance(cathyRolesGroup);

        Assert.assertTrue(alice.getGrantedRoles().contains("Project Admin:PROJECT ALPHA"));
        Assert.assertTrue(alice.getGrantedRoles().contains("Project Owner:PROJECT BETA"));
        Assert.assertFalse(alice.getGrantedRoles().contains("Project Owner:PROJECT GAMMA"));
        Assert.assertTrue(alice.getGrantedPermissions().contains("project:assign_user:" + PROJECT_ALPHA));
        Assert.assertTrue(alice.getGrantedPermissions().contains("project:view:" + PROJECT_ALPHA));
        Assert.assertFalse(alice.getGrantedPermissions().contains("project:delete:" + PROJECT_ALPHA));

        userDAO.makePersistent(alice);
        userDAO.makePersistent(bob);
        userDAO.makePersistent(cathy);

        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken("alice", "alice"));

        Assert.assertTrue(subject.hasRole("Project Admin:PROJECT ALPHA"));
        Assert.assertTrue(subject.hasRole("Project Owner:PROJECT BETA"));
        Assert.assertFalse(subject.hasRole("Project Owner:PROJECT GAMMA"));
        Assert.assertTrue(subject.isPermitted("project:view:" + PROJECT_ALPHA));
        Assert.assertFalse(subject.isPermitted("project:assign_admin:" + PROJECT_ALPHA));
        Assert.assertTrue(subject.isPermitted("project:view:" + PROJECT_BETA));
        Assert.assertTrue(subject.isPermitted("project:assign_admin:" + PROJECT_BETA));
        Assert.assertFalse(subject.isPermitted("agency:delete" + DOD));
        subject.logout();

        subject.login(new UsernamePasswordToken("bob", "bob"));
        Assert.assertTrue(subject.hasRole("Project Owner:PROJECT ALPHA"));
        Assert.assertTrue(subject.hasRole("Project Owner:PROJECT BETA"));
        Assert.assertFalse(subject.hasRole("Project Owner:PROJECT GAMMA"));
        Assert.assertTrue(subject.isPermitted("project:view:" + PROJECT_ALPHA));
        Assert.assertTrue(subject.isPermitted("project:assign_admin:" + PROJECT_ALPHA));
        Assert.assertFalse(subject.isPermitted("project:delete:" + PROJECT_ALPHA));
        Assert.assertTrue(subject.isPermitted("project:view:" + PROJECT_BETA));
        Assert.assertTrue(subject.isPermitted("project:assign_admin:" + PROJECT_BETA));
        Assert.assertFalse(subject.isPermitted("project:delete:" + PROJECT_BETA));
        Assert.assertFalse(subject.isPermitted("agency:delete" + DOD));
        subject.logout();

        subject.login(new UsernamePasswordToken("cathy", "cathy"));
        Assert.assertTrue(subject.isPermitted("project:assign_admin:" + PROJECT_ALPHA));
        Assert.assertTrue(subject.isPermitted("project:delete:" + PROJECT_ALPHA));
        Assert.assertTrue(subject.isPermitted("project:delete:" + PROJECT_BETA));
        Assert.assertTrue(subject.isPermitted("agency:delete" + DOD));
        subject.logout();
    }
}
