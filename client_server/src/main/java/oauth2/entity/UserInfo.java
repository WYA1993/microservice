package oauth2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2019/9/1.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private String username;
    private String email;
}
