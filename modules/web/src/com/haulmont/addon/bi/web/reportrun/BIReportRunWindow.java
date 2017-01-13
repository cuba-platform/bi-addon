/*
 * TODO Copyright
 */

package com.haulmont.addon.bi.web.reportrun;

import com.haulmont.addon.bi.BIConfig;
import com.haulmont.addon.bi.gui.components.BIComponent;
import com.haulmont.cuba.gui.WindowParam;
import com.haulmont.cuba.gui.components.AbstractWindow;

import javax.inject.Inject;
import java.util.Map;

public class BIReportRunWindow extends AbstractWindow {

    public static final String REPORT_NAME_PARAMETER = "reportName";
    public static final String REPORT_PATH = "reportPath";

    @Inject
    protected BIComponent biComponent;
    @Inject
    protected BIConfig biConfig;

    @WindowParam(name = REPORT_NAME_PARAMETER)
    protected String reportName;

    @WindowParam(name = REPORT_PATH)
    protected String reportPath;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        biComponent.setReportPath(reportPath);
        biComponent.setServerUrl(biConfig.getPentahoServerUrl());
        setCaption(reportName);
    }
}
