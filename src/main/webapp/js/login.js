$(document).ready(function(){

    $("#registration").on("click", function(){
        $("#registration").hide();
        $("#cancel").show();
        $("#loginForm").slideUp("slow");
        $("#regForm").slideDown("slow");
    });
    $("#cancel").on("click", function(){
        $("#cancel").hide();
        $("#registration").show();
        $("#errorMessage").hide();
        $("#regForm").slideUp("slow");
        $("#loginForm").slideDown("slow");
        clearForm();
    });
    $("#finish").on("click", function(e){
        e.preventDefault();
        if ($("#regPass1").val() === $("#regPass2").val() && isFormValid()){
            var registrationDto = {
                login: $("#regLogin").val().trim(),
                password: $("#regPass1").val().trim(),
                firstName: $("#regFirstName").val().trim(),
                lastName: $("#regLastName").val().trim(),
                foreName: $("#regForeName").val().trim()
            };
            $.ajax({
                url:"/registration",
                method:"POST",
                data: JSON.stringify(registrationDto),
                contentType: "application/json; charset=utf-8",
                dataType:"json"
            }).then(function (data) {
                if (data.status === "OK"){
                    $("#regForm").hide();
                    $("#regButton").hide();
                    $("#finishReg").show();
                    clearForm();
                } else if (data.status === "ERROR"){
                    $("#errorMessage").show();
                };
            });
        }
    });
    function clearForm(){
        $("#regLogin").val("");
        $("#regPass1").val("");
        $("#regPass2").val("");
        $("#regFirstName").val("");
        $("#regLastName").val("");
        $("#regForeName").val("");
    }
    var isFormValid = function(){
        if (!$("#regLogin").val() && !$("#regPass1").val() && !$("#regPass2").val()
            && !$("#regFirstName").val() && !$("#regLastName").val() && !$("#regForeName").val()){
            return false;
        } else {
            return true;
        }
    };
});