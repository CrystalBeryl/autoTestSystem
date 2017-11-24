package com.beryl.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by qjnup on 2016/10/21.
 */

public class PwDigest {

    public  String  digest(String str){
        MessageDigest md = null;
        byte[] password = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            password = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return convertToHexString(password);
    }

    public  String convertToHexString(byte[] data){
        StringBuffer strBuff = new StringBuffer();
        for (int i = 0; i < data.length ; i++) {
            strBuff.append(Integer.toHexString(0xff & data[i]));
        }
        return strBuff.toString().toUpperCase();
    }

}
