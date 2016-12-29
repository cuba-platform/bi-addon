var cubabi = cubabi || {};

cubabi.CubaBIComponent = function (element) {
  var reportUrl;
  var frameElement = document.createElement("iframe");

  frameElement.setAttribute("frameBorder", 0);
  frameElement.setAttribute("tabIndex", -1);
  frameElement.style.width = "100%";
  frameElement.style.height = "100%";

  element.appendChild(frameElement);

  this.setReportUrl = function(newReportUrl) {
    if (!reportUrl || reportUrl != newReportUrl) {
      frameElement.setAttribute("src", newReportUrl);
    }
    reportUrl = newReportUrl;
  }
};
