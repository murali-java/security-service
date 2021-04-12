package com.api.secure.securityservice.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TokenRequest implements Serializable {
    private String user;
    private String pwd;
}
