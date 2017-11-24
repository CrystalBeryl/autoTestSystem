package com.beryl.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import javax.mail.Authenticator;
import java.util.Map;

import static java.util.Collections.singletonMap;

/**
 * Created by qjnup on 2016/11/29.
 */
@WritingConverter
public class AddressToMapConverter implements Converter<Authenticator, Map<String, String>> {

    private String username;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Map<String, String> convert(Authenticator authenticator) {
        return singletonMap(username,authenticator.toString());
    }
}
