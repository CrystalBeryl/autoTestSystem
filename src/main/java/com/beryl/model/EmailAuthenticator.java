package com.beryl.model;

import groovy.transform.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.lang.annotation.Documented;
import java.util.List;

/**
 * Created by qjnup on 2016/10/11.
 */

/*@RedisHash("authenticator")
@EqualsAndHashCode*/
public class EmailAuthenticator extends Authenticator {

/*    @Id String id;*/
/*    private String username;
    private String password;*/

/*
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
*/

/*    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/
    String username = null;
    String password = null;

    public EmailAuthenticator() {
    }
    public EmailAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

}
