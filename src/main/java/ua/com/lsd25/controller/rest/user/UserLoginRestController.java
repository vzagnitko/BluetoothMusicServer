package ua.com.lsd25.controller.rest.user;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.com.lsd25.controller.handler.login.UserAlreadyLoggedException;
import ua.com.lsd25.controller.handler.validate.ValidationException;
import ua.com.lsd25.controller.rest.ServerResponse;
import ua.com.lsd25.service.UserService;

import javax.validation.Valid;

/**
 * @author vzagnitko
 */
@RestController
@RequestMapping("/rest/logins")
public class UserLoginRestController {

    private static final Logger LOG = Logger.getLogger(UserLoginRestController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"", "/", "*"}, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ServerResponse> loginController(@Valid @RequestBody UserLoginRequest request,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors());
        }
        String username = request.getUsername();
        boolean isLoggedUser = userService.isLoggedUser();
        if (isLoggedUser) {
            throw new UserAlreadyLoggedException("User with username: " + username + " already logged in");
        }
        LOG.info("Login user: " + username);
        String password = request.getPassword();
        userService.autologin(username, password);
        return ResponseEntity.ok().body(new ServerResponse(200));
    }

}
