package com.haulmont.addon.bi.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.security.entity.Role;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "CUBABI_BI_REPORT_ROLE")
@Entity(name = "cubabi$BIReportRole")
public class BIReportRole extends StandardEntity {
    private static final long serialVersionUID = -5043809760299464024L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORT_ID")
    protected BIReport report;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    protected Role role;

    public void setReport(BIReport report) {
        this.report = report;
    }

    public BIReport getReport() {
        return report;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }


}