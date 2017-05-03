package com.haulmont.addon.bi.web.bireport;

import com.haulmont.addon.bi.entity.BIReport;
import com.haulmont.addon.bi.entity.BIReportRole;
import com.haulmont.addon.bi.entity.ReportType;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.actions.AddAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.config.WindowConfig;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.security.entity.Role;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.UUID;

public class BIReportEdit extends AbstractEditor<BIReport> {
    @Inject
    protected Datasource<BIReport> biReportDs;
    @Inject
    protected CollectionDatasource<BIReportRole, UUID> rolesDs;
    @Inject
    protected Table<BIReportRole> rolesTable;
    @Named("fieldGroup.reportPath")
    protected TextField reportPathField;
    @Inject
    protected WindowConfig windowConfig;
    @Inject
    protected Metadata metadata;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        biReportDs.addItemPropertyChangeListener(e -> {
                    if ("reportType".equals(e.getProperty())) {
                        initRequiredFields();
                    }
                }
        );
        initRolesTable();
    }

    @Override
    protected void postInit() {
        super.postInit();
        initRequiredFields();
    }

    protected void initRolesTable() {
        AddAction addAction = new AddAction(rolesTable, items -> {
            if (items != null) {
                Role role = (Role) items.iterator().next();
                boolean hasRole = false;
                for (UUID id : rolesDs.getItemIds()) {
                    BIReportRole reportRole = rolesDs.getItem(id);
                    if (reportRole != null && role.equals(reportRole.getRole())) {
                        hasRole = true;
                    }
                }
                if (!hasRole) {
                    BIReportRole reportRole = metadata.create(BIReportRole.class);
                    reportRole.setReport(getItem());
                    reportRole.setRole(role);
                    rolesDs.addItem(reportRole);
                }
            }
        });
        addAction.setWindowId(windowConfig.getLookupScreen(Role.class).getId());
        rolesTable.addAction(addAction);
        rolesTable.addAction(new RemoveAction(rolesTable));
    }

    protected void initRequiredFields() {
        BIReport report = getItem();
        reportPathField.setRequired(report.getReportType() == ReportType.PENTAHO);
    }
}