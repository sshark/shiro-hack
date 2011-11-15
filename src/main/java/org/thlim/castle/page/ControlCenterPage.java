package org.thlim.castle.page;

import org.thlim.castle.model.User;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.Model;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

/**
 *
 * User: Lim, Teck Hooi
 * Date: 10/13/11
 * Time: 11:18 AM
 *
 */

@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class ControlCenterPage extends WebPage
{
    public ControlCenterPage()
    {
        final RequiredTextField<String> username = new RequiredTextField<String>("username", new Model<String>());
        Form<User> userForm = new Form<User>("userForm")
        {
            @Override
            protected void onSubmit()
            {
                super.onSubmit();
            }
        };
        add(userForm);
        userForm.add(username);

        final RequiredTextField<String> role = new RequiredTextField<String>("role", new Model<String>());
        Form<User> roleForm = new Form<User>("roleForm")
        {
            @Override
            protected void onSubmit()
            {
                super.onSubmit();
            }
        };
        add(roleForm);
        roleForm.add(role);

        final RequiredTextField<String> project = new RequiredTextField<String>("projectForm", new Model<String>());
        Form<User> projectForm = new Form<User>("projectForm")
        {
            @Override
            protected void onSubmit()
            {
                super.onSubmit();
            }
        };
        add(projectForm);
        projectForm.add(project);

    }
}
