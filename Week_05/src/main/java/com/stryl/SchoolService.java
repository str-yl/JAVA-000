package com.stryl;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2020/11/15.
 */
@Data
@Configuration
@ConditionalOnProperty(prefix = "test.sks", value = "enabled", havingValue = "true")
@EnableConfigurationProperties(Klass.class)
public class SchoolService implements ISchool {

    // Resource
    @Autowired(required = true) //primary
            Klass class1;

    @Resource(name = "student100")
    Student student100;

    @Override
    public void ding(){

        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);

    }

}
