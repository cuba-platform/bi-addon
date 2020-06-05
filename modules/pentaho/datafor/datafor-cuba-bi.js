
var queryParams = window.location.search;

if (queryParams) {
    var pairsArray = queryParams.split('&');
    pairsArray.filter(item => item.includes('CUBA_VIEW_STATE'))
              .forEach(item => {
                var state = item.split('=')[1];
                if (state == "VIEW") {
                    removeEditBtn();
                }
              });
}

function removeEditBtn() {
    var editBtn = document.body.getElementsByClassName("virtual-cell hr-btn bd-btn")[0];
    if (!editBtn) {
        // wait while button will be created
        setTimeout(removeEditBtn, 50);
    } else {
        editBtn.parentNode.removeChild(editBtn);
    }
}


