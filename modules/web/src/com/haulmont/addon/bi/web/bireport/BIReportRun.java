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

package com.haulmont.addon.bi.web.bireport;

import com.haulmont.addon.bi.entity.BIReport;
import com.haulmont.addon.bi.gui.BIReportGuiManager;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.client.ClientConfig;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.ItemTrackingAction;
import com.haulmont.cuba.gui.data.GroupDatasource;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.haulmont.addon.bi.web.reportrun.BIReportRunWindow.REPORT_NAME_PARAMETER;
import static com.haulmont.addon.bi.web.reportrun.BIReportRunWindow.REPORT_PATH;

public class BIReportRun extends AbstractLookup {
    @Inject
    protected TextField<String> nameFilter;
    @Inject
    protected TextField<String> codeFilter;
    @Inject
    protected GroupTable<BIReport> biReportsTable;
    @Inject
    protected GroupDatasource<BIReport, UUID> biReportsDs;
    @Inject
    protected UserSessionSource userSessionSource;
    @Inject
    protected BIReportGuiManager biReportGuiManager;
    @Inject
    protected ClientConfig clientConfig;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        List<BIReport> reports = biReportGuiManager.getAvailableReports(userSessionSource.getUserSession().getUser());
        for (BIReport report : reports) {
            biReportsDs.includeItem(report);
        }

        Action runAction = new ItemTrackingAction("runBIReport") {
            @Override
            public void actionPerform(Component component) {
                BIReport report = (BIReport) target.getSingleSelected();
                if (report != null) {
                    openWindow("cubabi$reportRun.window", WindowManager.OpenType.NEW_TAB,
                            ParamsMap.of(REPORT_NAME_PARAMETER, report.getName(),
                                    REPORT_PATH, report.getReportPath()));
                }
            }
        };
        biReportsTable.addAction(runAction);
        biReportsTable.setItemClickAction(runAction);

        addAction(new AbstractAction("applyFilter", clientConfig.getFilterApplyShortcut()) {
            @Override
            public void actionPerform(Component component) {
                filterReports();
            }
        });
    }

    public void filterReports() {
        String nameFilterValue = StringUtils.lowerCase(nameFilter.getValue());
        String codeFilterValue = StringUtils.lowerCase(codeFilter.getValue());

        List<BIReport> reports = new ArrayList<>(
                biReportGuiManager.getAvailableReports(userSessionSource.getUserSession().getUser()));
        reports = reports.stream().filter(report -> {
            if (nameFilterValue != null
                    && !report.getName().toLowerCase().contains(nameFilterValue)) {
                return false;
            }
            if (codeFilterValue != null) {
                if (report.getCode() == null
                        || (report.getCode() != null
                        && !report.getCode().toLowerCase().contains(codeFilterValue))) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());

        biReportsDs.clear();
        for (BIReport report : reports) {
            biReportsDs.includeItem(report);
        }
    }

    public void clearFilter() {
        nameFilter.setValue(null);
        codeFilter.setValue(null);
    }
}