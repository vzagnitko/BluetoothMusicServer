package ua.com.lsd25.controller.rest.user.login;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.com.lsd25.controller.handler.login.UserAlreadyLoggedException;
import ua.com.lsd25.controller.handler.validate.ValidationException;
import ua.com.lsd25.controller.rest.ServerResponse;
import ua.com.lsd25.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Md5PasswordEncoder passwordEncoder;

    @Autowired
    private TokenBasedRememberMeServices tokenBasedRememberMeServices;

    @RequestMapping(value = {"", "/", "*"}, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ServerResponse> loginController(HttpServletRequest httpServletRequest,
                                                          HttpServletResponse httpServletResponse,
                                                          @Valid @RequestBody UserLoginRequest request,
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
        boolean isRememberMe = request.getRememberMe();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        password = passwordEncoder.encodePassword(password, username);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        if (!Strings.isNullOrEmpty(password)) {
            authenticationManager.authenticate(authentication);
        }
        if (authentication.isAuthenticated()) {
            if (isRememberMe) {
                tokenBasedRememberMeServices.onLoginSuccess(httpServletRequest, httpServletResponse, authentication);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOG.debug(String.format("Auto login %s successfully!", username));
        }
        return ResponseEntity.ok().body(new ServerResponse(HttpStatus.OK.value()));
    }

}
