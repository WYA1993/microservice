package oauth2.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Curise
 * @create 2019/9/17
 */
@Component
@ConfigurationProperties(prefix = "client.info")
@Data
public class ClientInfo {

    private String clientId;
    private String secret;
    private String responseType;
    private String redirectUri;
    private String authorizeUri;
    private String userInfoUri;
    private String tokenUri;
    private String scope;
    private String state;

}
