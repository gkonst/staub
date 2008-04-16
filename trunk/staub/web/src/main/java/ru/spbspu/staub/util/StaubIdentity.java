package ru.spbspu.staub.util;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.Install;
import static org.jboss.seam.annotations.Install.APPLICATION;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.security.Identity;

/**
 * Overrides default login rules.
 *
 * @author Konstantin Grigoriev
 */
@Name("org.jboss.seam.security.identity")
@Scope(SESSION)
@Install(precedence = APPLICATION)
@BypassInterceptors
@Startup
public class StaubIdentity extends Identity {

    private static final long serialVersionUID = -7094748065591632097L;

    @Override
    public String login() {
        unAuthenticate();
        return super.login();
    }

}
