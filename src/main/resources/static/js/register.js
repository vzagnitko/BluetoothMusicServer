/**
 * Created by vzagnitko on 16.03.17.
 */

var link = '/rest/registers';

function registerUser() {

    var username = $("#reg-email").val();
    var firstName = $("#reg-first-name").val();
    var lastName = $("#reg-last-name").val();
    var password = $("#login-password").val();

    var userData = {
        username: username,
        first_name: firstName,
        last_name: lastName,
        password: password
    };

    $.ajax({
        type: "POST",
        url: link,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (msg) {
            console.log(msg);
            // window.location.replace("/music");
        },
        data: JSON.stringify(userData)
    });

}