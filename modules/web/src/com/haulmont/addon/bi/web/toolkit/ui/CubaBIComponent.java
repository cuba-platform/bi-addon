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

package com.haulmont.addon.bi.web.toolkit.ui;

import com.haulmont.addon.bi.util.BiUtil;
import com.haulmont.addon.bi.web.toolkit.ui.client.CubaBIComponentState;
import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import org.springframework.web.util.UriUtils;

import java.util.Base64;

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
                reportUrl = String.format("%s/content/saiku-ui/index.html?" +
                                "username=%s&ticket=%s&autoLogin=true&biplugin5=true&MODE=%s&DEFAULT_VIEW_STATE=%s" +
                                "&CUBA_VIEW_STATE=%s&dimension_prefetch=false&hide_workspace_icons=true#query/open/%s",
                        serverUrl,
                        authInfoProvider == null ? "" : authInfoProvider.getUserLogin(),
                        authInfoProvider == null ? "" : authInfoProvider.getUserTicket(),
                        editorMode ? "VIEW" : "view",
                        editorMode ? "EDIT" : "VIEW",
                        editorMode ? "EDIT" : "VIEW",
                        UriUtils.encodePathSegment(reportPath, "UTF-8"));
            } else if (BiUtil.isDatafor(reportPath)) {
                if (editorMode) {
                    reportUrl = String.format("%s/plugin/datafor/api/open/%s?username=%s&ticket=%s&autoLogin=true&CUBA_VIEW_STATE=%s",
                            serverUrl,
                            Base64.getEncoder().encodeToString(reportPath.replace(":", "/").getBytes()),
                            authInfoProvider == null ? "" : authInfoProvider.getUserLogin(),
                            authInfoProvider == null ? "" : authInfoProvider.getUserTicket(),
                            "EDIT");
                } else {
                    reportUrl = String.format("%s/plugin/datafor/api/integrate/%s?username=%s&ticket=%s&autoLogin=true&CUBA_VIEW_STATE=%s",
                            serverUrl,
                            Base64.getEncoder().encodeToString(reportPath.replace(":", "/").getBytes()),
                            authInfoProvider == null ? "" : authInfoProvider.getUserLogin(),
                            authInfoProvider == null ? "" : authInfoProvider.getUserTicket(),
                            "VIEW");
                }
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
