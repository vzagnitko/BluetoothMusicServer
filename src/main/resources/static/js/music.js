/**
 * Created by vzagnitko on 11.03.17.
 */

function musics() {

    $.ajax({
        type: "GET",
        url: '/rest/musics',
        success: function (data) {
            console.log(data);
            var musicTable = $("#music-table");
            var entity = data.entity;
            for (var i = 0; i < entity.length; i++) {
                var link = "<a href='#' onclick='playMusic()' music-id=" + entity[i].id + ">" + entity[i].name + "</a>";
                musicTable.append("<tr> <td>" + link + "</td> </tr>");
            }
        }
    });

}

function playMusic() {

    var selectedMusic = 3;

    var musicData = {
        music_id: selectedMusic
    };

    $.ajax({
        type: "POST",
        url: link + "/play",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            console.log(data);
        },
        data: JSON.stringify(musicData)
    });

}