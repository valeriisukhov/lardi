$(document).ready(function(){
    var currentIndex = 0;
    var editIndex = 0;
    var tmpList = null;
    var showAllContacts = function(){
        if (list.length === 0){
            $("#contacts").hide();
            $("#filter").hide();
            return;
        }
        $("#filter").show();
        $("#contacts").show();
        addContactsToTable(list);
    };
    function addContactsToTable(list){
        $("#contacts").show();
        $("#contactsBody").empty();
        currentIndex = 0;
        for (var i = 0; i < list.length; i++){
            appendToTable(list[i], i);
            currentIndex++;
        }
    };
    var clearNewContactForm = function(){
        $("#newContactLastName").val("");
        $("#newContactFirstName").val("");
        $("#newContactForeName").val("");
        $("#newContactMobile").val("");
        $("#newContactHome").val("");
        $("#newContactAddress").val("");
        $("#newContactEmail").val("");
    };
    var appendToTable = function(item, i){
        $("#contactsBody")
            .append($('<tr>')
                .append($('<td>').text(i+1))
                .append($('<td>').text(item.lastName)
                    .addClass("col-md-1"))
                .append($('<td>').text(item.firstName)
                    .addClass("col-md-1"))
                .append($('<td>').text(item.foreName)
                    .addClass("col-md-1"))
                .append($('<td>').text(item.mobilePhone)
                    .addClass("col-md-1"))
                .append($('<td>').text(item.homePhone)
                    .addClass("col-md-1"))
                .append($('<td>').text(item.address)
                    .addClass("col-md-1"))
                .append($('<td>').text(item.email)
                    .addClass("col-md-1"))
                .append($('<td>').addClass("col-md-1")
                    .append($('<button rel="'+item.id+'">').text("Edit")
                        .addClass("btn btn-default btn-block editContact")
                )
            )
                .append($('<td>').addClass("col-md-1")
                    .append($('<button rel="'+item.id+'">').text("Delete")
                        .addClass("btn btn-danger btn-block deleteContact")
                )
            )
        );
    }
    $("#addNewContact").on("click", function(){
        $("#filter").val("");
        $("#filter").trigger("keyup");
        $("#upperBlock").slideUp();
        $("#newContactForm").slideDown();
    });
    $("#cancelSaving").on("click", function(){
        $("#newContactForm").slideUp("fast");
        $("#upperBlock").slideDown("fast");
        $("#editContact").hide();
        $("#saveNewContact").show();
        $("#errorMessage").hide();
        clearNewContactForm();
    });
    $("#saveNewContact").on("click", function(){
        var contactDto = {
            lastName: $("#newContactLastName").val().trim(),
            firstName: $("#newContactFirstName").val().trim(),
            foreName: $("#newContactForeName").val().trim(),
            mobilePhone: $("#newContactMobile").val().trim(),
            homePhone: $("#newContactHome").val().trim(),
            address: $("#newContactAddress").val().trim(),
            email: $("#newContactEmail").val().trim(),
            user: null
        };
        $.ajax({
            url:"/saveContact",
            method:"POST",
            data: JSON.stringify(contactDto),
            contentType: "application/json; charset=utf-8",
            dataType:"json"
        }).then(function (data) {
            if (data.status === "OK"){
                $("#contacts").show();
                contactDto.id = data.data;
                list.push(contactDto);
                $("#filter").show();
                appendToTable(contactDto, currentIndex);
                currentIndex++;
                clearNewContactForm();
                $("#newContactForm").slideUp("fast");
                $("#upperBlock").slideDown("fast");
                $("#errorMessage").hide();
            } else if (data.status === "ERROR"){
                $("#errorMessage").show();
            }
        });
    });
    $("#contactsBody").on("click", ".deleteContact", function(){
        var self = $(this);
        var contactId = self.attr('rel');
        clearNewContactForm();
        $("#newContactForm").slideUp("fast");
        $.ajax({
            url:"/removeContact?id="+parseInt(contactId),
            method:"POST",
            contentType: "application/json; charset=utf-8",
            dataType:"json"
        }).then(function (data) {
            if (data.status === "OK"){
                self.parent().parent().remove();
                currentIndex--;
                deleteFromList(contactId);
                if (tmpList !== null){
                    if (tmpList.length > 0){
                        deleteFromTmpList(contactId);
                    }
                }
                if (currentIndex == 0 && tmpList == null){
                    $("#filter").val("");
                    $("#filter").trigger("keyup");
                    $("#filter").hide();
                    $("#editContact").hide();
                    $("#saveNewContact").show();
                    $("#upperBlock").show();
                    $("#contacts").hide();
                } else if (tmpList !== null && tmpList.length == 0 && currentIndex == 0){
                    $("#contacts").show();
                    $("#filter").val("");
                    $("#filter").trigger("keyup");
                }
            } else if (data.status === "ERROR"){
                console.log("ERROR");
            }
        });
    });
    $("#contactsBody").on("click", ".editContact", function(){
        var contactId = $(this).attr('rel');
        var indexInList = $(this).parent().parent().children(":nth-child(1)").text();
        clearNewContactForm();
        $("#newContactLastName").val(list[indexInList-1].lastName);
        $("#newContactFirstName").val(list[indexInList-1].firstName);
        $("#newContactForeName").val(list[indexInList-1].foreName);
        $("#newContactMobile").val(list[indexInList-1].mobilePhone);
        $("#newContactHome").val(list[indexInList-1].homePhone);
        $("#newContactAddress").val(list[indexInList-1].address);
        $("#newContactEmail").val(list[indexInList-1].email);
        $("#currentContactId").val(contactId);
        $("#saveNewContact").hide();
        $("#editContact").show();
        $("#filter").val("");
        $("#filter").trigger("keyup");
        $("#upperBlock").slideUp();
        $("#newContactForm").slideDown();
        editIndex = indexInList-1;
    });
    $("#editContact").on("click", function(){
        $("#errorMessage").hide();
        var contactDto = {
            lastName: $("#newContactLastName").val(),
            firstName: $("#newContactFirstName").val(),
            foreName: $("#newContactForeName").val(),
            mobilePhone: $("#newContactMobile").val(),
            homePhone: $("#newContactHome").val(),
            address: $("#newContactAddress").val(),
            email: $("#newContactEmail").val(),
            user: null
        };
        $.ajax({
            url:"/editContact?id="+parseInt($("#currentContactId").val()),
            method:"POST",
            data: JSON.stringify(contactDto),
            contentType: "application/json; charset=utf-8",
            dataType:"json"
        }).then(function (data) {
            if (data.status === "OK"){
                clearNewContactForm();
                editTableAfterContactUpdate(contactDto);
                showAllContacts();
                $("#errorMessage").hide();
                $("#saveNewContact").show();
                $("#editContact").hide();
                $("#newContactForm").slideUp("fast");
                $("#upperBlock").slideDown("fast");
            } else if (data.status === "ERROR"){
                console.log("ERROR");
                $("#errorMessage").show();
            }
        });
    });
    var editTableAfterContactUpdate = function(contactDto){
        var item = list[editIndex];
        item.lastName = contactDto.lastName;
        item.firstName = contactDto.firstName;
        item.foreName = contactDto.foreName;
        item.mobilePhone = contactDto.mobilePhone;
        item.homePhone = contactDto.homePhone;
        item.address = contactDto.address;
        item.email = contactDto.email;
    };
    var deleteFromList = function(id){
        for (var i = 0; i < list.length; i++){
            if (list[i].id === parseInt(id)){
                list.splice(i, 1);
            }
        }
    };
    var deleteFromTmpList = function(id){
        for (var i = 0; i < tmpList.length; i++){
            if (tmpList[i].id === parseInt(id)){
                tmpList.splice(i, 1);
            }
        }
    };
    showAllContacts();
    $("#filter").on("keyup", function(){
        filterContactsInTable($(this).val());
    });
    function filterContactsInTable(string){
        if (string.length > 0){
            tmpList = list.filter(function (el) {
                return ((el.firstName).toLowerCase()).indexOf(string.toLowerCase()) > -1 ||
                    ((el.lastName).toLowerCase()).indexOf(string.toLowerCase()) > -1 ||
                    el.mobilePhone.indexOf(string) > -1 ||
                    el.homePhone.indexOf(string) > -1;
            });
            addContactsToTable(tmpList);

        }
        else {
            tmpList = null;
            if(list.length > 0){
                addContactsToTable(list);
            }
        }
    };
});