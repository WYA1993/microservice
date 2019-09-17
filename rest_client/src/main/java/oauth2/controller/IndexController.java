package oauth2.controller;

import oauth2.dao.UserRepository;
import oauth2.entity.*;
import oauth2.oauth.AuthorizationCodeTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;

/**
 * @author Curise
 * @create 2019/9/17
 */
@Controller
public class IndexController {

    @Autowired
    private AuthorizationCodeTokenService tokenService;

    @Autowired
    private UserRepository users;
    @Autowired
    private ClientInfo clientInfo;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/mainPage")
    public ModelAndView mainPage() {
        ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        ClientUser clientUser = userDetails.getClientUser();

        if (clientUser.getAccessToken() == null) {
            String authEndpoint = tokenService.getAuthorizationEndpoint();
            return new ModelAndView("redirect:" + authEndpoint);
        }

        clientUser.setEntries(Arrays.asList(
                new Entry("entry 1"),
                new Entry("entry 2")));

        ModelAndView mv = new ModelAndView("mainPage");
        mv.addObject("user", clientUser);

        UserInfo userInfo = tokenService.tryToGetUserInfo(clientUser.getAccessToken());
        mv.addObject("userInfo", userInfo);
        return mv;
    }

    @GetMapping("/callback")
    public ModelAndView callback(@RequestParam("code") String code, @RequestParam("state") String state) {
        if (!Objects.equals(state, clientInfo.getState())) {
            throw new RuntimeException("Invalid Certificate");
        }
        ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        ClientUser clientUser = userDetails.getClientUser();

        OAuth2Token token = tokenService.getToken(code);
        clientUser.setAccessToken(token.getAccessToken());

        Calendar tokenValidity = Calendar.getInstance();
        long validIn = System.currentTimeMillis() + Long.parseLong(token.getExpiresIn());
        tokenValidity.setTime(new Date(validIn));
        clientUser.setAccessTokenValidity(tokenValidity);
        clientUser.setRefreshToken(token.getRefreshToken());
        users.save(clientUser);

        return new ModelAndView("redirect:/mainPage");
    }


}
