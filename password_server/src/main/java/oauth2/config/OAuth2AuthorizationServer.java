package oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * 授权服务器
 *
 * @author Curise
 * @date 2019-09-07
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 用户认证
        endpoints.authenticationManager(authenticationManager);
    }

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
                // 授权码和刷新token
                .authorizedGrantTypes("authorization_code","refresh_token")
                .and()
                .withClient("app1")
                .secret("1234")
                .scopes("read", "write")
                // 密码模式和刷新token
                .authorizedGrantTypes("password", "refresh_token");
    }
}
