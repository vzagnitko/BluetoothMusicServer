package ua.com.lsd25.controller.rest.music;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ua.com.lsd25.controller.handler.validate.ValidationException;
import ua.com.lsd25.controller.rest.ServerResponse;
import ua.com.lsd25.domain.music.Music;
import ua.com.lsd25.domain.music.MusicWrapper;
import ua.com.lsd25.helper.WrapperHelper;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.MusicService;

import javax.validation.Valid;
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
    public ResponseEntity<ServerResponse<List<MusicWrapper>>> userMusicsController() throws ApplicationException {
        List<Music> musics = musicService.findMusics();
        return ResponseEntity.ok().body(new ServerResponse<>(WrapperHelper.createWrapperList(musics), 200));
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

    @RequestMapping(value = "/play", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> playController(@Valid @RequestBody MusicRequest musicRequest,
                                               BindingResult bindingResult) throws ApplicationException, JavaLayerException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors());
        }
        Long musicId = musicRequest.getMusicId();
        Music music = musicService.findMusicById(musicId);

        InputStream is = new ByteArrayInputStream(music.getMusic());
        Player playMP3 = new Player(is);
        playMP3.play();

        return ResponseEntity.ok().build();
    }

}
