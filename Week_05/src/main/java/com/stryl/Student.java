package com.stryl;

/**
 * Created by Administrator on 2020/11/15.
 */
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "test.sks")
public class Student implements Serializable {

    private int id;
    private String name;

    public void init(){
        System.out.println("hello...........");
    }

    public Student create(){
        return new Student(101,"KK101");
    }
}
