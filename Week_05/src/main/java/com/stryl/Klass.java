package com.stryl;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Created by Administrator on 2020/11/15.
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "test.sks")
public class Klass {

    List<Student> students;

    public void dong(){
        System.out.println(this.getStudents());
    }

}
