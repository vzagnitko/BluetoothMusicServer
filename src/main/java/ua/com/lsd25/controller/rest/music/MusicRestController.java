package ua.com.lsd25.controller.rest.music;

import com.google.common.base.Strings;
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
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.MusicService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vzagnitko
 */
@RestController
@RequestMapping("/rest/musics")
public class MusicRestController {

    private final MusicService musicService;

//    @Autowired
//    private PlayMusicService playMusicService;

    private final JmsTemplate jmsTemplate;

    @Autowired
    public MusicRestController(MusicService musicService, JmsTemplate jmsTemplate) {
        this.musicService = musicService;
        this.jmsTemplate = jmsTemplate;
    }

    @RequestMapping(value = {"", "/", "*"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ServerResponse<List<MusicWrapper>>> userMusicsController() throws ApplicationException {
        List<Music> musics = musicService.findMusics();
        List<MusicWrapper> musicWrappers = new ArrayList<>();
        for (Music music : musics) {
            MusicWrapper musicWrapper = MusicWrapper
                    .builder()
                    .id(music.getId())
                    .isFavorite(music.isFavorite())
                    .name(music.getName())
                    .build();
            musicWrappers.add(musicWrapper);
        }
        return ResponseEntity.ok().body(new ServerResponse<>(musicWrappers, 200));
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<ServerResponse<Void>> uploadFileController(@RequestParam MultipartFile file)
            throws IOException, ApplicationException {

        if (file.isEmpty()) {
            throw new ApplicationException("Please select file!");
        }

        String name = file.getOriginalFilename();
        if (Strings.isNullOrEmpty(name)) {
            throw new ApplicationException("Wrong file name!");
        }
        byte[] musicBytes = file.getBytes();
        musicService.saveMusic(name, musicBytes);
        return ResponseEntity.ok().body(new ServerResponse<>(200));
    }

    //@Valid @RequestBody MusicRequest musicRequest,
    //, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    //,BindingResult bindingResult
    @RequestMapping(value = "/play", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ServerResponse<Void>> playController(@RequestParam Long musicId) throws ApplicationException {
//        if (bindingResult.hasErrors()) {
//            throw new ValidationException(bindingResult.getAllErrors());
//        }

//        playMusicService.playMusic(musicId);
        jmsTemplate.convertAndSend("play-music", musicId);
        return ResponseEntity.ok().body(new ServerResponse<>(200));
    }

    @RequestMapping(value = "/suspend", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ServerResponse<Void>> suspendController(@RequestParam Long musicId) throws ApplicationException {
//        if (bindingResult.hasErrors()) {
//            throw new ValidationException(bindingResult.getAllErrors());
//        }

//        playMusicService.suspendMusic(musicId);
//        jmsTemplate.convertAndSend("music-queue", musicId);
        jmsTemplate.convertAndSend("suspend-music", musicId);
        return ResponseEntity.ok().body(new ServerResponse<>(200));
    }

    @RequestMapping(value = "/resume", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ServerResponse<Void>> resumeController(@RequestParam Long musicId) throws ApplicationException {
//        if (bindingResult.hasErrors()) {
//            throw new ValidationException(bindingResult.getAllErrors());
//        }

        jmsTemplate.convertAndSend("resume-music", musicId);

        return ResponseEntity.ok().body(new ServerResponse<>(200));
    }

}
