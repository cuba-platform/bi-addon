/*
 * Copyright (c) 2008-2019 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haulmont.addon.bi.pentaho;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.pentaho.platform.api.engine.security.userroledao.IPentahoRole;
import org.pentaho.platform.api.engine.security.userroledao.IPentahoUser;
import org.pentaho.platform.api.engine.security.userroledao.IUserRoleDao;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class CubaPentahoAuthenticationFilter extends GenericFilterBean implements ApplicationEventPublisherAware {

    private static final String AUTO_LOGIN_PARAM_NAME = "autoLogin";
    private static final String TICKET_PARAM_NAME = "ticket";
    private static final String USERNAME_PARAM_NAME = "username";
    private static final Log log = LogFactory.getLog(CubaPentahoAuthenticationFilter.class);
    protected IUserRoleDao userRoleDao;
    protected AuthenticationManager authenticationManager;
    protected List extraRoles;
    protected ApplicationEventPublisher applicationEventPublisher;
    protected String cubaConnectionUrl;

    public void setUserRoleDao(IUserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void setExtraRoles(List extraRoles) {
        this.extraRoles = extraRoles;
    }

    public void setCubaConnectionUrl(String cubaConnectionUrl) {
        this.cubaConnectionUrl = cubaConnectionUrl;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("CubaPentahoAuthenticationFilter just supports HTTP requests");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (!needIgnoreRequest(request)) {
            try {
                String ticketId = httpRequest.getParameter(TICKET_PARAM_NAME);
                if (StringUtils.isEmpty(ticketId)) {
                    throw new IllegalArgumentException(format("The required parameter %s is not set", TICKET_PARAM_NAME));
                }
                String userName = httpRequest.getParameter(USERNAME_PARAM_NAME);
                if (StringUtils.isEmpty(userName)) {
                    throw new IllegalArgumentException(format("The required parameter %s is not set", USERNAME_PARAM_NAME));
                }
                testTicket(ticketId, userName);
                authenticateUser(userName, httpRequest);
                if (isSaiku(httpRequest.getRequestURI())) {
                    chain.doFilter(request, response);
                } else {
                    String newUrl = httpRequest.getRequestURL() + "?" + httpRequest.getQueryString();
                    newUrl = newUrl.replace(AUTO_LOGIN_PARAM_NAME + "=true", "");
                    httpResponse.sendRedirect(newUrl);
                }
            } catch (Exception e) {
                log.error("An exception occurred during the authentication process", e);
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    protected void testTicket(String ticketId, String userName) throws IOException {
        HttpClient httpClient = new HttpClient();
        GetMethod httpGet = new GetMethod(getAuthUrl(ticketId, userName));
        try {
            int status = httpClient.executeMethod(httpGet);
            if (HttpStatus.SC_OK != status) {
                throw new IllegalStateException(
                        format("Auth error by ticket %s for user %s ", ticketId, userName));
            }
            JSONObject authJson = new JSONObject(httpGet.getResponseBodyAsString());
            boolean authenticated = authJson.optBoolean("authenticated");
            if (!authenticated) {
                throw new IllegalStateException(
                        format("Auth error by ticket %s for user %s ", ticketId, userName));
            }
        } finally {
            httpGet.releaseConnection();
        }
    }

    protected void authenticateUser(String userName, HttpServletRequest request) {
        IPentahoUser user = userRoleDao.getUser(null, userName);
        if (user == null) {
            throw new IllegalStateException(
                    format("User '%s' not found in the current system using the default UserRoleDao bean", userName));
        }
        List<IPentahoRole> roles = userRoleDao.getUserRoles(null, userName);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (IPentahoRole role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        if (extraRoles != null) {
            for (Object role : extraRoles) {
                authorities.add(new SimpleGrantedAuthority((String) role));
            }
        }
        CubaAuthenticationToken authRequestToken = new CubaAuthenticationToken(userName, null,
                authorities);
        authRequestToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authResult = authenticationManager.authenticate(authRequestToken);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        if (applicationEventPublisher != null) {
            applicationEventPublisher.publishEvent(new AuthenticationSuccessEvent(authResult));
        }
    }

    protected boolean needIgnoreRequest(ServletRequest request) {
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuthentication != null && currentAuthentication.isAuthenticated()) {
            return true;
        }
        String autoLogin = request.getParameter(AUTO_LOGIN_PARAM_NAME);
        return !"true".equals(autoLogin);
    }

    protected String getAuthUrl(String ticketId, String userName) {
        String serverUrl = cubaConnectionUrl;
        if (serverUrl.endsWith("/")) {
            serverUrl = serverUrl.substring(0, serverUrl.length() - 1);
        }
        return format("%s/dispatch/authBI?ticket=%s&username=%s", serverUrl, ticketId, userName);
    }

    protected boolean isSaiku(String requestUrl) {
        return requestUrl.contains("/saiku-ui/");
    }
}
