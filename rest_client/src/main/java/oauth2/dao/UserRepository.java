package oauth2.dao;

import oauth2.entity.ClientUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Curise
 * @create 2019/9/17
 */
public interface UserRepository extends CrudRepository<ClientUser, Long> {

    Optional<ClientUser> findByUsername(String username);

}
