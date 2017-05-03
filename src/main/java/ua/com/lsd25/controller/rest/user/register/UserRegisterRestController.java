package ua.com.lsd25.controller.rest.user.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.com.lsd25.controller.handler.validate.ValidationException;
import ua.com.lsd25.controller.rest.ServerResponse;
import ua.com.lsd25.domain.user.User;
import ua.com.lsd25.domain.user.role.Role;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.UserService;

import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;

/**
 * @author vzagnitko
 */
@RestController
@RequestMapping("/rest/registers")
public class UserRegisterRestController {

    private final UserService userService;

    @Autowired
    public UserRegisterRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"", "/", "*"}, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ServerResponse> registerController(@Valid @RequestBody UserRegisterRequest request,
                                                             BindingResult bindingResult)
            throws UnknownHostException, ApplicationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors());
        }
        String registerIp = InetAddress.getLocalHost().getHostAddress();
//        User user = new User(request, registerIp);
        User user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(request.getPassword())
                .registerIp(registerIp)
                .registerDate(new Timestamp(System.currentTimeMillis()))
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .role(Role
                        .builder()
                        .userRole(Role.UserRole.USER)
                        .build())
                .build();
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(new ServerResponse(HttpStatus.CREATED.value()));
    }

}
