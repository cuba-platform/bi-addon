/*
 * TODO Copyright
 */

package com.haulmont.addon.bi.web.toolkit.ui;

import com.haulmont.addon.bi.service.AuthTicketService;
import com.haulmont.addon.bi.web.toolkit.ui.client.CubaBIComponentState;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;

@JavaScript({"vaadin://resources/cubabi/pentahoComponent.js", "vaadin://resources/cubabi/pentahoComponent-connector.js"})
public class CubaBIComponent extends AbstractJavaScriptComponent {
    protected String serverUrl;
    protected String reportPath;
    protected boolean editorMode = true;

    public CubaBIComponent() {
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        getState().reportUrl = getReportUrl();
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
        getState().reportUrl = getReportUrl();
    }

    public boolean isEditorMode() {
        return editorMode;
    }

    public void setEditorMode(boolean editorMode) {
        this.editorMode = editorMode;
        getState().reportUrl = getReportUrl();
    }

    protected String getReportUrl() {
        if (serverUrl != null && serverUrl.endsWith("/")) {
            serverUrl = serverUrl.substring(0, serverUrl.length() - 1);
        }
        try {
            UserSessionSource userSessionSource = AppBeans.get(UserSessionSource.NAME);
            AuthTicketService authTicketService = AppBeans.get(AuthTicketService.NAME);
            String reportUrl = String.format("%s/api/repos/%s/%s?disableFilterPanel=true&username=%s&ticket=%s&autoLogin=true",
                    serverUrl,
                    reportPath == null ? null : UriUtils.encodePathSegment(reportPath, "UTF-8"),
                    editorMode ? "editor" : "viewer",
                    userSessionSource.getUserSession().getCurrentOrSubstitutedUser().getLogin(),
                    authTicketService.generateTicket());
            if (editorMode) {
                reportUrl = reportUrl.concat("&removeFieldList=true");
            }
            return reportUrl;
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Unsupported encoding", e);
        }
    }

    @Override
    protected CubaBIComponentState getState() {
        return (CubaBIComponentState) super.getState();
    }

    @Override
    protected CubaBIComponentState getState(boolean markAsDirty) {
        return (CubaBIComponentState) super.getState(markAsDirty);
    }
}
