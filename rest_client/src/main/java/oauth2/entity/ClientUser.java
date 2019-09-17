package oauth2.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Curise
 * @create 2019/9/17
 */
@Entity
@Data
public class ClientUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String accessToken;

    private Calendar accessTokenValidity;

    private String refreshToken;

    @Transient
    private List<Entry> entries = new ArrayList<>();

}
