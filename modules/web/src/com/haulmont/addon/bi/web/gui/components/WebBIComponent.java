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

package com.haulmont.addon.bi.web.gui.components;

import com.haulmont.addon.bi.gui.components.BIComponent;
import com.haulmont.addon.bi.service.AuthTicketService;
import com.haulmont.addon.bi.web.toolkit.ui.CubaBIComponent;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.web.gui.components.WebAbstractComponent;

public class WebBIComponent
        extends WebAbstractComponent<CubaBIComponent>
        implements BIComponent {

    public WebBIComponent() {
        this.component = new CubaBIComponent();
        this.component.setAuthInfoProvider(new CubaBIComponent.AuthInfoProvider() {
            @Override
            public String getUserLogin() {
                UserSessionSource userSessionSource = AppBeans.get(UserSessionSource.NAME);
                return userSessionSource.getUserSession().getCurrentOrSubstitutedUser().getLogin();
            }

            @Override
            public String getUserTicket() {
                AuthTicketService authTicketService = AppBeans.get(AuthTicketService.NAME);
                return authTicketService.generateTicket();
            }
        });
    }

    @Override
    public String getServerUrl() {
        return component.getServerUrl();
    }

    @Override
    public void setServerUrl(String serverUrl) {
        component.setServerUrl(serverUrl);
    }

    @Override
    public String getReportPath() {
        return component.getReportPath();
    }

    @Override
    public void setReportPath(String reportPath) {
        component.setReportPath(reportPath);
    }

    @Override
    public boolean isEditorMode() {
        return component.isEditorMode();
    }

    @Override
    public void setEditorMode(boolean editorMode) {
        component.setEditorMode(editorMode);
    }
}
