package oauth2.service;

import oauth2.dao.UserRepository;
import oauth2.entity.ClientUser;
import oauth2.entity.ClientUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

/**
 * @author Curise
 * @create 2019/9/17
 */
public class ClientUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<ClientUser> clientUser = userRepository.findByUsername(s);
        if (!clientUser.isPresent()) {
            throw new UsernameNotFoundException("username is error");
        }
        return new ClientUserDetails(clientUser.get());
    }
}
