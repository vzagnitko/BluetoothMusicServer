package ua.com.lsd25.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author vzagnitko
 */
@Controller
@RequestMapping("/music")
public class MusicController {

    @RequestMapping(value = {"", "/", "*"}, method = RequestMethod.GET)
    public String loginController() {
        return "layout/music";
    }

}
