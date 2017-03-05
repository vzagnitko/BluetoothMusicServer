/**
 * Created by vzagnitko on 05.03.17.
 */

function login() {

    var username = $("#login-username").val();
    var password = $("#login-password").val();

    $.ajax({
        url: '/login',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            username: username,
            password: password
        }),
        dataType: 'json'
    });
}