package org.thlim.castle;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 23/10/11
 * Time: 3:58 PM
 *
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({TestAuthentication.class, TestAuthorization.class, TestSimpleUserLifeCycle.class})
public class AllTests {}
