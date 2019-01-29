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

package com.haulmont.addon.bi.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Lob;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.Set;
import javax.persistence.OneToMany;

@Table(name = "CUBABI_BI_REPORT")
@Entity(name = "cubabi$BIReport")
public class BIReport extends StandardEntity {
    private static final long serialVersionUID = 3951610026125506916L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "CODE")
    protected String code;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "REPORT_TYPE", nullable = false)
    protected Integer reportType = ReportType.PENTAHO.getId();

    @Column(name = "REPORT_PATH")
    protected String reportPath;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "report")
    protected Set<BIReportRole> roles;

    public Set<BIReportRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<BIReportRole> roles) {
        this.roles = roles;
    }


    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public String getReportPath() {
        return reportPath;
    }


    public void setReportType(ReportType reportType) {
        this.reportType = reportType == null ? null : reportType.getId();
    }

    public ReportType getReportType() {
        return reportType == null ? null : ReportType.fromId(reportType);
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}