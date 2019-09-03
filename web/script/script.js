function deleteDiv(el) {
    var div = document.getElementById(el.id);
    var parent = div.parentElement;
    div.style.display = "none";
    div.childNodes[3].childNodes[3].childNodes[1].value = '';
    var totalShowed = 0;
    for (var i = 0; i < parent.childElementCount; i++) {
        if (parent.children[i].style.display !== "none") {
            totalShowed++;
        }
    }
    if (totalShowed === 1) {
        parent.style.display = "none";
    }
}