package oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * 授权服务器
 * @author Curise
 * @date 2019-09-06
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 客户端id
                .withClient("app")
                // 客户端密钥
                .secret("123")
                // 权限
                .scopes("read","write")
                // 获取授权码后重定向地址
                .redirectUris("http://localhost:9000/callback")
                //令牌有效期秒
                //.accessTokenValiditySeconds(10)
                // 刷新令牌有效期秒
                //.refreshTokenValiditySeconds(1000)
                // 授权码和刷新token
                .authorizedGrantTypes("authorization_code","refresh_token");
    }

}
