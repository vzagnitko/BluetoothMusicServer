function loginUser() {
    var username = $("#login-username").val();
    var password = $("#login-password").val();

    var userData = {
        username: username,
        password: password
    };

    $.ajax({
        type: "POST",
        url: "/login",
        dataType: "json",
        contentType: "application/json",
        success: function (msg) {
//                if (msg) {
//                    alert("Login user" + username + "!");
//                    location.reload(true);
//                } else {
//                    alert("Cannot login user" + username + "!");
//                }
        },
        data: JSON.stringify(userData)
    });
}