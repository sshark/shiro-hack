package org.thlim.castle.dao.impl;

import org.thlim.castle.dao.OrgDAO;
import org.thlim.castle.model.Organization;
import org.springframework.stereotype.Component;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 11/10/11
 * Time: 5:30 PM
 *
 */
@Component
public class DefaultOrgDAO extends AbstractHibernateDAO<Organization> implements OrgDAO
{
}
