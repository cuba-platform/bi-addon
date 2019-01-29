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

package com.haulmont.addon.bi.gui.xml.layout;

import com.haulmont.addon.bi.BIConfig;
import com.haulmont.addon.bi.gui.components.BIComponent;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Configuration;
import com.haulmont.cuba.gui.xml.layout.loaders.AbstractComponentLoader;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

public class BIComponentLoader extends AbstractComponentLoader<BIComponent> {
    @Override
    public void createComponent() {
        resultComponent = (BIComponent) factory.create(BIComponent.NAME);
        loadId(resultComponent, element);
    }

    @Override
    public void loadComponent() {
        assignFrame(resultComponent);
        assignXmlDescriptor(resultComponent, element);

        loadVisible(resultComponent, element);

        loadHeight(resultComponent, element);
        loadWidth(resultComponent, element);

        loadServerUrl(resultComponent, element);
        loadReportPath(resultComponent, element);
    }

    protected void loadServerUrl(BIComponent component, Element element) {
        String serverUrl = element.attributeValue("serverUrl");
        if (StringUtils.isNotEmpty(serverUrl)) {
            component.setServerUrl(serverUrl);
        } else {
            Configuration configuration = AppBeans.get(Configuration.class);
            BIConfig biConfig = configuration.getConfig(BIConfig.class);
            component.setServerUrl(biConfig.getPentahoServerUrl());
        }
}

    protected void loadReportPath(BIComponent component, Element element) {
        String reportPath = element.attributeValue("reportPath");
        if (StringUtils.isNotEmpty(reportPath)) {
            component.setReportPath(reportPath);
        }
    }
}
