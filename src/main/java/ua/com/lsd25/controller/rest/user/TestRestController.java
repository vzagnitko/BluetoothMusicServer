package ua.com.lsd25.controller.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.com.lsd25.domain.user.Role;
import ua.com.lsd25.domain.user.User;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.UserService;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author vzagnitko
 */
@RestController
@RequestMapping("/rest/test")
public class TestRestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"", "/", "*"}, method = RequestMethod.GET)
    public void testSaveUser() throws UnknownHostException, ApplicationException {
        userService.saveUser(new User("Victor", "Zagnitko", "1234",
                "victor.zagnitko@gmail.com", Role.ADMIN, InetAddress.getLocalHost().getHostAddress()));
    }

}
