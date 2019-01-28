/*
 * TODO Copyright
 */

package com.haulmont.addon.bi.web.toolkit.ui;

import com.haulmont.addon.bi.util.BiUtil;
import com.haulmont.addon.bi.web.toolkit.ui.client.CubaBIComponentState;
import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import org.springframework.web.util.UriUtils;

@JavaScript({"vaadin://resources/cubabi/pentahoComponent.js", "vaadin://resources/cubabi/pentahoComponent-connector.js"})
public class CubaBIComponent extends AbstractJavaScriptComponent {
    protected String serverUrl;
    protected String reportPath;
    protected boolean editorMode = true;
    protected AuthInfoProvider authInfoProvider;

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

    public void setAuthInfoProvider(AuthInfoProvider authInfoProvider) {
        this.authInfoProvider = authInfoProvider;
    }

    protected String getReportUrl() {
        if (serverUrl != null && serverUrl.endsWith("/")) {
            serverUrl = serverUrl.substring(0, serverUrl.length() - 1);
        }

        String reportUrl = null;
        if (reportPath != null) {
            if (BiUtil.isPentaho(reportPath)) {
                reportUrl = String.format("%s/api/repos/%s/%s?disableFilterPanel=true&username=%s&ticket=%s&autoLogin=true",
                        serverUrl,
                        UriUtils.encodePathSegment(reportPath, "UTF-8"),
                        editorMode ? "editor" : "viewer",
                        authInfoProvider == null ? "" : authInfoProvider.getUserLogin(),
                        authInfoProvider == null ? "" : authInfoProvider.getUserTicket());
                if (editorMode) {
                    reportUrl = reportUrl.concat("&removeFieldList=true");
                }
            } else if (BiUtil.isSaiku(reportPath)) {
                reportUrl = String.format("%s/content/saiku-ui/index.html?username=%s&ticket=%s&autoLogin=true&biplugin5=true&MODE=%s&DEFAULT_VIEW_STATE=%s" +
                                "&CUBA_VIEW_STATE=%s&dimension_prefetch=false&hide_workspace_icons=true#query/open/%s",
                        serverUrl,
                        authInfoProvider == null ? "" : authInfoProvider.getUserLogin(),
                        authInfoProvider == null ? "" : authInfoProvider.getUserTicket(),
                        editorMode ? "VIEW" : "view",
                        editorMode ? "EDIT" : "VIEW",
                        editorMode ? "EDIT" : "VIEW",
                        UriUtils.encodePathSegment(reportPath, "UTF-8"));
            }
        }
        return reportUrl;
    }

    @Override
    protected CubaBIComponentState getState() {
        return (CubaBIComponentState) super.getState();
    }

    @Override
    protected CubaBIComponentState getState(boolean markAsDirty) {
        return (CubaBIComponentState) super.getState(markAsDirty);
    }

    public interface AuthInfoProvider {
        String getUserLogin();

        String getUserTicket();
    }
}
