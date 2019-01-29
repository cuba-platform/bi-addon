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
