package org.thlim.castle.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.shiro.component.LoginPanel;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(HomePage.class);

    private String username;
    private String password;

    public HomePage(final PageParameters parameters) {
        Form loginForm = new Form("loginForm")
        {
            @Override
            protected void onSubmit()
            {
                LOG.debug(username + " with password '" + password + "' tries to log in.");
            }
        };
        add(loginForm);
        loginForm.add(new LoginPanel("loginPanel"));
        add(new FeedbackPanel("feedback"));
    }

}
