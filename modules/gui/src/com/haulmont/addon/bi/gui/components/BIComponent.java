/*
 * TODO Copyright
 */

package com.haulmont.addon.bi.gui.components;

import com.haulmont.cuba.gui.components.Component;

public interface BIComponent extends Component, Component.BelongToFrame {
    String NAME = "biComponent";

    String getServerUrl();

    void setServerUrl(String serverUrl);

    String getReportPath();

    void setReportPath(String reportPath);

    boolean isEditorMode();

    void setEditorMode(boolean editorMode);
}
