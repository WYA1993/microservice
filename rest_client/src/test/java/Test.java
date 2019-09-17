import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Curise
 * @create 2019/9/17
 */
public class Test {

    @org.junit.Test
    public void test(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String xyz = bCryptPasswordEncoder.encode("xyz");
        System.out.println(xyz);
    }
}
