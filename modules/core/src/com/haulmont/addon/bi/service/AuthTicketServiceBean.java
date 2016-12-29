package com.haulmont.addon.bi.service;

import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.core.global.UuidProvider;
import com.haulmont.cuba.security.app.UserSessions;
import com.haulmont.cuba.security.global.UserSession;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service(AuthTicketService.NAME)
public class AuthTicketServiceBean implements AuthTicketService {
    @Inject
    protected UserSessions userSessions;
    @Inject
    protected UserSessionSource userSessionSource;

    protected static final String BI_AUTH_TICKET_ATTRIBUTE = "biAuthTicket";

    @Override
    public String generateTicket() {
        UserSession userSession = userSessionSource.getUserSession();
        String ticket = userSession.getAttribute(BI_AUTH_TICKET_ATTRIBUTE);
        if (ticket == null) {
            ticket = UuidProvider.createUuid().toString();
            userSession.setAttribute(BI_AUTH_TICKET_ATTRIBUTE, ticket);
        }
        return ticket;
    }

    @Override
    public boolean isTicketGenerated(String ticket, String username) {
        List<UUID> sessionIds = userSessions.findUserSessionsByAttribute(BI_AUTH_TICKET_ATTRIBUTE, ticket);
        if (sessionIds.isEmpty() || sessionIds.size() > 1) {
            return false;
        }
        UserSession userSession = userSessions.get(sessionIds.get(0), false);
        return userSession != null && Objects.equals(userSession.getCurrentOrSubstitutedUser().getLogin(), username);
    }
}