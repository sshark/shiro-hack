/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.thlim.castle.dao;

import javax.sql.DataSource;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Loads sample data for the sample app since it's an in-memory database.
 */
@Component
public class BootstrapDataPopulator implements InitializingBean
{

	private DataSource dataSource;
	private SessionFactory sessionFactory;

	@Autowired
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	// Session factory is only injected to ensure it is initialized before this runs
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

    enum PermittedType { agency, project, vm, storage, user, role }

    enum PermittedAction { create, edit, delete, retrieve, approve, start, stop }

    private String getWildcardPermissionString(PermittedType permittedType, PermittedAction permittedAction, String instanceId){
        return permittedType.toString() + ":" + permittedAction.toString() + ":" + instanceId;
    }

	public void afterPropertiesSet() throws Exception
	{
		// because we're using an in-memory hsqldb for the sample app, a new one will be created
// each time the
		// app starts, so insert the sample admin user at startup:
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.execute("insert into role(id,name,description) values (1, 'platform admin', 'Overall admin for all levels')");
		jdbcTemplate.execute("insert into role(id,name,description) values (2, 'agency admin', 'Overall admin for an agency')");
        jdbcTemplate.execute("insert into role(id,name,description) values (3, 'agency owner', 'Owns an agency.')");
        jdbcTemplate.execute("insert into role(id,name,description) values (4, 'project admin', 'Admin for project.')");
        jdbcTemplate.execute("insert into role(id,name,description) values (5, 'project owner', 'Owns a project.')");
        jdbcTemplate.execute("insert into role(id,name,description) values (6, 'operator', 'Operators for project.')");
		jdbcTemplate.execute("insert into permission values (1, '*')");
		jdbcTemplate.execute("insert into permission values (2, 'agency:*')");
		jdbcTemplate.execute("insert into permission values (3, 'agency:view')");
		jdbcTemplate.execute("insert into permission values (4, 'agency:edit')");
		jdbcTemplate.execute("insert into permission values (5, 'agency:create')");
		jdbcTemplate.execute("insert into permission values (6, 'agency:delete')");
		jdbcTemplate.execute("insert into permission values (7, 'user:create')");
		jdbcTemplate.execute("insert into permission values (8, 'role:create')");

        // project permissions created on the fly
		jdbcTemplate.execute("insert into permission values (9, 'project:*')");
        jdbcTemplate.execute("insert into permission values (10, 'project:create')");
		jdbcTemplate.execute("insert into permission values (11, 'project:view')");
		jdbcTemplate.execute("insert into permission values (12, 'project:edit')");
		jdbcTemplate.execute("insert into permission values (13, 'project:assign')");
		jdbcTemplate.execute("insert into permission values (14, 'project:delete')");
		jdbcTemplate.execute("insert into permission values (15, 'vm:delete')");
		jdbcTemplate.execute("insert into permission values (16, 'vm:stop')");
		jdbcTemplate.execute("insert into permission values (17, 'vm:start')");

        jdbcTemplate.execute("insert into role_permission values (1, 1)");
		jdbcTemplate.execute("insert into role_permission values (2, 2)");
		jdbcTemplate.execute("insert into user(id,username,email,password) values (1, 'alice', 'sample@shiro.apache.org', '" +
			new Sha256Hash("alice").toHex() + "')");
		jdbcTemplate.execute("insert into user(id,username,email,password) values (2, 'bob', 'sample@shiro.apache.org', '" +
			new Sha256Hash("bob").toHex() + "')");
		jdbcTemplate.execute("insert into user_role values (1, 2)");
		jdbcTemplate.execute("insert into user_role values (2, 1)");

	}
}
