package oauth2.api;

import oauth2.entity.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/9/1.
 */
@RestController
public class UserController {

    @GetMapping("/api/userInfo")
    public ResponseEntity<UserInfo> getUserInfo(){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = user.getUsername() + "@qq.com";

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(user.getUsername());
        userInfo.setEmail(email);
        return ResponseEntity.ok(userInfo);
    }

}
