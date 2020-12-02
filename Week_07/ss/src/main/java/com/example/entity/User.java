package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -1205226416664488559L;
    private Integer id;
    private String username;
    private String password;

}
