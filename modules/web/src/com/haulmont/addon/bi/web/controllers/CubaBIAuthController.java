/*
 * TODO Copyright
 */

package com.haulmont.addon.bi.web.controllers;

import com.haulmont.addon.bi.service.AuthTicketService;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.core.sys.SecurityContext;
import com.haulmont.cuba.security.app.LoginService;
import com.haulmont.cuba.security.global.LoginException;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.cuba.web.auth.WebAuthConfig;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class CubaBIAuthController {
    protected final static Logger log = LoggerFactory.getLogger(CubaBIAuthController.class);
    @Inject
    protected LoginService loginService;
    @Inject
    protected AuthTicketService authTicketService;
    @Inject
    protected WebAuthConfig webAuthConfig;

    @RequestMapping(value = "/authBI", method = RequestMethod.GET)
    public void authPentaho(HttpServletResponse response,
                            @RequestParam(value = "ticket") String ticket,
                            @RequestParam(value = "username") String username) throws IOException {
        String trustedClientPassword = webAuthConfig.getTrustedClientPassword();
        UserSession systemSession;
        PrintWriter responseWriter = response.getWriter();
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            systemSession = loginService.getSystemSession(trustedClientPassword);
        } catch (LoginException e) {
            log.error("Unable to login with trusted client password", e);
            responseWriter.print(generateAuthJson(false));
            return;
        }
        AppContext.setSecurityContext(new SecurityContext(systemSession.getId()));
        try {
            if (authTicketService.isTicketGenerated(ticket, username)) {
                responseWriter.print(generateAuthJson(true));
                return;
            }
        } catch (Exception e) {
            log.error("Unable to verify ticket", e);
            responseWriter.print(generateAuthJson(false));
        } finally {
            AppContext.setSecurityContext(null);
        }
    }

    protected String generateAuthJson(boolean authResult) {
        JSONObject authJson = new JSONObject();
        authJson.putOpt("authenticated", authResult);
        return authJson.toString();
    }
}
