package ua.com.lsd25.controller.rest.music;

import javazoom.jl.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ua.com.lsd25.controller.rest.ServerResponse;
import ua.com.lsd25.domain.music.Music;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.MusicService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author vzagnitko
 */
@RestController
@RequestMapping("/rest/musics")
public class MusicRestController {

    @Autowired
    private MusicService musicService;

    @RequestMapping(value = {"", "/", "*"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ServerResponse<List<Music>>> userMusicsController() throws ApplicationException {
        List<Music> musics = musicService.findMusics();
        return ResponseEntity.ok().body(new ServerResponse<>(musics, 200));
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<Void> uploadFileController(@RequestParam MultipartFile file) throws IOException, ApplicationException {

        if (file.isEmpty()) {
            throw new IllegalStateException("Please select file!");
        }

        String name = ((CommonsMultipartFile) file).getFileItem().getName();
        byte[] musicBytes = file.getBytes();
        musicService.saveMusic(name, musicBytes);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/play", method = RequestMethod.GET)
    public ResponseEntity<Void> playController(@RequestParam String name) throws Exception {
        Music music = musicService.findMusicByName(name);


//        File audioFile = new File("/Users/vzagnitko/Downloads/Temp/No Doubt – Don't Speak.mp3");

        InputStream is = new ByteArrayInputStream(music.getMusic());
        Player playMP3 = new Player(is);
        playMP3.play();


        return ResponseEntity.ok().build();
    }

}
