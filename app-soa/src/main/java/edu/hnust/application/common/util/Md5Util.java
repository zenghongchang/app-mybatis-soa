package edu.hnust.application.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    
    private String originalString;
    
    public Md5Util(String originalString) {
        this.originalString = originalString;
    }
    
    public String toMD5() {
        MessageDigest messageDigest = null;        
        try {
            messageDigest = MessageDigest.getInstance("MD5");            
            messageDigest.reset();            
            messageDigest.update(this.originalString.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }        
        byte[] byteArray = messageDigest.digest();        
        StringBuilder md5StrBuff = new StringBuilder();        
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }        
        return md5StrBuff.toString();
    }
}