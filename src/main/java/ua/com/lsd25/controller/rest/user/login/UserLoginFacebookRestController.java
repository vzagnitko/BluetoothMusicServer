package ua.com.lsd25.controller.rest.user.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.com.lsd25.controller.rest.ServerResponse;

/**
 * @author vzagnitko
 */
@RestController
@RequestMapping("/rest/login/facebooks")
public class UserLoginFacebookRestController {

    private final Facebook facebook;

    private final ConnectionRepository connectionRepository;

    @Autowired
    public UserLoginFacebookRestController(Facebook facebook,
                                           ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value = {"", "/", "*"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ServerResponse> helloFacebook() {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {

        }


        org.springframework.social.facebook.api.User user = facebook.userOperations().getUserProfile();
        PagedList<Post> feed = facebook.feedOperations().getFeed();
        return ResponseEntity.ok().body(new ServerResponse(HttpStatus.OK.value()));
    }

}
