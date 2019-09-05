function addSection(sectionName) {
    var existingDivs = document.getElementsByName(sectionName);
    var count = existingDivs.length;

    var divElement = document.createElement("div");
    divElement.setAttribute("id", sectionName + count);

    var button = document.createElement("button");
    button.setAttribute("type", "button");
    button.setAttribute("class", "btn btn-link");
    button.addEventListener("click", function () {
        deleteDiv(divElement)
    });
    button.textContent = "delete";
    divElement.appendChild(button);

    addDLContainer(divElement, "Name:", "text", sectionName);
    addDLContainer(divElement, "URL:", "text", sectionName + count + "link");
    addDLContainer(divElement, "Start date:", "date", sectionName + count + "start");
    addDLContainer(divElement, "End date:", "date", sectionName + count + "end");
    addDLContainer(divElement, "Title:", "text", sectionName + count + "title");
    addDLContainer(divElement, "Description:", "text", sectionName + count + "description");

    var hr = document.createElement("hr");
    divElement.appendChild(hr);

    var mainSection = document.getElementById(sectionName + "main");
    mainSection.append(divElement);
}

function addDLContainer(parent, textContent, type, name) {
    var dl = document.createElement("dl");
    var dt = document.createElement("dt");
    dt.textContent = textContent;
    var dd = document.createElement("dd");
    var inputName = document.createElement("input");
    inputName.setAttribute("type", type);
    inputName.setAttribute("name", name);
    inputName.setAttribute("class", "form-control");
    dd.append(inputName);
    dl.append(dt);
    dl.append(dd);
    parent.append(dl);
}

function deleteDiv(el) {
    var div = document.getElementById(el.id);
    div.style.display = "none";
    var inputs = div.getElementsByTagName("input");
    inputs[0].value = "";
}