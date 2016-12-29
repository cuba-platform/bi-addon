/*
 * TODO Copyright
 */

package com.haulmont.addon.bi.gui.xml.layout;

import com.haulmont.addon.bi.gui.components.BIComponent;
import com.haulmont.cuba.gui.xml.layout.loaders.AbstractComponentLoader;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

public class BIComponentLoader extends AbstractComponentLoader<BIComponent> {
    @Override
    public void createComponent() {
        resultComponent = (BIComponent) factory.createComponent(BIComponent.NAME);
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
        }
}

    protected void loadReportPath(BIComponent component, Element element) {
        String reportPath = element.attributeValue("reportPath");
        if (StringUtils.isNotEmpty(reportPath)) {
            component.setReportPath(reportPath);
        }
    }
}
