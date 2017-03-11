package ua.com.lsd25.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.com.lsd25.service.MusicService;

/**
 * @author vzagnitko
 */
@Controller
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @RequestMapping(method = RequestMethod.GET)
    public String loginController() {
        return "layout/music";
    }

}
