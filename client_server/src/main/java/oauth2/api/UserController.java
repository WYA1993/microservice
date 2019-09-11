package oauth2.api;

import oauth2.entity.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/9/1.
 */
@RestController
public class UserController {

    @GetMapping("/api/userList")
    public ResponseEntity<List<UserInfo>> getUserInfo(){
        List<UserInfo> users = new ArrayList<>();
        users.add(new UserInfo("test1", "test1@qq.com"));
        users.add(new UserInfo("test2", "test2@qq.com"));
        return ResponseEntity.ok(users);
    }

}
