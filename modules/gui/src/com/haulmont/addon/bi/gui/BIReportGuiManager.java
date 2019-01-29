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

package com.haulmont.addon.bi.gui;

import com.haulmont.addon.bi.entity.BIReport;
import com.haulmont.addon.bi.entity.BIReportRole;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.security.entity.RoleType;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.entity.UserRole;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("cubabi_BIReportGuiManager")
public class BIReportGuiManager {
    @Inject
    protected DataManager dataManager;

    /**
     * Return list of reports, available for user
     *
     * @param user - caller user
     */
    public List<BIReport> getAvailableReports(@Nullable User user) {
        LoadContext<BIReport> ctx = new LoadContext<>(BIReport.class);
        ctx.setLoadDynamicAttributes(false);
        ctx.setView("biReport-edit-view");
        ctx.setQueryString("select r from cubabi$BIReport r");

        List<BIReport> reports = dataManager.loadList(ctx);
        reports = applySecurityPolicies(user, reports);
        return reports;
    }

    protected List<BIReport> applySecurityPolicies(User user, List<BIReport> reports) {
        if (user != null) {
            List<BIReport> filter = new ArrayList<>();
            for (BIReport report : reports) {
                Set<BIReportRole> biReportRoles = report.getRoles();
                if (biReportRoles == null || biReportRoles.size() == 0) {
                    filter.add(report);
                } else {
                    List<UserRole> userRoles = user.getUserRoles();
                    Set biRoles = biReportRoles.stream().map(BIReportRole::getRole).collect(Collectors.toSet());
                    userRoles.stream().filter(userRole -> {
                        //noinspection CodeBlock2Expr
                        return biRoles.contains(userRole.getRole())
                                || userRole.getRole().getType() == RoleType.SUPER;

                    }).findFirst().ifPresent(userRole -> {
                        //noinspection CodeBlock2Expr
                        filter.add(report);
                    });
                }
            }
            return filter;
        } else {
            return reports;
        }
    }
}
