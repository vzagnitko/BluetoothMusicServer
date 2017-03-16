/**
 * Created by vzagnitko on 11.03.17.
 */

var link = '/rest/logins';

function loginUser() {

    var username = $("#login-username").val();
    var password = $("#login-password").val();
    var isRememberMe = $("#remember-me").is(':checked');

    var userData = {
        username: username,
        password: password,
        remember_me: isRememberMe
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