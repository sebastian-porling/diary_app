/* Global variables */
let addForm, addDate, addText, addImage;
let editForm, editDate, editText, editImage, editId;
let entriesHtml;

const BASE_URL = location.protocol + '//' + location.host + "/api/";

function initializeGlobalVariables() {
    addForm = document.getElementById('addForm');
    addDate = document.getElementById('date');
    addText = document.getElementById('text');
    addImage = document.getElementById('image')
    editForm = document.getElementById('editForm');
    editDate = document.getElementById('edit-date');
    editText = document.getElementById('edit-text');
    editImage = document.getElementById('edit-image');
    editId = document.getElementById('edit-id');
    entriesHtml = document.getElementById('entries');
}

/* When ready document */
window.addEventListener("load", () => {
    initializeGlobalVariables();

    addForm.addEventListener('submit', (event) => {
        event.preventDefault();
        $('#addModal').modal('hide');
        var reader = new FileReader();
        console.log(addImage.files[0]);
        reader.onload = ((e) => {
            let img = e.target.result;
            console.log(img);
            addEntry(addDate.value, addText.value, img);
            resetFields(addDate, addText, addImage);
        });
        reader.readAsDataURL(addImage.files[0]);
    });

    editForm.addEventListener('submit', (event) => {
        event.preventDefault();
        $('#editModal').modal('hide');
        editEntry(editId.value, editDate.value, editText.value, editImage.value);
        resetFields(editId, editDate, editText, editImage);
    });
});



/**
 * Resets a input field
 * @param args input fields
 */
function resetFields(...args) {
    args.forEach(argument => argument.value="");
}

/**
 * Checks if any of the arguments are empty
 * @param args any number of strings
 * @returns {boolean} true if one is empty
 */
function checkEmptyField(...args) {
    for (let i = 0; i < args.length; i++)
        if(args[i] === "") return true;
    return false;
}

function showEditEntry(element) {
    setEditFields(element.parentNode.parentNode.parentNode);
    $('#editModal').modal('show');
}

function setEditFields(element) {
    editDate.value = element.children[0].children[1].innerText;
    editId.value = element.children[0].children[0].innerText;
    editText.value = element.children[1].children[0].innerText;
    editImage.value = element.children[1].children[2].children[0].src;
}

/* ******* Fetching functions ******* */

function getAll() {
    $.ajax({
        url: BASE_URL + "entries",
        type: 'GET',
        contentType: "application/json",
        success: function(data) {
            updateEntries(data.data);
        },
        fail: (err) => console.log("Couldn't get contacts ", err)
    });
}

function addEntry(date, text, img) {
    if(checkEmptyField(date, text, img)) return;
    let dataObject = {date: date, text: text, img: img};
    console.log("Adding ", dataObject);
    $.ajax({
        url: BASE_URL + "entry/create",
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(dataObject),
        success: function(data) {
            getAll();
        },
        fail: (err) => console.log("Couldn't create contact " + dataObject, err)
    });
}

function editEntry(id, date, text, img) {
    if(checkEmptyField(id, date, text, img)) return;
    let dataObject = {id: id, date: date, text: text, img:img}
    console.log("Editing ", dataObject);
    $.ajax({
        url: BASE_URL + "entry/update",
        type: 'PATCH',
        contentType: "application/json",
        data: JSON.stringify(dataObject),
        success: function(data) {
            getAll();
        },
        fail: (err) => console.log("Couldn't update contact " + dataObject, err)
    });
}

function deleteEntry(element) {
    let parent = element.parentNode.parentNode.parentNode
    let id = parent.children[0].children[0].innerText;
    console.log("Deleting ", id);
    console.log(location.protocol + '//' + location.host + "/api/entry/delete/"+id)
    $.ajax({
        url: BASE_URL + "entry/delete/" + id,
        type: 'DELETE',
        contentType: "application/json",
        success: function(data) {
            parent.parentNode.removeChild(parent);
        },
        fail: (err) => console.log("Couldn't delete contact", err)
    });

}

function addToEntries(entry) {
    entriesHtml.appendChild(generateEntry(entry));
}

function updateEntries(entries) {
    entriesHtml.innerHTML = "";
    entries.forEach(entry => {
       entriesHtml.appendChild(generateEntry(entry));
    });
}

function editEntryDiv(entry) {
    if (searchEntryDiv(entry.id, 0, entriesHtml.children.length-1, entry)){
        console.log("found!");
    }
}

function searchEntryDiv(x, start, end, entry) {
    if (start > end) return false;
    let mid=Math.floor((start + end)/2);
    if (entriesHtml.children[mid].children[0].children[0].innerText==x) {
        entriesHtml.children[mid].replaceWith(generateEntry(entry));
        return true;
    }
    if(entriesHtml.children[mid].children[0].children[0].innerText > x)
        return searchEntryDiv(x, start, mid-1, entry);
    else
        return searchEntryDiv(x, mid+1, end, entry);
}

function generateEntry(entry) {
    const entryHtml = `
        <div class="entry-header card-header">
            <div class="entry-id"><h2>${entry.id}</h2></div>
            <div class="entry-date"><h2>${entry.date}</h2></div>
            <div class="btn-group">
                <button class="btn btn-sm" onclick="showEditEntry(this);">
                    <span class="oi oi-pencil" title="pencil" aria-hidden="true"></span>
                </button>
                <button class="close" onclick="deleteEntry(this);">x</button>
            </div>
        </div>
        <div class="entry-body card-body">
            <div class="entry-text">${entry.text}</div>
            <br>
            <div class="entry-image">
                <img class="card-img-top" src="${entry.img}" alt="entry-img"/>
            </div>
        </div>
        <div class="clear"></div>`;

    let entryDiv = document.createElement('div');
    entryDiv.setAttribute('class', 'entry card');
    entryDiv.innerHTML = entryHtml;
    return entryDiv;
}

/**
 * Appends the children to the parent
 * @param parent HTML Element
 * @param children and number of HTML elements.
 */
function appendChildren(parent, ...children) {
    children.forEach(child => parent.appendChild(child));
}