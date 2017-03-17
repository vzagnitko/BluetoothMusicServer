/**
 * Created by vzagnitko on 16.03.17.
 */

function registerUser() {

    var username = $("#reg-email").val();
    var firstName = $("#reg-first-name").val();
    var lastName = $("#reg-last-name").val();
    var password = $("#reg-password").val();

    var userData = {
        username: username,
        first_name: firstName,
        last_name: lastName,
        password: password
    };

    $.ajax({
        type: "POST",
        url: '/rest/registers',
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (msg) {
            console.log(msg);
            window.location.replace("/login");
        },
        data: JSON.stringify(userData)
    });

}