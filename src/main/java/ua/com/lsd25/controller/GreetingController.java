package ua.com.lsd25.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.lsd25.domain.user.Role;
import ua.com.lsd25.domain.user.User;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.UserService;
import ua.com.lsd25.ws.request.HelloMessage;
import ua.com.lsd25.ws.response.Greeting;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author vzagnitko
 */
@Controller
public class GreetingController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) throws ApplicationException, UnknownHostException {
        model.addAttribute("name", name);
//        userService.saveUser(new User("Victor", "Zagnitko", "1234",
//                "victor.zagnitko@gmail.com", new Role(Role.UserRole.ADMIN), InetAddress.getLocalHost().getHostAddress()));
        User user = userService.findUserByUsername("victor.zagnitko@gmail.com");
        return "greeting";
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

}