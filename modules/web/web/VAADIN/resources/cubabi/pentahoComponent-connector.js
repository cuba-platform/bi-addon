window.com_haulmont_addon_bi_web_toolkit_ui_CubaBIComponent = function() {
    var cubaBIComponent = new cubabi.CubaBIComponent(this.getElement());

    this.onStateChange = function() {
        cubaBIComponent.setReportUrl(this.getState().reportUrl);
    };
};
