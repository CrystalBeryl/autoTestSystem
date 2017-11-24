package com.beryl.util;

import com.beryl.model.EmailAuthenticator;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import javax.mail.Authenticator;
import java.util.Map;

/**
 * Created by qjnup on 2016/11/29.
 */

/*
@ReadingConverter
public class MapToAddressConverter implements Converter<Map<String, String>, Authenticator> {

    private String username;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Authenticator convert(Map<String, String> stringStringMap) {
        return new EmailAuthenticator(username, stringStringMap.get(username));
    }
}
*/
