package ua.com.lsd25.controller.rest.music;

import javazoom.jl.decoder.JavaLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ua.com.lsd25.controller.rest.ServerResponse;
import ua.com.lsd25.domain.music.Music;
import ua.com.lsd25.domain.music.MusicWrapper;
import ua.com.lsd25.helper.WrapperHelper;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.MusicService;
import ua.com.lsd25.service.PlayMusicService;

import java.io.IOException;
import java.util.List;

/**
 * @author vzagnitko
 */
@RestController
@RequestMapping("/rest/musics")
public class MusicRestController {

    @Autowired
    private MusicService musicService;

    @Autowired
    private PlayMusicService playMusicService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @RequestMapping(value = {"", "/", "*"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ServerResponse<List<MusicWrapper>>> userMusicsController() throws ApplicationException {
        List<Music> musics = musicService.findMusics();
        return ResponseEntity.ok().body(new ServerResponse<>(WrapperHelper.createWrapperList(musics), 200));
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<ServerResponse<Void>> uploadFileController(@RequestParam MultipartFile file)
            throws IOException, ApplicationException {

        if (file.isEmpty()) {
            throw new IllegalStateException("Please select file!");
        }

        String name = file.getOriginalFilename();
        byte[] musicBytes = file.getBytes();
        musicService.saveMusic(name, musicBytes);
        return ResponseEntity.ok().body(new ServerResponse<>(200));
    }

    //@Valid @RequestBody MusicRequest musicRequest,
    //, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    //,BindingResult bindingResult
    @RequestMapping(value = "/play", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ServerResponse<Void>> playController(@RequestParam Long musicId) throws ApplicationException, JavaLayerException {
//        if (bindingResult.hasErrors()) {
//            throw new ValidationException(bindingResult.getAllErrors());
//        }

        playMusicService.playMusic(musicId);

        return ResponseEntity.ok().body(new ServerResponse<>(200));
    }

    @RequestMapping(value = "/suspend", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ServerResponse<Void>> suspendController(@RequestParam Long musicId) throws ApplicationException {
//        if (bindingResult.hasErrors()) {
//            throw new ValidationException(bindingResult.getAllErrors());
//        }

//        playMusicService.suspendMusic(musicId);
        jmsTemplate.convertAndSend("music-queue", musicId);

        return ResponseEntity.ok().body(new ServerResponse<>(200));
    }

    @RequestMapping(value = "/resume", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ServerResponse<Void>> resumeController(@RequestParam Long musicId) throws ApplicationException {
//        if (bindingResult.hasErrors()) {
//            throw new ValidationException(bindingResult.getAllErrors());
//        }

        playMusicService.resumeMusic(musicId);

        return ResponseEntity.ok().body(new ServerResponse<>(200));
    }

}
