package jdbc.test;
import lombok.*;

/**
 * Created by Administrator on 2020/11/16.
 */
@Data
@AllArgsConstructor
public class User {
    String userName;
    String passWord;
    int age;
    String gender;
}
